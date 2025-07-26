package hw03;

public class CustomQueueDemo {
    public static void main(String[] args) {
        CustomQueue<String> aQueue = new CustomQueue<>();
        aQueue.enqueue("first");
        aQueue.enqueue("second");
        System.out.println(aQueue.size());
        System.out.println(aQueue.peek());
        System.out.println(aQueue.dequeue());
        System.out.println(aQueue.dequeue());
        System.out.println(aQueue.size());
        System.out.println(aQueue.isEmpty());
    }
}