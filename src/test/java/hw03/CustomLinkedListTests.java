package hw03;

import hw01.CustomList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CustomLinkedListTests {
    private CustomLinkedList<String> list;

    @BeforeEach
    public void setUp() {
        this.list = new CustomLinkedList<>();
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
    void toArrayReturnsEmptyIfListEmpty() {
        Object[] emptyArr = new Object[0];
        assertArrayEquals(emptyArr, list.toArray());
    }

    @Test
    void toArrayReturnsElementsOfNonemptyList() {
        Object[] arr = new Object[2];
        arr[0] = "str1";
        arr[1] = "str2";
        list.add("str1");
        list.add("str2");
        assertArrayEquals(arr, list.toArray());
    }

    @Test
    void removeReturnsFalseIfNothingChanged() {
        Assertions.assertFalse(list.remove("aa"));
    }

    @Test
    void removeReturnsTrueIfChanged() {
        list.add("aa");
        Assertions.assertTrue(list.remove("aa"));
    }

    @Test
    void containsEmptyTrue() {
        LinkedList<String> containsCollection = new LinkedList<>();
        Assertions.assertTrue(list.containsAll(containsCollection));
    }

    @Test
    void containsAllTrueIfExist() {
        LinkedList<String> containsCollection = new LinkedList<>();
        containsCollection.add("str1");
        containsCollection.add("str2");
        list.add("str2");
        list.add("str1");
        Assertions.assertTrue(list.containsAll(containsCollection));
    }

    @Test
    void containsAllFalseIfNotExist() {
        LinkedList<String> containsCollection = new LinkedList<>();
        containsCollection.add("str1");
        containsCollection.add("str2");
        list.add("str2");
        Assertions.assertFalse(list.containsAll(containsCollection));
    }

    @Test
    void addAllAddsElements() {
        LinkedList<String> addCollection = new LinkedList<>();
        addCollection.add("str1");
        addCollection.add("str2");
        list.addAll(addCollection);
        Assertions.assertEquals(2, list.size());
        Assertions.assertTrue(list.containsAll(addCollection));
    }

    @Test
    void addAllThrowsExceptionOnNegativeIndex() {
        LinkedList<String> addCollection = new LinkedList<>();
        addCollection.add("123");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.addAll(-1, addCollection));
    }

    @Test
    void addAllThrowsExceptionOnBigIndex() {
        LinkedList<String> addCollection = new LinkedList<>();
        addCollection.add("123");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.addAll(4, addCollection));
    }

    @Test
    void removeAllReturnsFalseIfUnchanged() {
        LinkedList<String> removeCollection = new LinkedList<>();
        removeCollection.add("str2");
        Assertions.assertFalse(list.removeAll(removeCollection));
    }

    @Test
    void removeAllReturnsTrueIfChanged() {
        LinkedList<String> removeCollection = new LinkedList<>();
        removeCollection.add("str2");
        removeCollection.add("str1");
        list.add("str2");
        list.add("str1");
        Assertions.assertTrue(list.removeAll(removeCollection));
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void retainAllThrowsExceptionOnNull() {
        list.add("str2");
        Assertions.assertThrows(NullPointerException.class, () -> list.retainAll(null));
    }

    @Test
    void retainAllDropsOtherElements() {
        CustomList<String> retainCollection = new CustomList<>();
        retainCollection.add("str2");
        list.add("str1");
        list.add("str2");
        Assertions.assertTrue(list.retainAll(retainCollection));
        Assertions.assertEquals(list.size(), 1);
    }

    @Test
    void clearDropsElements() {
        list.add("str1");
        list.add("str2");
        list.clear();
        Assertions.assertEquals(list.size(), 0);
    }

    @Test
    void getThrowsExceptionOnNegativeIndex() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(-2));
    }

    @Test
    void getThrowsExceptionOnBigIndex() {
        list.add("325");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
    }

    @Test
    void getReturnsElementUnderIndex() {
        list.add("afkj");
        list.add("325");
        Assertions.assertEquals("325", list.get(1));
    }

    @Test
    void setReturnsDeletedElement() {
        list.add("afkj");
        list.add("325");
        Assertions.assertEquals("325", list.set(1, "123"));
        Assertions.assertEquals(list.get(1), "123");
    }

    @Test
    void addIndexShouldAddElement() {
        list.add("afkj");
        list.add("325");
        list.add(1, "234");
        Object[] resultArray = new Object[3];
        resultArray[0] = "afkj";
        resultArray[1] = "234";
        resultArray[2] = "325";
        Assertions.assertEquals(list.size(), 3);
        assertArrayEquals(list.toArray(), resultArray);
    }

    @Test
    void removeIndexShouldRemoveElement() {
        list.add("afkj");
        list.add("325");
        list.add("klhaf");
        list.remove(1);
        Object[] resultArray = new Object[2];
        resultArray[0] = "afkj";
        resultArray[1] = "klhaf";
        Assertions.assertEquals(list.size(), 2);
        assertArrayEquals(list.toArray(), resultArray);
    }

    @Test
    void indexShouldReturnNegativeIfNotExists() {
        Assertions.assertEquals(list.indexOf("srg"), -1);
    }

    @Test
    void indexShouldReturnIndexIfExists() {
        list.add("srg1");
        list.add("srg2");
        Assertions.assertEquals(list.indexOf("srg2"), 1);
    }

    @Test
    void listIndexShouldReturnNegativeIfNotExists() {
        list.add("srg1");
        Assertions.assertEquals(list.lastIndexOf("srg2"), -1);
    }

    @Test
    void listIndexShouldReturnLastIfExists() {
        list.add("srg1");
        list.add("srg2");
        list.add("srg1");
        list.add("srg3");
        Assertions.assertEquals(list.lastIndexOf("srg1"), 2);
    }

    @Test
    void sublistThrowsExceptionIfEndMoreThanStart() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.subList(2, 1));
    }

    @Test
    void sublistReturnsElementsBetween() {
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        Object[] elementsArray = new Object[1];
        elementsArray[0] = "3";
        List<String> aSublist = list.subList(2, 3);
        Assertions.assertEquals(aSublist.size(), 1);
        assertArrayEquals(aSublist.toArray(), elementsArray);
    }

    @Test
    void sublistIsEmptyIfIndicesEqual() {
        list.add("1");
        List<String> aSublist = list.subList(0, 0);
        Assertions.assertEquals(aSublist.size(), 0);
    }

    @Test
    void differentListsHaveDifferentHashcode() {
        CustomList<String> otherList = new CustomList<>();
        otherList.add("123");
        list.add("345");
        Assertions.assertNotEquals(list.hashCode(), otherList.hashCode());
    }

    @Test
    void listsWithDifferentElementsNotEqual() {
        CustomList<String> otherList = new CustomList<>();
        otherList.add("123");
        list.add("345");
        Assertions.assertNotEquals(list, otherList);
    }
}
