import java.util.Arrays;

/**
 * CustomListDemo class contains a demo code for the most basic methods implemented
 */
public class CustomListDemo {
    public static void main(String[] args) {
        //creation
        CustomList<String> aList = new CustomList<String>();

        class PrintArray {
            void printArray() {
                System.out.println(Arrays.toString(aList.toArray()));
            }
        }

        PrintArray util = new PrintArray();

        //adding elements
        aList.add("someStr");
        aList.add("someStr2");
        util.printArray();

        //removing elements
        aList.remove("someStr2");
        util.printArray();

        //size
        System.out.println(aList.size());

        //setting elements
        aList.set(0, "someOtherStr");
        util.printArray();

        //getting elements
        System.out.println(aList.get(0));

        //check for empty
        System.out.println(aList.isEmpty());

        //clear
        aList.clear();
        util.printArray();
    }
}
