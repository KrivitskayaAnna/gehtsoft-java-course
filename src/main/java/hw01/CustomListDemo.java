package hw01;

import java.util.Arrays;
import java.util.List;

/**
 * CustomListDemo class contains a demo code for the most basic methods implemented
 */
public class CustomListDemo {
    public static void main(String[] args) {
        //creation
        CustomList<String> aList = new CustomList<String>();

        //adding elements
        aList.add("str1");
        aList.printArray();

        //adding element by index
        aList.add(0, "str2");
        aList.printArray();

        //add all elements
        CustomList<String> addList = new CustomList<String>();
        addList.add("str3");
        addList.add("str4");
        aList.addAll(addList);
        aList.printArray();

        //contain elements
        System.out.println(aList.contains("str3"));
        System.out.println(aList.contains("nonexisting"));

        //contain all elements
        System.out.println(aList.containsAll(addList));

        //removing elements
        aList.add("str3");
        aList.printArray();
        aList.remove("str3");
        aList.printArray();

        //removing element by
        aList.remove(1);
        aList.printArray();

        //remove all elements
        CustomList<String> removeList = new CustomList<String>();
        removeList.add("str4");
        removeList.add("str2");
        aList.removeAll(removeList);
        aList.printArray();

        //retain all elements
        aList.add("str1");
        aList.add("str2");
        aList.add("str3");
        CustomList<String> retainList = new CustomList<String>();
        retainList.add("str2");
        retainList.add("str3");
        aList.retainAll(retainList);
        aList.printArray();

        //indexOf element
        System.out.println(aList.indexOf("str3"));
        System.out.println(aList.indexOf("nonexisting"));

        //lastIndexOf element
        aList.add("str3");
        aList.printArray();
        System.out.println(aList.lastIndexOf("str3"));
        System.out.println(aList.lastIndexOf("nonexisting"));

        //equal lists
        CustomList<String> otherList = new CustomList<String>();
        otherList.add("str2");
        otherList.add("str3");
        System.out.println(aList.equals(otherList));
        otherList.add("str3");
        System.out.println(aList.equals(otherList));

        //size
        System.out.println(aList.size());

        //sublist
        List<String> subList = aList.subList(0, 2);
        System.out.println(Arrays.toString(subList.toArray()));

        //setting elements
        aList.set(0, "str4");
        aList.printArray();

        //getting elements
        System.out.println(aList.get(0));

        //check for empty
        System.out.println(aList.isEmpty());

        //resizing list when full
        for (int i = 0; i < 10; i++) {
            aList.add("strX");
        }
        aList.printArray();

        //clear
        aList.clear();
        System.out.println(aList.size());
        System.out.println(aList.isEmpty());
        aList.printArray();
    }
}
