package hw03;

import hw01.CustomList;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomDynamicDeque<T> implements Deque<T> {
    private final CustomList<T> list;

    public CustomDynamicDeque() {
        this.list = new CustomList<>();
    }

    @Override
    public void addFirst(T t) {
        list.add(0, t);
    }

    @Override
    public void addLast(T t) {
        list.add(size() - 1, t);
    }

    @Override
    public boolean offerFirst(T t) {
        addFirst(t);
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        return list.add(t);
    }

    @Override
    public T removeFirst() {
        return list.remove(0);
    }

    @Override
    public T removeLast() {
        return list.remove(size() - 1);
    }

    @Override
    public T pollFirst() {
        if (isEmpty()) return null;
        return removeFirst();
    }

    @Override
    public T pollLast() {
        if (isEmpty()) return null;
        return removeLast();
    }

    @Override
    public T getFirst() {
        return list.get(0);
    }

    @Override
    public T getLast() {
        return list.get(size() - 1);
    }

    @Override
    public T peekFirst() {
        if (isEmpty()) return null;
        return getFirst();
    }

    @Override
    public T peekLast() {
        if (isEmpty()) return null;
        return getLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (o != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == o) {
                    list.remove(i);
                    return true;
                }
            }
        } else {
            throw new NullPointerException("Cannot remove null from a list");
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (o != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (list.get(i) == o) {
                    list.remove(i);
                    return true;
                }
            }
        } else {
            throw new NullPointerException("Cannot remove null from a list");
        }
        return false;
    }

    @Override
    public boolean add(T t) {
        return list.add(t);
    }

    @Override
    public boolean offer(T t) {
        return offerLast(t);
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return peek();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void push(T t) {
        addFirst(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    @Override
    public Iterator<T> descendingIterator() {
        return null;
    }
}
