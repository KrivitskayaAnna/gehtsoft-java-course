package hw05;

import com.google.common.collect.Lists;
import com.google.common.primitives.Shorts;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Multithreading {
    public static long sumWithParallelStream(int threadsCount) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(threadsCount));
        return IntStream.range(0, 100_000_000).parallel().sum();
    }

    @SneakyThrows
    public static long sumWithParallelThreads(int threadsCount) {
        short[] arr = new short[100_000_000];
        int numElementsPerChunk = arr.length / threadsCount;
        List<List<Short>> chunks = Lists.partition(Shorts.asList(arr), numElementsPerChunk);
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        try {
            for (int i = 0; i < threadsCount; i++) {
                List<Short> chunk = chunks.get(i);
                Future<Integer> future = executor.submit(() -> {
                    int sum = 0;
                    for (Short aShort : chunk) sum += aShort;
                    return sum;
                });
                futures.add(future);
            }
            int totalSum = 0;
            for (Future<Integer> future : futures) totalSum += future.get();
            return totalSum;
        } finally {
            executor.shutdown();
        }
    }

    public static long getExecutionTimeMillis(int threadsCount, Function<Integer, Long> func) {
        long startTime = System.nanoTime();
        func.apply(threadsCount);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000;
    }

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream fileOut = new PrintStream(new FileOutputStream("multithreading_results.txt"));
        System.setOut(fileOut);
        int[] threadCounts = {1, 10, 100, 1000};
        for (int threadsCount : threadCounts) {
            long executionStreamMillis = getExecutionTimeMillis(threadsCount, Multithreading::sumWithParallelStream);
            System.out.printf("Thread count: %d, method: %s, time: %d millis\n", threadsCount, "stream", executionStreamMillis);
            long executionThreadsMillis = getExecutionTimeMillis(threadsCount, Multithreading::sumWithParallelThreads);
            System.out.printf("Thread count: %d, method: %s, time: %d millis\n", threadsCount, "threads", executionThreadsMillis);
        }
    }
    //using parallel stream is always more efficient than threads, perhaps due to inner optimizations
    //efficiency grows as the number of threads grow at first
    //but after increasing threads from 100 to 1000, efficiency decreases
    //because gains from extra threads are too low compared to overhead of threads initialization and synchronization
    //also, only threads num equal to cpu cores num can be truly parallel in cpu-bound tasks such that

//    result from file:
//    Thread count: 1, method: stream, time: 33 millis
//    Thread count: 1, method: threads, time: 151 millis
//    Thread count: 10, method: stream, time: 16 millis
//    Thread count: 10, method: threads, time: 65 millis
//    Thread count: 100, method: stream, time: 15 millis
//    Thread count: 100, method: threads, time: 18 millis
//    Thread count: 1000, method: stream, time: 17 millis
//    Thread count: 1000, method: threads, time: 105 millis
}
