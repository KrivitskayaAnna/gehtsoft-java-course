package hw03;

import java.util.*;

public class CustomLinkedList<T> implements List<T>, Deque<T> {
    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private int size = 0;

    private Node<T> first;

    private Node<T> last;

    public CustomLinkedList() {
    }

    @Override
    public boolean offerFirst(T t) {

        Node<T> previousFirst = first;
        Node<T> newFirst = new Node<>(null, t, previousFirst);
        first = newFirst;
        if (previousFirst != null) {
            previousFirst.prev = newFirst;
        } else {
            last = newFirst;
        }
        size++;
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        Node<T> previousLast = last;
        Node<T> newLast = new Node<>(previousLast, t, null);
        last = newLast;
        if (previousLast != null) {
            previousLast.next = newLast;
        } else {
            first = newLast;
        }
        size++;
        return true;
    }

    @Override
    public T pollFirst() {
        if (first == null) {
            return null;
        } else {
            Node<T> head = first;
            Node<T> second = first.next;
            if (second != null) {
                second.prev = null;
            }
            first = second;
            size--;
            return head.item;
        }
    }

    @Override
    public T pollLast() {
        if (last == null) {
            return null;
        } else {
            Node<T> tail = last;
            Node<T> preLast = last.prev;
            if (preLast != null) {
                preLast.next = null;
            }
            last = preLast;
            size--;
            return tail.item;
        }
    }

    @Override
    public T peekFirst() {
        if (first != null) {
            return first.item;
        } else return null;
    }

    @Override
    public T peekLast() {
        if (last != null) {
            return last.item;
        } else return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Node<T> element = last;
        while (element != null) {
            if (element.item == o) {
                if (element.prev != null) {
                    element.prev.next = element.next;
                }
                if (element.next != null) {
                    element.next.prev = element.prev;
                }
                if (element == last) {
                    last = element.prev;
                }
                if (element == first) {
                    first = element.next;
                }
                size--;
                return true;
            }
            element = element.prev;
        }
        return false;
    }

    @Override
    public boolean offer(T t) {
        return offerLast(t);
    }

    @Override
    public T remove() {
        if (first == null) {
            throw new NoSuchElementException("Empty list");
        }
        return pollFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return peekFirst();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public void push(T t) {
        offerFirst(t);
    }

    @Override
    public T pop() {
        return pollFirst();
    }

    @Override
    public Iterator<T> descendingIterator() {
        return null;
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
    public boolean contains(Object o) {
        Node<T> element = first;
        while (element != null) {
            if (element.item == o) {
                return true;
            }
            element = element.next;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node<T> element = first;
        int idx = 0;
        while (element != null) {
            arr[idx++] = element.item;
            element = element.next;
        }
        return arr;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        Object[] arr = toArray();
        if (a.length >= size) {
            System.arraycopy(arr, 0, a, 0, size);
            return a;
        } else {
            return (T1[]) arr;
        }
    }

    @Override
    public boolean add(T t) {
        return offerLast(t);
    }

    @Override
    public boolean remove(Object o) {
        Node<T> element = first;
        while (element != null) {
            if (element.item == o) {
                if (element.prev != null) {
                    element.prev.next = element.next;
                }
                if (element.next != null) {
                    element.next.prev = element.prev;
                }
                if (element == first) {
                    first = element.next;
                }
                if (element == last) {
                    last = element.prev;
                }
                size--;
                return true;
            }
            element = element.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object el : c) {
            if (!contains(el)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.size() == 0) {
            return false;
        }
        for (T el : c) {
            add(el);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        for (T el : c) {
            add(index, el);
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        if (c.size() == 0) {
            return false;
        }
        for (Object el : c) {
            boolean result = remove(el);
            if (result) {
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Node<T> element = first;
        boolean overallRemove = false;
        while (element != null) {
            if (!c.contains(element.item)) {
                overallRemove = true;
                remove(element.item);
            }
            element = element.next;
        }
        return overallRemove;
    }

    @Override
    public void clear() {
        first = null;
        size = 0;
        last = null;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("No such index in list");
        }
        Node<T> element = first;
        for (int i = 0; i < index; i++) {
            element = element.next;
        }
        return element.item;
    }

    @Override
    public T set(int index, T element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to list");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot set this index in list");
        }
        Node<T> el = first;
        for (int i = 0; i <= index; i++) {
            el = el.next;
        }
        T prevItem = el.item;
        el.item = element;
        return prevItem;
    }

    @Override
    public void add(int index, T element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to list");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Cannot add this index to list");
        }
        Node<T> el = first;
        for (int i = 0; i < index; i++) {
            el = el.next;
        }
        Node<T> newEl = new Node<>(el.prev, element, el);
        if (el.prev != null) {
            el.prev.next = newEl;
        }
        size++;
        el.prev = newEl;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("No such index in list");
        }
        Node<T> element = first;
        for (int i = 0; i < index; i++) {
            element = element.next;
        }
        if (element == first) {
            first = element.next;
        }
        if (element == last) {
            last = element.prev;
        }
        if (element.next != null) {
            element.next.prev = element.prev;
        }
        if (element.prev != null) {
            element.prev.next = element.next;
        }
        size--;
        return element.item;
    }

    @Override
    public int indexOf(Object o) {
        Node<T> element = first;
        int idx = 0;
        while (element != null) {
            if (element.item == o) {
                return idx;
            }
            idx++;
            element = element.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<T> element = last;
        int idx = size - 1;
        while (element != null) {
            if (element.item == o) {
                return idx;
            }
            idx--;
            element = element.prev;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("No such indices in list");
        }
        CustomLinkedList<T> subList = new CustomLinkedList<>();
        Node<T> element = first;
        for (int i = 0; i < fromIndex; i++) {
            element = element.next;
        }
        for (int j = fromIndex; j < toIndex; j++) {
            subList.add(element.item);
            element = element.next;
        }
        return subList;
    }

    @Override
    public void addFirst(T t) {
        offerFirst(t);
    }

    @Override
    public void addLast(T t) {
        offerLast(t);
    }

    @Override
    public T getFirst() {
        if (first == null) {
            throw new NoSuchElementException("List is empty");
        }
        return first.item;
    }

    @Override
    public T getLast() {
        if (last == null) {
            throw new NoSuchElementException("List is empty");
        }
        return last.item;
    }

    @Override
    public T removeFirst() {
        return remove();
    }

    @Override
    public T removeLast() {
        if (last == null) {
            throw new NoSuchElementException("List is empty");
        }
        return pollLast();
    }

    public LinkedList<T> reversed() {
        LinkedList<T> reversed = new LinkedList<>();
        Node<T> element = first;
        while (element != null) {
            reversed.offerFirst(element.item);
            element = element.next;
        }
        return reversed;
    }
}