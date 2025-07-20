package hw04;

public class CustomHashMapDemo {
    public static void main(String[] args) {
        CustomHashMap<Integer, String> customMap = new CustomHashMap<>();

        System.out.println(customMap.size());

        for (int i = 0; i <= 20; i++) {
            customMap.put(i, "aValue" + i);
        }

        System.out.println(customMap.get(0));
        System.out.println(customMap.get(10));
        System.out.println(customMap.get(20));
        System.out.println(customMap.get(21));

        System.out.println(customMap.containsKey(0));
        System.out.println(customMap.containsKey(10));
        System.out.println(customMap.containsKey(20));

        System.out.println(customMap.size());

        System.out.println(customMap.remove(19));
        System.out.println(customMap.remove(21));

        System.out.println(customMap.containsValue("aValue0"));
        System.out.println(customMap.containsValue("aValue10"));
        System.out.println(customMap.containsValue("aValue21"));
        System.out.println(customMap.containsValue("aValue20"));

        System.out.println(customMap.keySet());
        System.out.println(customMap.values());

        CustomHashMap<Integer, String> putMap = new CustomHashMap<>();
        System.out.println(customMap.size());
        putMap.put(30, "aValue30");
        putMap.put(33, "aValue33");
        putMap.put(35, "aValue35");
        customMap.putAll(putMap);
        System.out.println(customMap.size());
    }
}
