package hw07.custom;

import hw06.CustomWebServer;

public class CustomCrudWebServer extends CustomWebServer {
    public CustomCrudWebServer(int port, int threadPoolSize, boolean useVirtualThreads) {
        super(port, threadPoolSize, useVirtualThreads);
    }

    public void registerController(Object controller) {
        // TODO: Use reflection to scan annotations and register handlers
    }

    // In handleClient(Socket clientSocket):
    // Parse request, match to registered handlers, invoke methods
}
