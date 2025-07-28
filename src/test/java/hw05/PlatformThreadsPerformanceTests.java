package hw05;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;


@State(Scope.Benchmark)
public class PlatformThreadsPerformanceTests {
    private Thread.Builder virtualThreadBuilder;
    private Thread.Builder platformThreadBuilder;

    @Setup(Level.Trial)
    public void setup() {
        virtualThreadBuilder = Thread.ofVirtual();
        platformThreadBuilder = Thread.ofPlatform();
    }

    public void createThreadsTest(Blackhole bh, Thread.Builder threadBuilder, int threadNum, int sleepMillis) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            Thread thread = threadBuilder.start(() -> {
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            threads.add(thread);
        }
        for (Thread thread : threads) thread.join();
        bh.consume(threads);
    }

    @Benchmark
    public void createVirtual(Blackhole bh) throws InterruptedException {
        createThreadsTest(bh, virtualThreadBuilder, 8_000, 200);
    }

    @Benchmark
    public void createPhysical(Blackhole bh) throws InterruptedException {
        createThreadsTest(bh, platformThreadBuilder, 8_000, 200);
    }


}
