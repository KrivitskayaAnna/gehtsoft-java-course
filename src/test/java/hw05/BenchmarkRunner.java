package hw05;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class BenchmarkRunner {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PlatformThreadsPerformanceTests.class.getSimpleName())
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

//    results:
//    Benchmark                                                          Mode  Cnt        Score   Error   Units
//    PlatformThreadsPerformanceTests.createPhysical                     avgt    2        0.848            s/op
//    PlatformThreadsPerformanceTests.createPhysical:gc.alloc.rate       avgt    2        3.993          MB/sec
//    PlatformThreadsPerformanceTests.createPhysical:gc.alloc.rate.norm  avgt    2  3508216.000            B/op
//    PlatformThreadsPerformanceTests.createPhysical:gc.count            avgt    2          ≈ 0          counts
//    PlatformThreadsPerformanceTests.createVirtual                      avgt    2        0.216            s/op
//    PlatformThreadsPerformanceTests.createVirtual:gc.alloc.rate        avgt    2       26.598          MB/sec
//    PlatformThreadsPerformanceTests.createVirtual:gc.alloc.rate.norm   avgt    2  6014900.000            B/op
//    PlatformThreadsPerformanceTests.createVirtual:gc.count             avgt    2        1.000          counts
//    PlatformThreadsPerformanceTests.createVirtual:gc.time              avgt    2        5.000              ms
}