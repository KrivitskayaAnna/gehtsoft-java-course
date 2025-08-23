package hw06;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
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
        CustomExecutorService virtualService = new CustomExecutorService(10, true);
        CustomExecutorService physicalService = new CustomExecutorService(10, false);
        Assertions.assertEquals(10, shutdownProceedsExistingTasks(virtualService));
        Assertions.assertEquals(10, shutdownProceedsExistingTasks(physicalService));
        Assertions.assertTrue(virtualService.isShutdown());
        Assertions.assertTrue(virtualService.isTerminated());
        Assertions.assertTrue(physicalService.isShutdown());
        Assertions.assertTrue(physicalService.isTerminated());
    }

    public static int shutdownNowReturnsEnqueued(Boolean useVirtualThreads) throws InterruptedException {
        CustomExecutorService executorService = new CustomExecutorService(1, useVirtualThreads);
        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Thread.sleep(10);
        List<Runnable> runnable = executorService.shutdownNow();
        return runnable.size();
    }

    @Test
    public void testShutdownNowInterruptsImmediately() throws InterruptedException {
        Assertions.assertEquals(2, shutdownNowReturnsEnqueued(true));
        Assertions.assertEquals(2, shutdownNowReturnsEnqueued(false));
    }

    public static boolean awaitTerminationResult(Boolean useVirtualThreads, Integer timeoutMillis) throws InterruptedException {
        CustomExecutorService executorService = new CustomExecutorService(1, useVirtualThreads);
        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executorService.shutdown();
        return executorService.awaitTermination(timeoutMillis, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testAwaitTerminationFalseOnTimeout() throws InterruptedException {
        Assertions.assertFalse(awaitTerminationResult(true, 10));
    }

    @Test
    public void testAwaitTerminationTrueOnEnd() throws InterruptedException {
        Assertions.assertTrue(awaitTerminationResult(true, 100));
    }
}
