package hw05;

import java.util.ArrayList;

public class PlatformThreads {
    public static void createThreads(Thread.Builder threadBuilder, int threadNum, int sleepMillis) throws InterruptedException {
        System.gc();
        Thread.sleep(1000);
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            Thread thread = threadBuilder.start(() -> {
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            threads.add(thread);
        }
        for (Thread thread : threads) thread.join();
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) / 1_000_000 + " milliseconds");
        System.out.println("Memory used: " + (memoryAfter - memoryBefore) + " bytes");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Physical");
        createThreads(Thread.ofPlatform(), 8000, 200);
        System.out.println("Virtual");
        createThreads(Thread.ofVirtual(), 8000, 200);
    }
    //though virtual threads executed faster, they took much more memory than physical threads
    //probably virtual threads pre-allocate memory while physical use it only when needed

//    Physical
//    Execution time: 872 milliseconds
//    Memory used: 3_271_408 bytes
//    Virtual
//    Execution time: 257 milliseconds
//    Memory used: 16_090_064 bytes

//    1_000_000 threads
//    Physical
//    (finishes never)
//    Virtual
//    Execution time: 4223 milliseconds
//    Memory used: 857574944 bytes
}
