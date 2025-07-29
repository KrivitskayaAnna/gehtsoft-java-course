package hw05;

import hw01.CustomList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(1)
public class MultithreadedListPerformanceTests {
    @Param({"2"})
    private int numThreads;

    @Param({"1000000"})
    private int numElementsAdd;

    @Param({"list", "synclist", "readwritelist"})
    private String listType;

    private CustomList<String> list;

    @Setup(Level.Trial)
    public void setup() {
        switch (listType) {
            case "list":
                list = new CustomList<>();
                break;
            case "synclist":
                list = new SynchronizedCustomList<>();
                break;
            case "readwritelist":
                list = new ReadWriteLockCustomList<>();
                break;
            default:
                throw new IllegalArgumentException("Incorrect listType " + listType);
        }
    }

    public Thread addElementsSinleThread() {
        return Thread.ofVirtual().start(() -> {
            for (int i = 0; i < numElementsAdd; i++) {
                list.add(String.valueOf(i));
            }
        });
    }

    public void addElementsConcurrently(Blackhole bh) throws InterruptedException {
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            Thread athread = addElementsSinleThread();
            threads[i] = athread;
        }
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }
        bh.consume(list);
    }

    @Benchmark
    public void addElementsBenchMark(Blackhole bh) throws InterruptedException {
        addElementsConcurrently(bh);
    }
    //performance test results show that synchronizations and locks reduce efficiency
    //and thus should be used only if absolutely necessary

//    results:
//    Benchmark                                                  (listType)  (numElementsAdd)  (numThreads)  Mode  Cnt  Score   Error  Units
//    MultithreadedListPerformanceTests.addElementsBenchMark           list           1000000             2  avgt       0.126           s/op
//    MultithreadedListPerformanceTests.addElementsBenchMark       synclist           1000000             2  avgt       0.131           s/op
//    MultithreadedListPerformanceTests.addElementsBenchMark  readwritelist           1000000             2  avgt       0.281           s/op
}
