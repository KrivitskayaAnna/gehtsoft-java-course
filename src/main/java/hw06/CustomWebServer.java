package hw06;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class CustomWebServer {
    private static final Logger logger;

    static {
        Configurator.setRootLevel(org.apache.logging.log4j.Level.INFO);
        logger = LogManager.getLogger(CustomWebServer.class);
    }

    private final int port;
    private final ExecutorService executor;
    private ServerSocket serverSocket;
    private volatile boolean running = false;
    private int requestsServed = 0;
    private LocalDateTime serverStarted;

    public CustomWebServer(int port, int threadPoolSize, boolean useVirtualThreads) {
        this.port = port;
        this.executor = new CustomExecutorService(threadPoolSize, useVirtualThreads);//Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        serverStarted = LocalDateTime.now();
        executor.submit(() -> {
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    logger.info("Got new input connection");
                    executor.submit(() -> {
                        try {
                            logger.info("Handling request by " + Thread.currentThread().getName());
                            handleClient(clientSocket);
                            logger.info("Finished handling request by " + Thread.currentThread().getName());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void stop() throws IOException {
        serverSocket.close();
        running = false;
        executor.shutdown();
    }


    @Data
    @AllArgsConstructor
    private static class HttpReq {
        private String method;
        private String path;
        private String httpVersion;
        private Map<String, String> headers;
        private String body;
    }

    private static HttpReq readRequest(BufferedReader in) throws IOException {
        String requestLine = in.readLine();
        if (requestLine == null) return null;
        String[] requestParts = requestLine.split(" ");
        if (requestParts.length < 3) return null;
        String method = requestParts[0];
        String path = requestParts[1];
        String httpVersion = requestParts[2];
        Map<String, String> headers = new HashMap<>();
        String inputHeader = in.readLine();
        while (inputHeader != null && !inputHeader.isEmpty()) {
            String[] headerParts = inputHeader.split(":");
            if (headerParts.length >= 2) {
                headers.put(headerParts[0], headerParts[1]);
            }
            inputHeader = in.readLine();
        }
        String body = null;
        int contentLength = Integer.parseInt(headers.getOrDefault("Content-Length", "0").trim());
        if (contentLength > 0) {
            char[] bodyChars = new char[contentLength];
            int totalRead = 0;
            while (totalRead < contentLength) {
                int bytesRead = in.read(bodyChars, totalRead, contentLength - totalRead);
                if (bytesRead == -1) break;
                totalRead += bytesRead;
            }
            body = new String(bodyChars);
        }
        return new HttpReq(method, path, httpVersion, headers, body);
    }

    private void handleClient(Socket clientSocket) throws IOException {
        try (clientSocket) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            HttpReq request = readRequest(bufferedReader);
            if (request == null) {
                respondError(printWriter);
                logger.info("Responded with error due to null request");
            } else {
                requestsServed += 1;
                String method = request.getMethod();
                String path = request.getPath();
                logger.info("Received request with method " + method + ", path:" + path);
                if ((Objects.equals(method, "GET")) && (Objects.equals(path, "/"))) {
                    handleGetEndpoint(printWriter);
                } else if ((Objects.equals(method, "GET")) && (path.startsWith("/static/"))) {
                    handleGetStaticEndpoint(printWriter, clientSocket.getOutputStream(), path);
                } else if ((Objects.equals(method, "GET") && (Objects.equals(path, "/api/time")))) {
                    handleGetTimeEndpoint(printWriter);
                } else if ((Objects.equals(method, "GET") && (Objects.equals(path, "/api/stats")))) {
                    handleGetStatsEndpoint(printWriter);
                } else if ((Objects.equals(method, "POST") && (Objects.equals(path, "/api/echo")))) {
                    handlePostEchoEndpoint(printWriter, request.getBody());
                } else {
                    respondError(printWriter);
                    logger.info("Responded with error due to unhandled endpoint");
                }
            }
        }
    }


    private void respondError(PrintWriter printWriter) {
        printWriter.println("HTTP/1.1 400 Bad Request\r");
        printWriter.println("Content-Type: text/html\r");
        printWriter.println("Content-Length: 0\r");
        printWriter.println("\r");
        printWriter.flush();
    }

    private void respondSuccess(PrintWriter printWriter, String contentType, String body) {
        printWriter.println("HTTP/1.1 200 OK\r");
        printWriter.println("Content-Type: " + contentType + '\r');
        printWriter.println("Content-Length: " + body.length() + '\r');
        printWriter.println("\r");
        printWriter.println(body + '\r');
        printWriter.flush();
    }

    private void handleGetEndpoint(PrintWriter printWriter) throws IOException {
        String staticHtmlFile = String.join("", Files.readAllLines(Paths.get("/home/anna/Documents/java-course/java-course-krivitskaya-anna/src/main/java/static/index.html")));
        respondSuccess(printWriter, "text/html", staticHtmlFile);
        logger.info("Responded successfully to GET /");
    }

    private String defineContentType(String fileFormat) {
        switch (fileFormat) {
            case "png":
            case "jpg":
            case "gif":
                return "image/" + fileFormat;
            case "ico":
                return "image/x-icon";
            case "js":
                return "application/javascript";
            case "html":
            case "css":
            default:
                return "text/" + fileFormat;
        }
    }

    private void handleGetStaticEndpoint(PrintWriter printWriter, OutputStream outputStream, String path) {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get("/home/anna/Documents/java-course/java-course-krivitskaya-anna/src/main/java" + path));
            String fileFormat = path.split("\\.")[1];
            String contentType = defineContentType(fileFormat);
            printWriter.println("HTTP/1.1 200 OK\r");
            printWriter.println("Content-Type: " + contentType + '\r');
            printWriter.println("Content-Length: " + fileContent.length + '\r');
            printWriter.println("\r");
            outputStream.write(fileContent);
            outputStream.flush();
            logger.info("Responded successfully to GET static");
        } catch (Exception e) {
            respondError(printWriter);
            logger.info("Responded with error to " + path);
        }
    }

    private void handleGetTimeEndpoint(PrintWriter printWriter) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String responseBody = String.format("{\"currentTime\":\"%s\"}", formattedDateTime);
        respondSuccess(printWriter, "application/json", responseBody);
        logger.info("Responded successfully to GET time");
    }

    private void handleGetStatsEndpoint(PrintWriter printWriter) {
        long uptimeMillis = Duration.between(serverStarted, LocalDateTime.now()).toMillis();
        String responseBody = String.format("{\"requestsServed\":\"%d\", \"uptimeMillis\":\"%d\"}", requestsServed, uptimeMillis);
        respondSuccess(printWriter, "application/json", responseBody);
        logger.info("Responded successfully to GET stats");
    }

    private void handlePostEchoEndpoint(PrintWriter printWriter, String inputBody) {
        respondSuccess(printWriter, "application/json", inputBody);
        logger.info("Responded successfully to POST echo");
    }
}