package hw03;

import java.util.Arrays;

public class CustomDequeDemo {
    public static void main(String[] args) {
        CustomDynamicDeque<String> aQueue = new CustomDynamicDeque<>();

        System.out.println(aQueue.peekFirst());
        System.out.println(aQueue.peekLast());

        aQueue.addFirst("second");
        aQueue.addFirst("first");
        aQueue.addLast("third");

        System.out.println(Arrays.toString(aQueue.toArray(new String[2])));
        System.out.println(Arrays.toString(aQueue.toArray()));

        System.out.println(aQueue.size());
        System.out.println(aQueue.peek());
        System.out.println(aQueue.pollFirst());
        System.out.println(aQueue.size());
        System.out.println(aQueue.isEmpty());
        System.out.println(aQueue.removeLast());
        System.out.println(aQueue.removeFirst());
    }
}
