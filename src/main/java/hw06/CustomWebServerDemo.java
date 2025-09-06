package hw06;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class CustomWebServerDemo {
    @Data
    @AllArgsConstructor
    private static class HttpResp {
        private String httpVersion;
        private String statusCode;
        private String statusText;
        private Map<String, String> headers;
        private String body;
    }

    public static HttpResp readResponse(BufferedReader in) throws IOException {
        String requestLine = in.readLine();
        if (requestLine == null) return null;
        String[] requestParts = requestLine.split(" ");
        if (requestParts.length < 3) return null;
        String httpVersion = requestParts[0];
        String statusCode = requestParts[1];
        String statusText = requestParts[2];
        Map<String, String> headers = new HashMap<>();
        String inputHeader = in.readLine();
        while (inputHeader != null && !inputHeader.isEmpty()) {
            String[] headerParts = inputHeader.split(":");
            if (headerParts.length >= 2) {
                headers.put(headerParts[0], headerParts[1]);
            }
            inputHeader = in.readLine();
        }
        String body = in.readLine();
        return new HttpResp(httpVersion, statusCode, statusText, headers, body);
    }

    public static void writeRequest(PrintWriter printWriter, String method, String path, String body) {
        printWriter.println(String.format("%s %s HTTP/1.1", method, path));
        printWriter.println("Host: localhost:8080");
        printWriter.println("User-Agent: Mozilla/5.0");
        printWriter.println("Accept: */*");
        printWriter.println("");
        printWriter.println(body);
    }

    public static void sendClientRequest(String method, String path, String body) throws IOException {
        Socket socket = new Socket("localhost", 8080);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writeRequest(printWriter, method, path, body);
        HttpResp serverResponse = readResponse(bufferedReader);
        System.out.println("Got server response: " + serverResponse);
        socket.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        CustomWebServer server = new CustomWebServer(8080, 100, true);
        server.start();
        System.out.println("Server started");
        Thread.sleep(100);

        sendClientRequest("GET", "/", "");
        sendClientRequest("GET", "/api/stats", "");
        sendClientRequest("GET", "/api/time", "");
        sendClientRequest("GET", "/static/img/kotiki.jpg", "");
        sendClientRequest("POST", "/api/echo", "Hello server!");
        server.stop();
    }
}