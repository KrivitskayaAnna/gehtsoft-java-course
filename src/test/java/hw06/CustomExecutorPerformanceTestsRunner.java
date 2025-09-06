package hw06;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class CustomExecutorPerformanceTestsRunner {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CustomExecutorPerformanceTests.class.getSimpleName())
                .warmupIterations(1)
                .warmupTime(TimeValue.seconds(1))
                .measurementIterations(2)
                .measurementTime(TimeValue.seconds(1))
                .mode(Mode.AverageTime)
                .forks(1)
                .addProfiler(GCProfiler.class)
                .jvmArgs("-Xms2G", "-Xmx2G")
                .build();

        new Runner(opt).run();
    }

    // virtual threads executor is twice faster than the physical as a number of threads grow
    // the memory for virtual threads is allocated faster (alloc.rate)
    // the amount of memory (alloc.rate.norm) taken by virtual threads executor is more than memory taken by physical one
    // but the gap between them decreases with the raise of threads number

//    results:
//    Benchmark                                                                   (threadNum)  (useVirtualThreads)  Mode  Cnt        Score   Error   Units
//    CustomExecutorPerformanceTests.testExecutorServiceTasks                              10                 true  avgt    2       10.333            s/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate                10                 true  avgt    2        0.242          MB/sec
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate.norm           10                 true  avgt    2  2621248.000            B/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.count                     10                 true  avgt    2          ≈ 0          counts
//    CustomExecutorPerformanceTests.testExecutorServiceTasks                              10                false  avgt    2       10.139            s/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate                10                false  avgt    2        0.113          MB/sec
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate.norm           10                false  avgt    2  1204680.000            B/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.count                     10                false  avgt    2          ≈ 0          counts
//    CustomExecutorPerformanceTests.testExecutorServiceTasks                              50                 true  avgt    2        2.065            s/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate                50                 true  avgt    2        1.188          MB/sec
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate.norm           50                 true  avgt    2  2573100.000            B/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.count                     50                 true  avgt    2          ≈ 0          counts
//    CustomExecutorPerformanceTests.testExecutorServiceTasks                              50                false  avgt    2        2.030            s/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate                50                false  avgt    2        0.557          MB/sec
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate.norm           50                false  avgt    2  1186796.000            B/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.count                     50                false  avgt    2          ≈ 0          counts
//    CustomExecutorPerformanceTests.testExecutorServiceTasks                             100                 true  avgt    2        1.032            s/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate               100                 true  avgt    2        2.423          MB/sec
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate.norm          100                 true  avgt    2  2625068.000            B/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.count                    100                 true  avgt    2          ≈ 0          counts
//    CustomExecutorPerformanceTests.testExecutorServiceTasks                             100                false  avgt    2        1.016            s/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate               100                false  avgt    2        1.118          MB/sec
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate.norm          100                false  avgt    2  1190552.000            B/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.count                    100                false  avgt    2          ≈ 0          counts
//    CustomExecutorPerformanceTests.testExecutorServiceTasks                             500                 true  avgt    2        0.209            s/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate               500                 true  avgt    2       13.997          MB/sec
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate.norm          500                 true  avgt    2  3071948.800            B/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.count                    500                 true  avgt    2          ≈ 0          counts
//    CustomExecutorPerformanceTests.testExecutorServiceTasks                             500                false  avgt    2        0.419            s/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate               500                false  avgt    2        6.629          MB/sec
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.alloc.rate.norm          500                false  avgt    2  3004135.200            B/op
//    CustomExecutorPerformanceTests.testExecutorServiceTasks:gc.count                    500                false  avgt    2          ≈ 0          counts
}