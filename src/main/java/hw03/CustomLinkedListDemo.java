package hw03;

import java.util.Arrays;
import java.util.LinkedList;

public class CustomLinkedListDemo {
    public static void main(String[] args) {
        CustomLinkedList<Integer> intList = new CustomLinkedList<>();
        intList.offerFirst(1);
        intList.offerLast(2);
        intList.offerLast(5);
        intList.offerLast(6);

        LinkedList<Integer> addList = new LinkedList<>();
        addList.add(3);
        addList.add(4);

        System.out.println(Arrays.toString(intList.toArray()));
        intList.addAll(2, addList);
        intList.remove(4);
        System.out.println(Arrays.toString(intList.toArray()));
        System.out.println(Arrays.toString(intList.subList(1, 3).toArray()));
        System.out.println(Arrays.toString(intList.reversed().toArray()));
        System.out.println(Arrays.toString(intList.toArray(new Integer[5])));
        System.out.println(intList.contains(3));
        System.out.println(intList.indexOf(2485));
        System.out.println(intList.lastIndexOf(3));
        System.out.println(intList.remove((Object) 1));
        System.out.println(intList.removeLastOccurrence((Object) 3));
        System.out.println(Arrays.toString(intList.toArray()));
        System.out.println(intList.peekFirst());
        System.out.println(intList.pollLast());
        System.out.println(intList.size());
        System.out.println(Arrays.toString(intList.toArray()));
        System.out.println(intList.get(0));
    }
}
