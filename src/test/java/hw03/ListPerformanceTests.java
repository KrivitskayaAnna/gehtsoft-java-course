package hw03;

import hw01.CustomList;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListPerformanceTests {
    public static void measureComplexity(Executable executable, String listType, Boolean print) throws Throwable {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.currentTimeMillis();
        executable.execute();
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long endTime = System.currentTimeMillis();
        if (print) {
            System.out.printf("Operation: %s, exec time (millis): %d\n", listType, (endTime - startTime));
            System.out.printf("Operation: %s, memory used (MB): %.2f\n", listType, (float) (memoryAfter - memoryBefore) / (1024 * 1024));
        }
    }

    public static void addMillion(List<Integer> aList) {
        for (int i = 0; i < 1_000_000; i++) {
            aList.add(i);
        }
    }

    public static void addRemove(List<Integer> aList) {
        for (int i = 0; i < 10_000; i++) {
            aList.add(i);
        }
        for (int i = 0; i < 10_000; i++) {
            aList.remove(0);
        }
    }

    public static void performanceTest(Boolean print) throws Throwable {
        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        CustomList<Integer> customList = new CustomList<>();
        CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();

        measureComplexity(() -> addRemove(arrayList), "arrayList addRemove", print);
        measureComplexity(() -> addRemove(customList), "customList addRemove", print);
        measureComplexity(() -> addRemove(linkedList), "linkedList addRemove", print);
        measureComplexity(() -> addRemove(customLinkedList), "customLinkedList addRemove", print);

        measureComplexity(() -> addMillion(arrayList), "arrayList addMillion", print);
        measureComplexity(() -> addMillion(customList), "customList addMillion", print);
        measureComplexity(() -> addMillion(linkedList), "linkedList addMillion", print);
        measureComplexity(() -> addMillion(customLinkedList), "customLinkedList addMillion", print);
    }

    public static void main(String[] args) throws Throwable {
        //warm-up
        for (int i = 0; i <= 50; i++) {
            performanceTest(false);
        }

        performanceTest(true);
    }

    //the java dynamic array is the most performant in second scenario, while the linked list is better in the first test
    //the custom implementation of list based on dynamic array (hw01) has worse performance in first case (faster remove in java array)
    //the custom linked list is better on adding a million of elements than the java linked list (though the code of two methods is almost the same)

//    tests result:
//    Operation: arrayList addRemove, exec time (millis): 1
//    Operation: arrayList addRemove, memory used (MB): 1.00
//    Operation: customList addRemove, exec time (millis): 31
//    Operation: customList addRemove, memory used (MB): 37.10

//    Operation: linkedList addRemove, exec time (millis): 0
//    Operation: linkedList addRemove, memory used (MB): 1.00
//    Operation: customLinkedList addRemove, exec time (millis): 0
//    Operation: customLinkedList addRemove, memory used (MB): 0.46

//    Operation: arrayList addMillion, exec time (millis): 15
//    Operation: arrayList addMillion, memory used (MB): 32.56
//    Operation: customList addMillion, exec time (millis): 8
//    Operation: customList addMillion, memory used (MB): 31.60

//    Operation: linkedList addMillion, exec time (millis): 53
//    Operation: linkedList addMillion, memory used (MB): 41.63
//    Operation: customLinkedList addMillion, exec time (millis): 18
//    Operation: customLinkedList addMillion, memory used (MB): 39.00
}
