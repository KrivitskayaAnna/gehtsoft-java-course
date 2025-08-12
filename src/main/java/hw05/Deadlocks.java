package hw05;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Deadlocks {
    public static void doWithReentrantLock(ReentrantLock lock1, ReentrantLock lock2) {
        System.out.println("Started thread");
        lock1.lock();
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock2.lock();
        lock1.unlock();
        lock2.unlock();
        System.out.println("Finished thread");
    }

    public static void firstDeadlock() throws InterruptedException {
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();
        Thread thread1 = Thread.ofVirtual().start(() ->
                doWithReentrantLock(lock1, lock2)
        );
        Thread thread2 = Thread.ofVirtual().start(() ->
                doWithReentrantLock(lock2, lock1)
        );
        thread1.join();
        thread2.join();
    }

    public static void doWithSycnronized(Object lock1, Object lock2) {
        System.out.println("Started thread");
        synchronized (lock1) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock2) {
                System.out.println("Thread doing smth");
            }
        }
        System.out.println("Finished thread");
    }

    public static void secondDeadlock() throws InterruptedException {
        Object lock1 = new Object();
        Object lock2 = new Object();
        Thread thread1 = Thread.ofVirtual().start(() ->
                doWithSycnronized(lock1, lock2)
        );
        Thread thread2 = Thread.ofVirtual().start(() ->
                doWithSycnronized(lock2, lock1));
        thread1.join();
        thread2.join();
    }

    public static void thirdDeadlock(Object lock, int depth) throws InterruptedException {
        if (depth > 0) {
            Thread thread = Thread.ofVirtual().start(() -> {
                System.out.println("Started thread");
                synchronized (lock) {
                    try {
                        thirdDeadlock(lock, depth - 1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Finished thread");
            });
            thread.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        firstDeadlock();

        secondDeadlock();

        Object lock = new Object();
        thirdDeadlock(lock, 2);
    }
}
