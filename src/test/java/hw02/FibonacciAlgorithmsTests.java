package hw02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class FibonacciAlgorithmsTests {
    public static void measureComplexity(Integer n, Function<Integer, Long> func, Boolean print) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.nanoTime();
        long result = func.apply(n);
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long endTime = System.nanoTime();
        if (print) {
            System.out.println(result);
            System.out.println("Execution time: " + (endTime - startTime) + " nanos");
            System.out.println("Memory used: " + (memoryAfter - memoryBefore) + " bytes");
        }
    }

    public static void measureRecursiveComplexity(int n, Boolean print) {
        measureComplexity(n, FibonacciAlgorithms::fibonacciRecursive, print);
    }

    public static void measureMemoizedComplexity(int n, Boolean print) {
        measureComplexity(n, FibonacciAlgorithms::fibonacciMemoized, print);
    }

    public static void measureIterativeComplexity(int n, Boolean print) {
        measureComplexity(n, FibonacciAlgorithms::fibonacciIterative, print);
    }

    @Test
    void algorithmsProduceSameResults() {
        for (int i = 0; i <= 35; i++) {
            Long recursiveResult = FibonacciAlgorithms.fibonacciRecursive(i);
            Long memoizedResult = FibonacciAlgorithms.fibonacciMemoized(i);
            Long iterativeResult = FibonacciAlgorithms.fibonacciIterative(i);
            Assertions.assertTrue(recursiveResult.equals(memoizedResult) && memoizedResult.equals(iterativeResult));
        }
    }

    public static void main(String[] args) {
        //warm-up
        for (int i = 0; i < 50; i++) {
            measureRecursiveComplexity(20, false);
        }

        //recursive tests
        measureRecursiveComplexity(10, true); //6193 nanos, 0 bytes
        measureRecursiveComplexity(20, true); //80786 nanos, 2648 bytes
        measureRecursiveComplexity(30, true); //2999332 nanos, 2648 bytes
        measureRecursiveComplexity(35, true); //34257921 nanos, 2648 bytes
        measureRecursiveComplexity(45, true); //3864592025 nanos, 1172632 bytes
        //in recursive implementation, time usage raises exponentially and computation for n > 45 seems to hang forever
        //memory usage, however, does not seem to be linear, with sharp increase on n >= 45

        //warm-up
        for (int i = 0; i < 50; i++) {
            measureMemoizedComplexity(50, false);
        }

        //memoized tests
        measureMemoizedComplexity(10, true); //15223 nanos, 2672 bytes
        measureMemoizedComplexity(20, true); //51417 nanos, 2672 bytes
        measureMemoizedComplexity(30, true); //25462 nanos, 2672 bytes
        measureMemoizedComplexity(35, true); //87455 nanos, 5328 bytes
        measureMemoizedComplexity(45, true); //42260 nanos, 5328 bytes
        measureMemoizedComplexity(150, true); //211410 nanos, 15920 bytes
        //in memoized implementation, both time and memory usage raise linearly

        //warm-up
        for (int i = 0; i < 50; i++) {
            measureIterativeComplexity(50, false);
        }

        //iterative tests
        measureIterativeComplexity(10, true); //13424 nanos, 2648 bytes
        measureIterativeComplexity(20, true); //32265 nanos, 2648 bytes
        measureIterativeComplexity(30, true); //18962 nanos, 2648 bytes
        measureIterativeComplexity(35, true); //19604 nanos, 2648 bytes
        measureIterativeComplexity(45, true); //19661 nanos, 2648 bytes
        measureIterativeComplexity(150, true); //41102 nanos, 5296 bytes
        measureIterativeComplexity(500, true); //77044 nanos, 18536 bytes
        measureIterativeComplexity(600, true); //83820 nanos, 20960 bytes
        measureIterativeComplexity(700, true); //87107 nanos, 26656 bytes
        measureIterativeComplexity(800, true); //120694 nanos, 29704 bytes
        measureIterativeComplexity(900, true); //139749 nanos, 32752 bytes
        measureIterativeComplexity(1000, true); //148985 nanos, 35800 bytes
        //in iterative implementation, time and memory usage raise slowly with an increase of n
    }
}
