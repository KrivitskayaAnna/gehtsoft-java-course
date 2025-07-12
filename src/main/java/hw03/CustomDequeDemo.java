package hw03;

public class CustomDequeDemo {
    public static void main(String[] args) {
        CustomDynamicDeque<String> aQueue = new CustomDynamicDeque<>();
        aQueue.addFirst("second");
        aQueue.addFirst("first");
        aQueue.addLast("third");

        System.out.println(aQueue.size());
        System.out.println(aQueue.peek());
        System.out.println(aQueue.pollFirst());
        System.out.println(aQueue.pollLast());
        System.out.println(aQueue.size());
        System.out.println(aQueue.isEmpty());
    }
}
