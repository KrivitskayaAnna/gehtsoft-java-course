package hw06;

import java.util.concurrent.Future;

public class CustomExecutorServiceDemo {
    private static Runnable printHello(Integer taskNum) {
        return () -> System.out.println("hello" + taskNum);
    }

    public static void main(String[] args) throws InterruptedException {
        CustomExecutorService executorService = new CustomExecutorService(2, true);
        Future<?> fut1 = executorService.submit(printHello(1));
        Future<?> fut2 = executorService.submit(printHello(2));
        Future<?> fut3 = executorService.submit(printHello(3));
        Future<?> fut4 = executorService.submit(printHello(4));
        Future<?> fut5 = executorService.submit(printHello(5));
        Thread.sleep(1000);
    }
}
