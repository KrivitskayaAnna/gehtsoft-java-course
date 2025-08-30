package hw06;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class CustomExecutorService implements ExecutorService {
    private final BlockingQueue<Runnable> taskQueue;
    private AtomicInteger numTasksRunning;
    private final List<Thread> workerThreads;
    private boolean allowNewTasks = true;

    private void pollTasks() {
        while (allowNewTasks || !taskQueue.isEmpty()) {
            Runnable task = taskQueue.poll();
            if (task != null) {
                System.out.printf("Worker %s start running task\n", Thread.currentThread().getName());
                task.run();
                System.out.printf("Worker %s finished running task\n", Thread.currentThread().getName());
            }
        }
    }

    private Runnable pollForTasks() {
        return () -> pollTasks();
    }

    public CustomExecutorService(int corePoolSize, boolean useVirtualThreads) {
        this.taskQueue = new TaskQueue();
        this.workerThreads = new ArrayList<>();
        this.numTasksRunning = new AtomicInteger(0);
        for (int i = 0; i < corePoolSize; i++) {
            Thread.Builder worker;
            if (useVirtualThreads) {
                worker = Thread.ofVirtual();
            } else {
                worker = Thread.ofPlatform();
            }
            Thread thread = worker.name("worker-" + i).start(pollForTasks());
            workerThreads.add(thread);
        }
    }

    @Override
    public void shutdown() {
        allowNewTasks = false;
    }

    @Override
    public List<Runnable> shutdownNow() {
        allowNewTasks = false;
        List<Runnable> existingTasks = new ArrayList<>();
        for (Thread worker : workerThreads) {
            worker.interrupt();
        }
        while (!taskQueue.isEmpty()) {
            existingTasks.add(taskQueue.poll());
        }
        numTasksRunning.set(0);
        return existingTasks;
    }

    @Override
    public boolean isShutdown() {
        return !allowNewTasks;
    }

    @Override
    public boolean isTerminated() {
        return (!allowNewTasks && (numTasksRunning.get() == 0) && (taskQueue.isEmpty()));
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long endTime = System.nanoTime() + unit.toNanos(timeout);
        while (!isTerminated()) {
            if (System.nanoTime() > endTime) {
                return false;
            }
        }
        return true;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        FutureTask<T> futureTask = new FutureTask<>(task);
        execute(futureTask);
        return futureTask;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        FutureTask<T> futureTask = new FutureTask<>(task, result);
        execute(futureTask);
        return futureTask;
    }

    @Override
    public Future<?> submit(Runnable task) {
        return submit(task, null);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return List.of();
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return List.of();
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void execute(Runnable command) {
        if (!allowNewTasks) {
            throw new RejectedExecutionException("Pool was shut down");
        }
        if (command == null) {
            throw new NullPointerException("Command is null");
        }
        taskQueue.add(command);
    }

    private class TaskQueue implements BlockingQueue<Runnable> {
        private final ReentrantLock lock = new ReentrantLock();
        private final LinkedList<Runnable> list;

        public TaskQueue() {
            this.list = new LinkedList<>();
        }

        @Override
        public boolean add(Runnable runnable) {
            lock.lock();
            try {
                return list.add(runnable);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public boolean offer(Runnable runnable) {
            return false;
        }

        @Override
        public Runnable remove() {
            return null;
        }

        @Override
        public Runnable poll() {
            lock.lock();
            try {
                return list.pollFirst();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public Runnable element() {
            return null;
        }

        @Override
        public Runnable peek() {
            return null;
        }

        @Override
        public void put(Runnable runnable) throws InterruptedException {

        }

        @Override
        public boolean offer(Runnable runnable, long timeout, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public Runnable take() throws InterruptedException {
            return null;
        }

        @Override
        public Runnable poll(long timeout, TimeUnit unit) throws InterruptedException {
            return null;
        }

        @Override
        public int remainingCapacity() {
            return 0;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Runnable> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            lock.lock();
            try {
                return list.isEmpty();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Runnable> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public int drainTo(Collection<? super Runnable> c) {
            return 0;
        }

        @Override
        public int drainTo(Collection<? super Runnable> c, int maxElements) {
            return 0;
        }
    }
}
