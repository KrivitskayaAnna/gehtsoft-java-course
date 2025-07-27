package hw03;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class CustomLinkedDeque<T> implements Deque<T> {
    private final CustomLinkedList<T> list;

    public CustomLinkedDeque() {
        this.list = new CustomLinkedList<>();
    }


    @Override
    public void addFirst(T t) {
        list.addFirst(t);
    }

    @Override
    public void addLast(T t) {
        list.addLast(t);
    }

    @Override
    public boolean offerFirst(T t) {
        return list.offerFirst(t);
    }

    @Override
    public boolean offerLast(T t) {
        return list.offerLast(t);
    }

    @Override
    public T removeFirst() {
        return list.removeFirst();
    }

    @Override
    public T removeLast() {
        return list.removeLast();
    }

    @Override
    public T pollFirst() {
        return list.pollFirst();
    }

    @Override
    public T pollLast() {
        return list.pollLast();
    }

    @Override
    public T getFirst() {
        return list.getFirst();
    }

    @Override
    public T getLast() {
        return list.getLast();
    }

    @Override
    public T peekFirst() {
        return list.peekFirst();
    }

    @Override
    public T peekLast() {
        return list.peekLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return list.removeFirstOccurrence(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return list.removeLastOccurrence(o);
    }

    @Override
    public boolean add(T t) {
        return list.add(t);
    }

    @Override
    public boolean offer(T t) {
        return list.offer(t);
    }

    @Override
    public T remove() {
        return list.remove();
    }

    @Override
    public T poll() {
        return list.poll();
    }

    @Override
    public T element() {
        return list.element();
    }

    @Override
    public T peek() {
        return list.peek();
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
        list.push(t);
    }

    @Override
    public T pop() {
        return list.pop();
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
        return null;
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
