package hw06;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CustomExecutorServiceDemo {
    private static Runnable printHello(Integer taskNum) {
        return () -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("hello" + taskNum);
        };
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CustomExecutorService executorService = new CustomExecutorService(10, true);
        ArrayList<Future> futures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            futures.add(executorService.submit(printHello(i)));
        }
        executorService.shutdown();
        for (Future future : futures) {
            future.get();
        }
    }
}
