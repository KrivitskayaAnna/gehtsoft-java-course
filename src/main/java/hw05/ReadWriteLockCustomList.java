package hw05;

import hw01.CustomList;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class ReadWriteLockCustomList<T> extends CustomList<T> {
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    private final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private <T1> T1 withWriteLock(Supplier<T1> function) {
        writeLock.lock();
        T1 result = function.get();
        writeLock.unlock();
        return result;
    }

    private void withWriteLock(Runnable function) {
        writeLock.lock();
        function.run();
        writeLock.unlock();
    }

    @Override
    public boolean add(T t) {
        return withWriteLock(() -> super.add(t));
    }

    @Override
    public boolean remove(Object o) {
        return withWriteLock(() -> super.remove(o));
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return withWriteLock(() -> super.addAll(c));
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return withWriteLock(() -> super.addAll(index, c));
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return withWriteLock(() -> super.removeAll(c));
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return withWriteLock(() -> super.retainAll(c));
    }

    @Override
    public T set(int index, T element) {
        return withWriteLock(() -> super.set(index, element));
    }

    @Override
    public void add(int index, T element) {
        withWriteLock(() -> super.add(index, element));
    }

    @Override
    public T remove(int index) {
        return withWriteLock(() -> super.remove(index));
    }
}
