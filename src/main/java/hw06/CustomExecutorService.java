package hw06;

import hw03.CustomLinkedList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class CustomExecutorService implements ExecutorService {
    private int corePoolSize;
    private boolean useVirtualThreads;
    private BlockingQueue<Runnable> taskQueue;
    private final List<Thread> workerThreads;
    private boolean allowNewTasks = true;

    private void runTasks() {
        while (allowNewTasks || !taskQueue.isEmpty()) {
            Runnable task = taskQueue.poll();
            if (task != null) {
                task.run();
            }
        }
    }

    private Runnable pollForTasks() {
        return () -> runTasks();
    }

    public CustomExecutorService(int corePoolSize, boolean useVirtualThreads) {
        this.corePoolSize = corePoolSize;
        this.useVirtualThreads = useVirtualThreads;
        this.taskQueue = new TaskQueue();
        this.workerThreads = new ArrayList<>();
        for (int i = 0; i < corePoolSize; i++) {
            Thread.Builder worker;
            if (useVirtualThreads) {
                worker = Thread.ofVirtual();
            } else {
                worker = Thread.ofPlatform();
            }
            Thread thread = worker.start(pollForTasks());
            workerThreads.add(thread);

        }
    }

    @Override
    public void shutdown() {
        allowNewTasks = false;
    }

    @Override
    public List<Runnable> shutdownNow() {
        return List.of(); //TODO
    }

    @Override
    public boolean isShutdown() {
        return !allowNewTasks;
    }

    @Override
    public boolean isTerminated() {
        return !allowNewTasks; //TODO: what is the difference
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false; //TODO
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        Runnable runnable = () -> {
            try {
                task.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        return submit(runnable, null);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        if ((task != null) & (allowNewTasks)) {
            FutureTask<T> futureTask = new FutureTask<>(task, result);
            execute(task);
            return futureTask;
        } else if (!allowNewTasks) {
            throw new RejectedExecutionException("Pool was shut down");
        } else throw new NullPointerException("Command is null");
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
        if ((command != null) & (allowNewTasks)) {
            taskQueue.add(command);
        } else if (!allowNewTasks) {
            throw new RejectedExecutionException("Pool was shut down");
        } else throw new NullPointerException("Command is null");
    }

    private class TaskQueue implements BlockingQueue<Runnable> {
        private final ReentrantLock lock = new ReentrantLock();
        private final CustomLinkedList<Runnable> list;

        public TaskQueue() {
            this.list = new CustomLinkedList<>();
        }

        @Override
        public boolean add(Runnable runnable) {
            lock.lock();
            try {
                boolean taskAdded = list.add(runnable);
                return taskAdded;
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
            return false;
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
