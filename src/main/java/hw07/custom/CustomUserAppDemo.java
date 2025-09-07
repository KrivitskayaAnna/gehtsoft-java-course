package hw07.custom;

import java.io.IOException;

public class CustomUserAppDemo {
    public static void main(String[] args) throws IOException {
        // Initialize server
        CustomCrudWebServer server = new CustomCrudWebServer(8080, 50, true); // Virtual threads
        CustomUserService service = new CustomUserService();
        CustomUserController controller = new CustomUserController(service);
        server.registerController(controller);

        try {
            server.start();
            System.out.println("REST API server started: http://localhost:8080");
            // Run for testing
            Thread.sleep(60000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }

    }
}
