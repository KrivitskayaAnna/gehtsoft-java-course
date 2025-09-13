package hw07.custom;

import hw06.CustomWebServer;
import hw07.custom.annotations.CustomRequestMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CustomCrudWebServer extends CustomWebServer {
    private Map<String, Handler> routingMap;

    public CustomCrudWebServer(int port, int threadPoolSize, boolean useVirtualThreads) {
        super(port, threadPoolSize, useVirtualThreads);
        this.routingMap = new LinkedHashMap<>();
    }

    public void registerController(Object controller) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        logger.info("Start controller");
        Class<?> controllerClass = controller.getClass();
        Method[] methods = controllerClass.getDeclaredMethods();
        String basePath = "";
        if (controllerClass.isAnnotationPresent(CustomRequestMapping.class)) {
            CustomRequestMapping classAnnotation = controllerClass.getAnnotation(CustomRequestMapping.class);
            basePath = classAnnotation.value();
        }
        for (Method method : methods) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                Class<?> annotationType = annotation.annotationType();
                CustomRequestMapping reqAnnotation = annotationType.getAnnotation(CustomRequestMapping.class);
                if (reqAnnotation != null) {
                    logger.info("Start registering handler for method " + method.getName());
                    String httpMethod = reqAnnotation.httpMethod();
                    String path = (String) annotationType.getMethod("value").invoke(annotation);
                    if (Objects.equals(path, "/")) path = "";
                    registerHandler(httpMethod, basePath + path, method, controller);
                }
                break;
            }
        }
    }

    @Data
    @AllArgsConstructor
    private class Handler {
        private Method method;
        private Object controller;

    }

    private void registerHandler(String httpMethod, String path, Method method, Object controller) {
        Handler handler = new Handler(method, controller);
        routingMap.put(httpMethod + " " + path, handler);
        logger.info("Registered method " + httpMethod + " , path " + path);
    }

    private Handler getHandlerForRequest(String httpMethod, String path) {
        return routingMap.get(httpMethod + " " + path);
    }

    @Override
    public void handleClient(Socket clientSocket) throws IOException {
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
                Handler handler = getHandlerForRequest(method, path);
                if (handler == null) {
                    respondError(printWriter);
                    logger.info("Responded with error due to unhandled request");
                }
                logger.info("Found handler"); //TODO: pass arguments to invokation
                Object methodResult = handler.getMethod().invoke(handler.getController());
                respondSuccess(printWriter, "application/json", methodResult.toString());
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

//curl -vvv -X POST http://localhost:8080/api/v1/users -H 'Content-Type: application/json' -d '{"name":"Ann", "surname":"K", "age":"24"}'
//curl -vvv -X GET http://localhost:8080/api/v1/users