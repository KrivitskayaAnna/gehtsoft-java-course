package hw02;

import java.util.HashMap;
import java.util.List;

public class FibonacciAlgorithms {
    /**
     * Recursive implementation of Fibonacci sequence
     * Time Complexity: exponential, each time num of operations is 2^n where n is depth of tree of calls
     * Space Complexity: linear, each time we append one frame in recursion stack of calls
     * <p>
     * Explanation: Each call branches into two recursive calls, creating
     * a binary tree of calls with height n. The same subproblems are
     * solved multiple times, leading to exponential time complexity.
     */
    public static long fibonacciRecursive(int n) {
        if (n < 0) throw new IllegalArgumentException("n should be grater than 0");
        else if (n <= 1) return n;
        else return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    /**
     * Memoized implementation of Fibonacci sequence
     * Time Complexity: linear, compute each new level of tree calls just once
     * Space Complexity: linear, preserve each fibonacci number into cache and each frame in recursion stack of calls
     * <p>
     * Explanation: By caching intermediate results, we avoid
     * redundant calculations. Each number from 0 to n is
     * calculated exactly once.
     */
    public static long fibonacciMemoized(int n) {
        HashMap<Integer, Long> cache = new HashMap<>();
        cache.put(0, 0L);
        cache.put(1, 1L);
        return fibonacciWithCache(n, cache);
    }

    private static long fibonacciWithCache(int n, HashMap<Integer, Long> cache) {
        if (n < 0) throw new IllegalArgumentException("n should be grater than 0");
        else if (cache.containsKey(n)) return cache.get(n);
        else {
            long result = fibonacciWithCache(n - 1, cache) + fibonacciWithCache(n - 2, cache);
            cache.put(n, result);
            return result;
        }
    }

    /**
     * Iterative implementation of Fibonacci sequence
     * Time Complexity: O(n) - single loop from 0 to n
     * Space Complexity: O(n) - saving n numbers in array
     * <p>
     * Explanation: Uses bottom-up approach with only two variables
     * to track previous values, eliminating recursion overhead.
     */
    public static long fibonacciIterative(int n) {
        Long[] values = List.of(0L, 1L).toArray(new Long[n + 1]);
        if (n < 0) throw new IllegalArgumentException("n should be grater than 0");
        else if (n <= 1) return values[n];
        else {
            for (int i = 2; i <= n; i++) {
                values[i] = values[i - 1] + values[i - 2];
            }
            return values[n];
        }
    }
}