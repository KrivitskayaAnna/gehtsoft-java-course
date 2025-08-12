package hw05;

import hw01.CustomList;

import java.util.Collection;
import java.util.function.Supplier;

public class SynchronizedCustomList<T> extends CustomList<T> {
    private final Object modifyLock = new Object();

    private <T1> T1 withSynchronized(Supplier<T1> function) {
        synchronized (modifyLock) {
            return function.get();
        }
    }

    private void withSynchronized(Runnable function) {
        synchronized (modifyLock) {
            function.run();
        }
    }

    @Override
    public boolean add(T t) {
        return withSynchronized(() -> super.add(t));
    }

    @Override
    public boolean remove(Object o) {
        return withSynchronized(() -> super.remove(o));
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return withSynchronized(() -> super.addAll(c));
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return withSynchronized(() -> super.addAll(index, c));
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return withSynchronized(() -> super.removeAll(c));
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return withSynchronized(() -> super.retainAll(c));
    }

    @Override
    public T set(int index, T element) {
        return withSynchronized(() -> super.set(index, element));
    }

    @Override
    public void add(int index, T element) {
        withSynchronized(() -> super.add(index, element));
    }

    @Override
    public T remove(int index) {
        return withSynchronized(() -> super.remove(index));
    }
}
