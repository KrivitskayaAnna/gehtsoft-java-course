package hw04;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Map;


@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
@Fork(1)
public class CustomHashMapPerformanceTests {
    @Param({"1000", "10000", "100000", "1000000"})
    private int size;

    private HashMap<Integer, String> javaMap;
    private CustomHashMap<Integer, String> customMap;

    @Setup(Level.Trial)
    public void setup() {
        javaMap = new HashMap<>();
        customMap = new CustomHashMap<>();
    }

    public void putTest(Blackhole bh, Map<Integer, String> testMap) {
        for (int i = 0; i < size; i++) {
            testMap.put(i, "value" + i);
        }
        bh.consume(testMap);
    }

    public void addRemoveTest(Blackhole bh, Map<Integer, String> testMap) {
        for (int i = 0; i < size; i++) {
            testMap.put(i, "value" + i);
        }
        bh.consume(testMap);
        for (int i = 0; i < size; i++) {
            testMap.remove(i);
            bh.consume(testMap);
        }
    }

    public void addGetTest(Blackhole bh, Map<Integer, String> testMap) {
        for (int i = 0; i < size; i++) {
            testMap.put(i, "value" + i);
            bh.consume(testMap);
        }
        for (int i = 0; i < size; i++) {
            bh.consume(testMap.get(i));
        }
    }

    @Benchmark
    public void javaPut(Blackhole bh) {
        putTest(bh, javaMap);
    }

    @Benchmark
    public void customPut(Blackhole bh) {
        putTest(bh, customMap);
    }

    @Benchmark
    public void javaAddRemove(Blackhole bh) {
        addRemoveTest(bh, javaMap);
    }

    @Benchmark
    public void customAddRemove(Blackhole bh) {
        addRemoveTest(bh, customMap);
    }

    @Benchmark
    public void javaAddGet(Blackhole bh) {
        addGetTest(bh, javaMap);
    }

    @Benchmark
    public void customAddGet(Blackhole bh) {
        addGetTest(bh, customMap);
    }


    //the metrics on java hashMap and custom hashMap are almost the same
    //despite the fact that in custom hashMap we skipped red-black tree transformation that happens on java hash map when it has lots of collisions

//    tests results:
//Benchmark                                       (size)  Mode  Cnt   Score   Error  Units
//    CustomHashMapPerformanceTests.customAddGet        1000  avgt       ≈ 10⁻⁵           s/op
//    CustomHashMapPerformanceTests.customAddGet       10000  avgt       ≈ 10⁻⁴           s/op
//    CustomHashMapPerformanceTests.customAddGet      100000  avgt        0.005           s/op
//    CustomHashMapPerformanceTests.customAddGet     1000000  avgt        0.041           s/op
//    CustomHashMapPerformanceTests.customAddRemove     1000  avgt       ≈ 10⁻⁵           s/op
//    CustomHashMapPerformanceTests.customAddRemove    10000  avgt       ≈ 10⁻⁴           s/op
//    CustomHashMapPerformanceTests.customAddRemove   100000  avgt        0.003           s/op
//    CustomHashMapPerformanceTests.customAddRemove  1000000  avgt        0.028           s/op
//    CustomHashMapPerformanceTests.customPut           1000  avgt       ≈ 10⁻⁵           s/op
//    CustomHashMapPerformanceTests.customPut          10000  avgt       ≈ 10⁻⁴           s/op
//    CustomHashMapPerformanceTests.customPut         100000  avgt        0.004           s/op
//    CustomHashMapPerformanceTests.customPut        1000000  avgt        0.031           s/op
//    CustomHashMapPerformanceTests.javaAddGet          1000  avgt       ≈ 10⁻⁵           s/op
//    CustomHashMapPerformanceTests.javaAddGet         10000  avgt       ≈ 10⁻⁴           s/op
//    CustomHashMapPerformanceTests.javaAddGet        100000  avgt        0.003           s/op
//    CustomHashMapPerformanceTests.javaAddGet       1000000  avgt        0.043           s/op
//    CustomHashMapPerformanceTests.javaAddRemove       1000  avgt       ≈ 10⁻⁵           s/op
//    CustomHashMapPerformanceTests.javaAddRemove      10000  avgt       ≈ 10⁻⁴           s/op
//    CustomHashMapPerformanceTests.javaAddRemove     100000  avgt        0.003           s/op
//    CustomHashMapPerformanceTests.javaAddRemove    1000000  avgt        0.029           s/op
//    CustomHashMapPerformanceTests.javaPut             1000  avgt       ≈ 10⁻⁵           s/op
//    CustomHashMapPerformanceTests.javaPut            10000  avgt       ≈ 10⁻⁴           s/op
//    CustomHashMapPerformanceTests.javaPut           100000  avgt        0.003           s/op
//    CustomHashMapPerformanceTests.javaPut          1000000  avgt        0.034           s/op
}
