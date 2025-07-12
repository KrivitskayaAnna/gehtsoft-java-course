package hw02;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class ArrayOperationsTests {
    public static void measureComplexity(int[] array, Integer positions, BiConsumer<int[], Integer> func, String atype) {
        long startTime = System.currentTimeMillis();
        func.accept(array, positions);
        long endTime = System.currentTimeMillis();
        System.out.printf("Arr length: %d, positions: %d, type: '%s', time (millis): %d%n", array.length, positions, atype, (endTime - startTime));
    }

    public static void measureSystem(int[] array, Integer positions) {
        measureComplexity(array, positions, ArrayOperations::shiftLeftSystemCopy, "system");
    }

    public static void measureManual(int[] array, Integer positions) {
        measureComplexity(array, positions, ArrayOperations::shiftLeftManualLoop, "manual");
    }

    public static int[] createArray(int n) {
        int[] array = new int[n];
        Arrays.fill(array, 1);
        return array;
    }

    public static void main(String[] args) {
        int[] sizes = {1_000, 10_000, 100_000, 1_000_000, 10_000_000, 100_000_000, 900_000_000};
        int[] shifts = {1, 10, 100, 1_000};
        int[][] arrOfArray = new int[sizes.length][];

        for (int i = 0; i < sizes.length; i++) {
            arrOfArray[i] = createArray(sizes[i]);
        }

        for (int[] ints : arrOfArray) {
            for (int shift : shifts) {
                measureSystem(ints, shift);
                measureManual(ints, shift);
            }
        }

        //the difference in time raises with the raise of array size
        //the impact of shift is insignificant
        //the significant differences in time starts from arrays length >= 1_000_000
        //in the worst case (arr length: 900_000_000, positions: 1), system copy is 167 millis, or 40%, faster than manual copy
        //system copy complexity seems to be linear (time is x10 for arrays of x9 size)
        //manual shift complexity looks like being linear, too, though less optimized than native system copy (time is x14 for arrays of x9 size)
        //below is execution results:
//        Arr length: 1000, positions: 1, type: 'system', time (millis): 0
//        Arr length: 1000, positions: 1, type: 'manual', time (millis): 0
//        Arr length: 1000, positions: 10, type: 'system', time (millis): 0
//        Arr length: 1000, positions: 10, type: 'manual', time (millis): 0
//        Arr length: 1000, positions: 100, type: 'system', time (millis): 0
//        Arr length: 1000, positions: 100, type: 'manual', time (millis): 0
//        Arr length: 1000, positions: 1000, type: 'system', time (millis): 1
//        Arr length: 1000, positions: 1000, type: 'manual', time (millis): 0
//        Arr length: 10000, positions: 1, type: 'system', time (millis): 0
//        Arr length: 10000, positions: 1, type: 'manual', time (millis): 0
//        Arr length: 10000, positions: 10, type: 'system', time (millis): 0
//        Arr length: 10000, positions: 10, type: 'manual', time (millis): 0
//        Arr length: 10000, positions: 100, type: 'system', time (millis): 0
//        Arr length: 10000, positions: 100, type: 'manual', time (millis): 1
//        Arr length: 10000, positions: 1000, type: 'system', time (millis): 0
//        Arr length: 10000, positions: 1000, type: 'manual', time (millis): 0
//        Arr length: 100000, positions: 1, type: 'system', time (millis): 0
//        Arr length: 100000, positions: 1, type: 'manual', time (millis): 1
//        Arr length: 100000, positions: 10, type: 'system', time (millis): 0
//        Arr length: 100000, positions: 10, type: 'manual', time (millis): 0
//        Arr length: 100000, positions: 100, type: 'system', time (millis): 0
//        Arr length: 100000, positions: 100, type: 'manual', time (millis): 0
//        Arr length: 100000, positions: 1000, type: 'system', time (millis): 0
//        Arr length: 100000, positions: 1000, type: 'manual', time (millis): 0
//        Arr length: 1000000, positions: 1, type: 'system', time (millis): 1
//        Arr length: 1000000, positions: 1, type: 'manual', time (millis): 3
//        Arr length: 1000000, positions: 10, type: 'system', time (millis): 0
//        Arr length: 1000000, positions: 10, type: 'manual', time (millis): 2
//        Arr length: 1000000, positions: 100, type: 'system', time (millis): 1
//        Arr length: 1000000, positions: 100, type: 'manual', time (millis): 1
//        Arr length: 1000000, positions: 1000, type: 'system', time (millis): 1
//        Arr length: 1000000, positions: 1000, type: 'manual', time (millis): 0
//        Arr length: 10000000, positions: 1, type: 'system', time (millis): 5
//        Arr length: 10000000, positions: 1, type: 'manual', time (millis): 5
//        Arr length: 10000000, positions: 10, type: 'system', time (millis): 4
//        Arr length: 10000000, positions: 10, type: 'manual', time (millis): 3
//        Arr length: 10000000, positions: 100, type: 'system', time (millis): 4
//        Arr length: 10000000, positions: 100, type: 'manual', time (millis): 4
//        Arr length: 10000000, positions: 1000, type: 'system', time (millis): 3
//        Arr length: 10000000, positions: 1000, type: 'manual', time (millis): 5
//        Arr length: 100000000, positions: 1, type: 'system', time (millis): 37
//        Arr length: 100000000, positions: 1, type: 'manual', time (millis): 38
//        Arr length: 100000000, positions: 10, type: 'system', time (millis): 36
//        Arr length: 100000000, positions: 10, type: 'manual', time (millis): 38
//        Arr length: 100000000, positions: 100, type: 'system', time (millis): 53
//        Arr length: 100000000, positions: 100, type: 'manual', time (millis): 59
//        Arr length: 100000000, positions: 1000, type: 'system', time (millis): 37
//        Arr length: 100000000, positions: 1000, type: 'manual', time (millis): 39
//        Arr length: 900000000, positions: 1, type: 'system', time (millis): 399
//        Arr length: 900000000, positions: 1, type: 'manual', time (millis): 566
//        Arr length: 900000000, positions: 10, type: 'system', time (millis): 481
//        Arr length: 900000000, positions: 10, type: 'manual', time (millis): 576
//        Arr length: 900000000, positions: 100, type: 'system', time (millis): 373
//        Arr length: 900000000, positions: 100, type: 'manual', time (millis): 350
//        Arr length: 900000000, positions: 1000, type: 'system', time (millis): 322
//        Arr length: 900000000, positions: 1000, type: 'manual', time (millis): 360
    }
}
