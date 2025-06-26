public class CustomListDemo {
    public static void main(String[] args) {
        CustomList<String> aList = new CustomList<String>();
        aList.add("some_Str");
        aList.add("some_Str2");
        System.out.println(aList.isEmpty());
    }
}
