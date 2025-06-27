import java.util.*;

public class CustomList<T> implements List<T> {
    private final int initSize = 10;
    private Object[] elements;
    private int size;

    private void resizeIfFull() {
        if (size == elements.length) {
            int newSize = elements.length * 2;
            elements = Arrays.copyOf(elements, newSize);
        }
    }

    public CustomList() {
        elements = new Object[initSize];
        size = 0;
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
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (Objects.equals(o, elements[i])) {
                    return true;
                }
            }
            return false;
        } else throw new NullPointerException("List cannot contain null");
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return this.elements;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        if (t != null) {
            resizeIfFull();
            elements[size++] = t;
            return true;
        } else {
            throw new NullPointerException("Cannot add null to a list");
        }
    }

    @Override
    public boolean remove(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (Objects.equals(o, elements[i])) {
                    Object[] removeCopyArray = new Object[elements.length - 1];
                    System.arraycopy(elements, 0, removeCopyArray, 0, i);
                    System.arraycopy(elements, i + 1, removeCopyArray, i, elements.length - i - 1);
                    elements = removeCopyArray;
                    size -= 1;
                    return true;
                }
            }
            return false;
        } else {
            throw new NullPointerException("Cannot remove null from a list");
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean overallResult = true;
        for (int i = 0; i < c.size(); i++) {
            boolean result = contains(c.toArray()[i]);
            if (!result) {
                overallResult = false;
                break;
            }
        }
        return overallResult;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] arrToAdd = c.toArray();
        for (int i = 0; i < c.size(); i++) {
            add((T) arrToAdd[i]);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Cannot add - index is incorrect");
        } else {
            Object[] arrToAdd = c.toArray();
            int insertPosition = index;
            for (int i = 0; i < c.size(); i++) {
                add(insertPosition, (T) arrToAdd[i]);
                insertPosition++;
            }
            return true;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] arrToRemove = c.toArray();
        boolean overallResult = false;
        for (int i = 0; i < c.size(); i++) {
            boolean result = remove(arrToRemove[i]);
            if (result) overallResult = true;
        }
        return overallResult;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c != null) {
            boolean overallResult = false;
            for (int i = 0; i < size; i++) {
                if (!c.contains(elements[i])) {
                    boolean result = remove(elements[i]);
                    if (result) overallResult = true;
                } else throw new NullPointerException("Cannot retain null element");
            }
            return overallResult;
        } else throw new NullPointerException("Cannot retail null collection");
    }

    @Override
    public void clear() {
        elements = new Object[initSize];
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < size && index >= 0) {
            return (T) elements[index];
        } else throw new IndexOutOfBoundsException("No element with index " + index);
    }

    @Override
    public T set(int index, T element) {
        if (index > 0 && index < size) {
            T previousEl = (T) elements[index];
            elements[index] = element;
            return previousEl;
        } else throw new IndexOutOfBoundsException("Cannot set index " + index);
    }

    @Override
    public void add(int index, T element) {
        if (index <= size && index > 0) {
            resizeIfFull();
            Object previous;
            Object next = element;
            for (int i = index; i < size; i++) {
                previous = elements[index];
                elements[i] = next;
                next = previous;
            }
            elements[size] = next;
            size++;
        } else throw new ArrayIndexOutOfBoundsException("Cannot add element with index " + index);
    }

    @Override
    public T remove(int index) {
        if (index < size && index > 0) {
            T removed = (T) elements[index];
            Object[] removeCopyArray = new Object[elements.length - 1];
            System.arraycopy(elements, 0, removeCopyArray, 0, index);
            System.arraycopy(elements, index + 1, removeCopyArray, index, elements.length - index - 1);
            elements = removeCopyArray;
            size--;
            return removed;
        } else throw new ArrayIndexOutOfBoundsException("Cannot remove element with index " + index);
    }

    @Override
    public int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (Objects.equals(o, elements[i])) {
                    return i;
                }
            }
            return -1;
        } else throw new NullPointerException("Cannot get index of null");
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o != null) {
            for (int i = size - 1; i >= 0; i--) {
                if (Objects.equals(o, elements[i])) {
                    return i;
                }
            }
        } else throw new NullPointerException("Cannot get index of null element");
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
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Improper indices passed");
        } else {
            Object[] subArray = new Object[toIndex - fromIndex];
            System.arraycopy(elements, fromIndex, subArray, 0, toIndex - fromIndex);
            CustomList<T> aSubList = new CustomList<>();
            aSubList.elements = subArray;
            aSubList.size = toIndex - fromIndex;
            return aSubList;
        }
    }

    public boolean equals(List<T> listEls) {
        return hashCode() == listEls.hashCode();
    }

    public int hashCode() {
        int hashCode = 1;
        for (int i = 0; i < size; i++) {
            hashCode = 31 * hashCode + (elements[i] == null ? 0 : get(i).hashCode());
        }
        return hashCode;
    }
}
