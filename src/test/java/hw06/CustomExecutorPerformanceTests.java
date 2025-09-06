package hw06;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@State(Scope.Benchmark)
public class CustomExecutorPerformanceTests {
    @Param({"true", "false"})
    private boolean useVirtualThreads;

    @Param({"10", "50", "100", "500"})
    private int threadNum;

    private CustomExecutorService executorService;

    @Setup(Level.Trial)
    public void setup() {
        executorService = new CustomExecutorService(threadNum, useVirtualThreads);
    }

    public static Runnable testTask(Integer sleepMillis) {
        return () -> {
            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public void createExecutorServiceTest(Blackhole bh, int numTasks, int sleepMillis) throws InterruptedException, ExecutionException {
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < numTasks; i++) {
            futures.add(executorService.submit(testTask(sleepMillis)));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        bh.consume(futures);
    }

    @Benchmark
    public void testExecutorServiceTasks(Blackhole bh) throws InterruptedException, ExecutionException {
        createExecutorServiceTest(bh, 10_000, 10);
    }
}
