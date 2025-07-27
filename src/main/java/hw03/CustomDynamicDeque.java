package hw03;

import java.util.*;

public class CustomDynamicDeque<T> implements Deque<T> {
    private final int initSize = 10;
    private Object[] elements;
    private int firstElementIdx;
    private int lastElementIdx;
    private int size;

    public CustomDynamicDeque() {
        elements = new Object[initSize];
        firstElementIdx = 0;
        lastElementIdx = 0;
        size = 0;
    }

    private void resize() {
        int newSize = elements.length * 2;
        Object[] newElements = new Object[newSize];
        int numElementsTillEnd = elements.length - firstElementIdx;
        System.arraycopy(elements, firstElementIdx, newElements, 0, numElementsTillEnd);
        System.arraycopy(elements, 0, newElements, numElementsTillEnd, size - numElementsTillEnd);
        firstElementIdx = 0;
        lastElementIdx = size;
        elements = newElements;
    }

    private int idxToLeft(int idx) {
        return (idx - 1 + elements.length) % elements.length;
    }

    private int idxToRight(int idx) {
        return (idx + 1) % elements.length;
    }

    @Override
    public void addFirst(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot add null");
        }
        if (size == elements.length) {
            resize();
        }
        firstElementIdx = idxToLeft(firstElementIdx);
        elements[firstElementIdx] = t;
        size++;
    }

    @Override
    public void addLast(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot add null");
        }
        if (size == elements.length) {
            resize();
        }
        elements[lastElementIdx] = t;
        lastElementIdx = idxToRight(lastElementIdx);
        size++;
    }

    @Override
    public boolean offerFirst(T t) {
        addFirst(t);
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        addLast(t);
        return true;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        T avalue = (T) elements[firstElementIdx];
        elements[firstElementIdx] = null;
        firstElementIdx = idxToRight(firstElementIdx);
        size--;
        return avalue;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        int lastFilledIdx = idxToLeft(lastElementIdx);
        T avalue = (T) elements[lastFilledIdx];
        elements[lastFilledIdx] = null;
        lastElementIdx = lastFilledIdx;
        size--;
        return avalue;
    }

    @Override
    public T pollFirst() {
        return removeFirst();
    }

    @Override
    public T pollLast() {
        return removeLast();
    }

    @Override
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        return peekFirst();
    }

    @Override
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        return peekLast();
    }

    @Override
    public T peekFirst() {
        return (T) elements[firstElementIdx];
    }

    @Override
    public T peekLast() {
        int lastFilledIdx = idxToLeft(lastElementIdx);
        return (T) elements[lastFilledIdx];
    }

    private boolean removeIdx(int currentIdx) {
        while (currentIdx != lastElementIdx) {
            int nextIdx = idxToRight(currentIdx);
            elements[currentIdx] = elements[nextIdx];
            currentIdx = nextIdx;
        }
        int lastFilledIdx = idxToLeft(lastElementIdx);
        elements[lastFilledIdx] = null;
        lastElementIdx = lastFilledIdx;
        if (currentIdx == firstElementIdx) {
            firstElementIdx = idxToRight(firstElementIdx);
        }
        size--;
        return true;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (o == null) {
            throw new NullPointerException("Cannot remove null");
        }
        int currentIdx = firstElementIdx;
        for (int idx = 0; idx < size; idx++) {
            if (Objects.equals(elements[currentIdx], o)) {
                return removeIdx(currentIdx);
            }
            currentIdx = idxToRight(currentIdx);
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            throw new NullPointerException("Cannot remove null");
        }
        int currentIdx = lastElementIdx;
        for (int idx = size - 1; idx >= 0; idx--) {
            if (Objects.equals(elements[currentIdx], o)) {
                return removeIdx(currentIdx);
            }
            currentIdx = idxToLeft(currentIdx);
        }
        return false;
    }

    @Override
    public boolean add(T t) {
        addLast(t);
        return true;
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
        return getFirst();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T el : c) {
            addLast(el);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        for (Object el : c) {
            if (removeFirstOccurrence(el)) result = true;
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Cannot retail null collection");
        }
        boolean overallResult = false;
        int currentIdx = firstElementIdx;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[currentIdx])) {
                boolean result = removeIdx(currentIdx);
                if (result) overallResult = true;
            }
            currentIdx = idxToRight(currentIdx);
        }
        return overallResult;
    }

    @Override
    public void clear() {
        elements = new Object[initSize];
        firstElementIdx = 0;
        lastElementIdx = 0;
        size = 0;
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
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object el : c) {
            if (!contains(el)) return false;
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException("Cannot contain null");
        }
        int currentIdx = firstElementIdx;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[currentIdx], o)) {
                return true;
            }
            currentIdx = idxToRight(currentIdx);
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        if (firstElementIdx < lastElementIdx) {
            System.arraycopy(elements, firstElementIdx, newArray, 0, size);
        } else {
            int numElementsTillEnd = elements.length - firstElementIdx;
            System.arraycopy(elements, firstElementIdx, newArray, 0, numElementsTillEnd);
            System.arraycopy(elements, 0, newArray, numElementsTillEnd, size - numElementsTillEnd);
        }
        return newArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        Object[] newArray = toArray();
        T1[] typedArray;
        if (a.length < size) {
            typedArray = (T1[]) (new Object[size]);
        } else {
            typedArray = a;
        }
        System.arraycopy(newArray, 0, typedArray, 0, size);
        return typedArray;
    }

    @Override
    public Iterator<T> descendingIterator() {
        return null;
    }
}
