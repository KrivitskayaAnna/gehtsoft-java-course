package hw05;

import hw01.CustomList;

import java.util.Collection;

public class SynchronizedCustomList<T> extends CustomList<T> {
    private final Object modifyLock = new Object();

    @Override
    public boolean add(T t) {
        synchronized (modifyLock) {
            return super.add(t);
        }
    }

    @Override
    public boolean remove(Object o) {
        synchronized (modifyLock) {
            return super.remove(o);
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        synchronized (modifyLock) {
            return super.addAll(c);
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        synchronized (modifyLock) {
            return super.addAll(index, c);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        synchronized (modifyLock) {
            return super.removeAll(c);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        synchronized (modifyLock) {
            return super.retainAll(c);
        }
    }

    @Override
    public T set(int index, T element) {
        synchronized (modifyLock) {
            return super.set(index, element);
        }
    }

    @Override
    public void add(int index, T element) {
        synchronized (modifyLock) {
            super.add(index, element);
        }
    }

    @Override
    public T remove(int index) {
        synchronized (modifyLock) {
            return super.remove(index);
        }
    }
}
