package hw03;

public class CustomQueue<T> {
    private final CustomLinkedList<T> list;

    public CustomQueue() {
        this.list = new CustomLinkedList<>();
    }

    public void enqueue(T element) {
        list.offerLast(element);
    }

    public T dequeue() {
        return list.pollFirst();
    }

    public T peek() {
        return list.peekFirst();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
