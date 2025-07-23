package hw04;

import hw03.ListPerformanceTests;

import java.util.HashMap;
import java.util.Map;

public class CustomHashMapPerformanceTests {
    public static void addMillion(Map<Integer, String> aMap) {
        for (int i = 0; i < 1_000_000; i++) {
            aMap.put(i, "value" + i);
        }
    }

    public static void getMillion(Map<Integer, String> aMap) {
        for (int i = 0; i < 1_000_000; i++) {
            aMap.get(i);
        }
    }

    public static void addRemove(Map<Integer, String> aMap) {
        for (int i = 0; i < 10_000; i++) {
            aMap.put(i, "value" + i);
        }
        for (int i = 0; i < 10_000; i++) {
            aMap.remove(i);
        }
    }

    public static void performanceTest(Boolean print) throws Throwable {
        HashMap<Integer, String> hashMap = new HashMap<>();
        CustomHashMap<Integer, String> customHashMap = new CustomHashMap<>();

        ListPerformanceTests.measureComplexity(() -> addRemove(hashMap), "hashMap addRemove", print);
        ListPerformanceTests.measureComplexity(() -> addRemove(customHashMap), "customHashMap addRemove", print);

        ListPerformanceTests.measureComplexity(() -> addMillion(hashMap), "hashMap addMillion", print);
        ListPerformanceTests.measureComplexity(() -> addMillion(customHashMap), "customHashMap addMillion", print);

        ListPerformanceTests.measureComplexity(() -> getMillion(hashMap), "hashMap getMillion", print);
        ListPerformanceTests.measureComplexity(() -> getMillion(customHashMap), "customHashMap getMillion", print);

    }

    public static void main(String[] args) throws Throwable {
        //warm-up
        for (int i = 0; i <= 50; i++) {
            performanceTest(false);
        }

        performanceTest(true);
    }

    //the metrics on java hashMap and custom hashMap are almost the same
    //despite the fact that in custom hashMap we skipped red-black tree transformation that happens on java hash map when it has lots of collisions

//    tests results:
//    Operation: hashMap addRemove, exec time (millis): 0
//    Operation: hashMap addRemove, memory used (MB): 2.00
//    Operation: customHashMap addRemove, exec time (millis): 0
//    Operation: customHashMap addRemove, memory used (MB): 1.49
//    Operation: hashMap addMillion, exec time (millis): 100
//    Operation: hashMap addMillion, memory used (MB): 130.38
//    Operation: customHashMap addMillion, exec time (millis): 82
//    Operation: customHashMap addMillion, memory used (MB): 156.20
//    Operation: hashMap getMillion, exec time (millis): 9
//    Operation: hashMap getMillion, memory used (MB): 16.00
//    Operation: customHashMap getMillion, exec time (millis): 7
//    Operation: customHashMap getMillion, memory used (MB): 16.00
}
