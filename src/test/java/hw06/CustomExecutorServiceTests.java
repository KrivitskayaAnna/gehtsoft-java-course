package hw06;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomExecutorServiceTests {
    public static int incrementAtomicTest(CustomExecutorService executorService) throws ExecutionException, InterruptedException {
        AtomicInteger atomic = new AtomicInteger(0);
        ArrayList<Future> futures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            futures.add(executorService.submit(() -> atomic.getAndIncrement()));
        }
        for (Future<Integer> future : futures) {
            future.get();
        }
        return atomic.get();
    }

    @Test
    public void testConcurrentExecution() throws ExecutionException, InterruptedException {
        Assertions.assertEquals(1000, incrementAtomicTest(new CustomExecutorService(10, true)));
        Assertions.assertEquals(1000, incrementAtomicTest(new CustomExecutorService(10, false)));
    }

    public static void shutdownNotAcceptNewTasks(CustomExecutorService executorService) {
        executorService.shutdown();
        executorService.submit(() -> System.out.println("Hello"));
    }

    @Test
    public void testShutdownNotAcceptNewTasks() {
        Assertions.assertThrows(RejectedExecutionException.class, () -> shutdownNotAcceptNewTasks(new CustomExecutorService(10, true)));
        Assertions.assertThrows(RejectedExecutionException.class, () -> shutdownNotAcceptNewTasks(new CustomExecutorService(10, false)));
    }

    public static int shutdownProceedsExistingTasks(CustomExecutorService executorService) throws ExecutionException, InterruptedException {
        Future<Integer> future = executorService.submit(() -> {
            try {
                Thread.sleep(10);
                return 10;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        executorService.shutdown();
        return future.get();
    }

    @Test
    public void testShutdownProceedsExistingTasks() throws ExecutionException, InterruptedException {
        Assertions.assertEquals(10, shutdownProceedsExistingTasks(new CustomExecutorService(10, true)));
        Assertions.assertEquals(10, shutdownProceedsExistingTasks(new CustomExecutorService(10, false)));
    }
}
