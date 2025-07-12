package hw03;

public class CustomStackDemo {
    public static void main(String[] args) {
        CustomStack<String> aStack = new CustomStack<>();
        aStack.push("first");
        aStack.push("second");
        System.out.println(aStack.size());
        System.out.println(aStack.peek());
        System.out.println(aStack.pop());
        System.out.println(aStack.pop());
        System.out.println(aStack.size());
        System.out.println(aStack.isEmpty());
    }
}
