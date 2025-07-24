package hw04;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class CustomHashMapTests {
    private CustomHashMap<Integer, String> hashMap;

    @BeforeEach
    public void setUp() {
        this.hashMap = new CustomHashMap<>();
    }

    @Test
    void setUpCreatesEmptyMap() {
        Assertions.assertEquals(hashMap.size(), 0);
        Assertions.assertTrue(hashMap.isEmpty());
    }

    @Test
    void putAddsElement() {
        String res = hashMap.put(1, "Hello");
        Assertions.assertEquals(hashMap.size(), 1);
        Assertions.assertEquals(hashMap.get(1), "Hello");
        Assertions.assertNull(res);
    }

    @Test
    void putReplacesValue() {
        hashMap.put(1, "Hello");
        String res = hashMap.put(1, "Hello2");
        Assertions.assertEquals(hashMap.size(), 1);
        Assertions.assertEquals(hashMap.get(1), "Hello2");
        Assertions.assertEquals(res, "Hello");
    }

    @Test
    void putManagesCollision() {
        Integer key1 = 0;
        Integer key2 = 16;
        Integer key3 = 32;
        Assertions.assertEquals(key1.hashCode() % 16, key2.hashCode() % 16);
        Assertions.assertEquals(key3.hashCode() % 16, key2.hashCode() % 16);
        hashMap.put(key1, "Hello1");
        hashMap.put(key2, "Hello2");
        hashMap.put(key3, "Hello3");
        Assertions.assertEquals(hashMap.size(), 3);
        Assertions.assertEquals(hashMap.get(key1), "Hello1");
        Assertions.assertEquals(hashMap.get(key2), "Hello2");
        Assertions.assertEquals(hashMap.get(key3), "Hello3");
    }

    @Test
    void putResizesAllData() {
        for (int i = 0; i < 20; i++) {
            hashMap.put(i, "value" + i);
        }
        Assertions.assertEquals(hashMap.size(), 20);
        for (int i = 0; i < 20; i++) {
            Assertions.assertEquals(hashMap.get(i), "value" + i);
        }
    }

    @Test
    void putNullValueProducesNoException() {
        hashMap.put(1, null);
        Assertions.assertNull(hashMap.get(1));
    }

    @Test
    void putNullKeyThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> hashMap.put(null, "Hello"));
    }

    @Test
    void getReturnsNullIfAbsent() {
        String res = hashMap.get(2);
        Assertions.assertNull(res);
    }

    @Test
    void getReturnsValueIfPresent() {
        hashMap.put(2, "Hello");
        String res = hashMap.get(2);
        Assertions.assertEquals(res, "Hello");
    }

    @Test
    void isEmptyTrueIfEmpty() {
        Assertions.assertTrue(hashMap.isEmpty());
    }

    @Test
    void isEmptyFalseIfNonempty() {
        hashMap.put(1, "Hello");
        Assertions.assertFalse(hashMap.isEmpty());
    }

    @Test
    void containsKeyTrueIfPresent() {
        hashMap.put(1, "Hello");
        Assertions.assertTrue(hashMap.containsKey(1));
    }

    @Test
    void containsKeyFalseIfAbsent() {
        hashMap.put(2, "Hello");
        Assertions.assertFalse(hashMap.containsKey(1));
    }

    @Test
    void containsValueTrueIfPresent() {
        hashMap.put(1, "Hello");
        Assertions.assertTrue(hashMap.containsValue("Hello"));
    }

    @Test
    void containsValueFalseIfAbsent() {
        hashMap.put(2, "Hello");
        Assertions.assertFalse(hashMap.containsValue("Hello2"));
    }

    @Test
    void removeReturnsNullIfAbsent() {
        String res = hashMap.remove(2);
        Assertions.assertNull(res);
    }

    @Test
    void removeReturnsDeletedValue() {
        hashMap.put(2, "Hello");
        String res = hashMap.remove(2);
        Assertions.assertEquals(res, "Hello");
        Assertions.assertEquals(hashMap.size(), 0);
    }

    @Test
    void removeHandlesCollision() {
        hashMap.put(0, "Hello1");
        hashMap.put(16, "Hello2");
        String res = hashMap.remove(0);
        Assertions.assertEquals(res, "Hello1");
        Assertions.assertEquals(hashMap.size(), 1);
        Assertions.assertEquals(hashMap.get(16), "Hello2");
    }

    @Test
    void putAllShouldFailOnNull() {
        Assertions.assertThrows(NullPointerException.class, () -> hashMap.putAll(null));
    }

    @Test
    void putAllShouldPutEachElement() {
        Map<Integer, String> putMap = new CustomHashMap<>();
        putMap.put(1, "Hello1");
        putMap.put(2, "Hello2");
        hashMap.putAll(putMap);
        Assertions.assertEquals(hashMap.size(), 2);
        Assertions.assertTrue(hashMap.containsKey(1));
        Assertions.assertTrue(hashMap.containsKey(2));
    }

    @Test
    void clearShouldRemoveAllElements() {
        hashMap.put(1, "Hello");
        hashMap.clear();
        Assertions.assertEquals(hashMap.size(), 0);
        Assertions.assertTrue(hashMap.isEmpty());
        Assertions.assertFalse(hashMap.containsKey(1));
    }

    @Test
    void keySetShouldReturnEmptyIfNoEntries() {
        Assertions.assertTrue(hashMap.keySet().isEmpty());
    }

    @Test
    void keySetShouldReturnKeys() {
        hashMap.put(0, "Hello1");
        hashMap.put(16, "Hello2");
        hashMap.put(10, "Hello3");
        Set<Integer> keysSet = new HashSet<>();
        keysSet.add(0);
        keysSet.add(16);
        keysSet.add(10);
        Assertions.assertEquals(hashMap.keySet().size(), 3);
        Assertions.assertTrue(hashMap.keySet().containsAll(keysSet));
    }

    @Test
    void valuesShouldReturnEmptyIfNoEntries() {
        Assertions.assertTrue(hashMap.values().isEmpty());
    }

    @Test
    void valuesShouldReturnValues() {
        hashMap.put(0, "Hello1");
        hashMap.put(16, "Hello2");
        hashMap.put(10, "Hello3");
        Set<String> keysSet = new HashSet<>();
        keysSet.add("Hello1");
        keysSet.add("Hello2");
        keysSet.add("Hello3");
        Assertions.assertEquals(hashMap.values().size(), 3);
        Assertions.assertTrue(hashMap.values().containsAll(keysSet));
    }

    @Test
    void valuesShouldPreserveDuplicates() {
        hashMap.put(0, "Hello1");
        hashMap.put(16, "Hello1");
        Set<String> keysSet = new HashSet<>();
        keysSet.add("Hello1");
        Assertions.assertEquals(hashMap.values().size(), 2);
        Assertions.assertTrue(hashMap.values().containsAll(keysSet));
    }

    @Test
    void entrySetShouldReturnEmptyIfNoEntries() {
        Assertions.assertTrue(hashMap.entrySet().isEmpty());
    }

    @Test
    void entrySetShouldReturnEntries() {
        hashMap.put(0, "Hello1");
        hashMap.put(16, "Hello2");
        hashMap.put(10, "Hello3");
        Assertions.assertEquals(hashMap.entrySet().size(), 3);
        Assertions.assertTrue(hashMap.entrySet().contains(new AbstractMap.SimpleEntry<>(0, "Hello1")));
        Assertions.assertTrue(hashMap.entrySet().contains(new AbstractMap.SimpleEntry<>(16, "Hello2")));
        Assertions.assertTrue(hashMap.entrySet().contains(new AbstractMap.SimpleEntry<>(10, "Hello3")));
    }
}
