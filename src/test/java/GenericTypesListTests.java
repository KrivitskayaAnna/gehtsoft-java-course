import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * GenericTypesListTests are tests for different types of elements inside a generic list
 */
class GenericTypesListTests {
    @Test
    void addAddsIntElement() {
        CustomList<Integer> intList = new CustomList<>();
        boolean res = intList.add(123);
        Assertions.assertEquals(intList.size(), 1);
        Assertions.assertTrue(res);
    }

    @Test
    void removeRemovesLongElement() {
        CustomList<Long> longList = new CustomList<>();
        longList.add(123L);
        boolean res = longList.remove(123L);
        Assertions.assertEquals(longList.size(), 0);
        Assertions.assertTrue(res);
    }

    @Test
    void addAllAddsBooleanElement() {
        CustomList<Boolean> booleanList = new CustomList<>();
        CustomList<Boolean> insertCollection = new CustomList<>();
        insertCollection.add(true);
        insertCollection.add(false);
        boolean res = booleanList.addAll(insertCollection);
        Assertions.assertEquals(booleanList.size(), 2);
        Assertions.assertTrue(res);
    }
}
