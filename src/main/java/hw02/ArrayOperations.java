package hw02;

import java.util.Arrays;

public class ArrayOperations {
    /**
     * Shift array elements using System.arraycopy
     */
    public static void shiftLeftSystemCopy(int[] array, int positions) {
        System.arraycopy(array, positions, array, 0, array.length - positions);
        for (int i = array.length - 1; i >= array.length - positions; i--) {
            array[i] = 0;
        }
    }

    /**
     * Shift array elements using manual for loop
     */
    public static void shiftLeftManualLoop(int[] array, int positions) {
        if (array.length > 1) {
            for (int i = 0; i < array.length - positions; i++) {
                array[i] = array[i + positions];
            }
            for (int i = array.length - 1; i >= array.length - positions; i--) {
                array[i] = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6};
        shiftLeftSystemCopy(array, 2);
        System.out.println(Arrays.toString(array));

        int[] array1 = {1, 2, 3, 4, 5, 6};
        shiftLeftManualLoop(array1, 2);
        System.out.println(Arrays.toString(array1));
    }
}
