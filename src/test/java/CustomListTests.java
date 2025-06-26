import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CustomListTests {
    private CustomList<String> list;

    @BeforeEach
    public void setUp() {
        this.list = new CustomList<>();
    }

    @Test
    void setUpCreatesEmptyList() {
        Assertions.assertEquals(list.size(), 0);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    void addAddsElement() {
        boolean res = list.add("Hello");
        Assertions.assertEquals(list.size(), 1);
        Assertions.assertTrue(res);
    }

    @Test
    void addResizesIfListFull() {
        for (int i = 0; i < 12; i++) {
            list.add("hello");
        }
        Assertions.assertEquals(list.size(), 12);
    }

    @Test
    void addNullThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> list.add(null));
    }

    @Test
    void sizeReturnsElementsNum() {
        list.add("Hello");
        list.add("Hello");
        Assertions.assertEquals(list.size(), 2);
    }

    @Test
    void containsFalseOnEmptyList() {
        Assertions.assertFalse(list.contains("some_o"));
    }

    @Test
    void containsTrueIfElementExists() {
        list.add("hello");
        Assertions.assertTrue(list.contains("hello"));
    }

    @Test
    void containsNullThrowsError() {
        Assertions.assertThrows(NullPointerException.class, () -> list.contains(null));
    }

    @Test
    void toArrayReturnsEmptyIfListEmpty() {
        Object[] emptyArr = new Object[10];
        assertArrayEquals(emptyArr, list.toArray());
    }

    @Test
    void toArrayReturnsElementsOfNonemptyList() {
        Object[] arr = new Object[10];
        arr[0] = "str1";
        arr[1] = "str2";
        list.add("str1");
        list.add("str2");
        assertArrayEquals(arr, list.toArray());
    }

    @Test
    void removeNullThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> list.remove(null));
    }

    @Test
    void removeReturnsFalseIfNothingChanged() {
        Assertions.assertFalse(list.remove("aa"));
    }

    @Test
    void removeReturnsTrueIfChanged() { //TODO: fix method remove
        list.add("aa");
        Assertions.assertTrue(list.remove("aa"));
    }

    @Test
    void removeReturnsTrueIfChanged() { //TODO: fix method remove
        list.add("aa");
        Assertions.assertTrue(list.remove("aa"));
    }
}
