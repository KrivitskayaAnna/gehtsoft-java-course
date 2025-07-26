package hw03;

public class CustomStack<T> {
    private final CustomLinkedList<T> list;

    public CustomStack() {
        this.list = new CustomLinkedList<>();
    }

    public T pop() {
        return list.pollFirst();
    }

    public T peek() {
        return list.peekFirst();
    }

    public void push(T element) {
        list.offerFirst(element);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
