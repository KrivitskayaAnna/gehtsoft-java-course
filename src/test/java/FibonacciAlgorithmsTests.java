import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class FibonacciAlgorithmsTests {
    public static void measureComplexity(Integer n, Function<Integer, Long> func, Boolean print) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.currentTimeMillis();
        long result = func.apply(n);
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long endTime = System.currentTimeMillis();
        if (print) {
            System.out.println(result);
            System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
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
        measureRecursiveComplexity(10, true); //0 milliseconds, 0 bytes
        measureRecursiveComplexity(20, true);  //1 milliseconds, 2648 bytes
        measureRecursiveComplexity(30, true); //4 milliseconds, 2648 bytes
        measureRecursiveComplexity(35, true); //37 milliseconds, 2648 bytes
        measureRecursiveComplexity(45, true); //3895 milliseconds, 1172632 bytes
        //in recursive implementation, time usage raises exponentially and computation for n > 45 seems to hang forever
        //memory usage, however, does not seem to be linear, with sharp increase on n >= 45

        //warm-up
        for (int i = 0; i < 50; i++) {
            measureMemoizedComplexity(50, false);
        }

        //memoized tests
        measureMemoizedComplexity(10, true); //0 milliseconds, 0 bytes
        measureMemoizedComplexity(20, true); //0 milliseconds, 2648 bytes
        measureMemoizedComplexity(30, true); //0 milliseconds, 2648 bytes
        measureMemoizedComplexity(35, true); //0 milliseconds, 2648 bytes
        measureMemoizedComplexity(45, true); //0 milliseconds, 2648 bytes
        measureMemoizedComplexity(150, true); //1 milliseconds, 10584 bytes
        measureMemoizedComplexity(5000, true); //2 milliseconds, 809296 bytes
        //in memoized implementation, both time and memory usage raise slowly, linearly

        //warm-up
        for (int i = 0; i < 50; i++) {
            measureIterativeComplexity(50, false);
        }

        //iterative tests
        measureIterativeComplexity(10, true); //0 milliseconds, 2648 bytes
        measureIterativeComplexity(20, true); //0 milliseconds, 2648 bytes
        measureIterativeComplexity(30, true); //0 milliseconds, 2648 bytes
        measureIterativeComplexity(35, true); //0 milliseconds, 2648 bytes
        measureIterativeComplexity(45, true); //1 milliseconds, 2648 bytes
        measureIterativeComplexity(150, true); //0 milliseconds, 5296 bytes
        measureIterativeComplexity(500, true); //0 milliseconds, 18536 bytes
        //in iterative implementation, time seems to be constant and memory usage raises with an increase of n
        //it doesn't meet our expectations (time raising linearly and memory being constant)
    }
}
