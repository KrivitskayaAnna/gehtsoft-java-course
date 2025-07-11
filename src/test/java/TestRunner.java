import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

//TODO: scan packages
public class TestRunner {
    public static void runTestsByClassName(String className) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> testClass = Class.forName(className);
        Object object = testClass.getDeclaredConstructor().newInstance();
        Method[] methods = testClass.getDeclaredMethods();
        ArrayList<Method> beforeEach = new ArrayList<>();
        ArrayList<Method> afterEach = new ArrayList<>();
        ArrayList<Method> tests = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeEach.class)) {
                beforeEach.add(method);
            }
            if (method.isAnnotationPresent(AfterEach.class)) {
                afterEach.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                tests.add(method);
            }
        }
        int passed = 0;
        int failed = 0;
        int totalTimeMillis = 0;
        System.out.printf("Class scanned: %s\n", className);
        System.out.printf("Tests discovered: %d\n", tests.size());
        System.out.println("Tests results:");
        for (Method method : tests) {
            for (Method beforeM : beforeEach) {
                beforeM.invoke(object);
            }
            long startTime = System.currentTimeMillis();
            try {
                method.invoke(object);
                passed += 1;
                long endTime = System.currentTimeMillis();
                totalTimeMillis += (endTime - startTime);
                System.out.printf("\u2713 Test %s succeeded (%d millis)\n", method.getName(), (endTime - startTime));
            } catch (InvocationTargetException e) {
                failed += 1;
                long endTime = System.currentTimeMillis();
                totalTimeMillis += (endTime - startTime);
                System.out.printf("\u2718 Test %s failed (%d millis), error: %s\n", method.getName(), (endTime - startTime), e.getCause().getMessage());
            }
            for (Method afterM : afterEach) {
                afterM.invoke(object);
            }
        }
        System.out.printf("Total tests: %d\n", passed + failed);
        System.out.printf("Passed tests: %d\n", passed);
        System.out.printf("Failed tests: %d\n", failed);
        System.out.printf("Total time: %d millis\n", totalTimeMillis);
        System.out.printf("Success rate: %.2f %% \n", (float) passed / (passed + failed) * 100);
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        runTestsByClassName("CustomListTests");
        runTestsByClassName("FibonacciAlgorithmsTests");

//        Class scanned: CustomListTests
//        Tests discovered: 43
//        Tests results:
//✓ Test setUpCreatesEmptyList succeeded (2 millis)
//✓ Test addAddsElement succeeded (0 millis)
//✓ Test addResizesIfListFull succeeded (0 millis)
//✓ Test addNullThrowsException succeeded (1 millis)
//✓ Test sizeReturnsElementsNum succeeded (0 millis)
//✓ Test containsFalseOnEmptyList succeeded (1 millis)
//✓ Test containsTrueIfElementExists succeeded (1 millis)
//✓ Test containsNullThrowsError succeeded (1 millis)
//✓ Test toArrayReturnsEmptyIfListEmpty succeeded (1 millis)
//✓ Test toArrayReturnsElementsOfNonemptyList succeeded (0 millis)
//✓ Test removeNullThrowsException succeeded (0 millis)
//✓ Test removeReturnsFalseIfNothingChanged succeeded (0 millis)
//✓ Test removeReturnsTrueIfChanged succeeded (1 millis)
//✓ Test containsEmptyTrue succeeded (0 millis)
//✓ Test containsAllTrueIfExist succeeded (0 millis)
//✓ Test containsAllFalseIfNotExist succeeded (0 millis)
//✓ Test addAllAddsElements succeeded (0 millis)
//✓ Test addAllThrowsExceptionOnNegativeIndex succeeded (0 millis)
//✓ Test addAllThrowsExceptionOnBigIndex succeeded (0 millis)
//✓ Test removeAllReturnsFalseIfUnchanged succeeded (0 millis)
//✓ Test removeAllReturnsTrueIfChanged succeeded (0 millis)
//✓ Test retainAllThrowsExceptionOnNull succeeded (0 millis)
//✓ Test retainAllDropsOtherElements succeeded (1 millis)
//✓ Test clearDropsElements succeeded (0 millis)
//✓ Test getThrowsExceptionOnNegativeIndex succeeded (4 millis)
//✓ Test getThrowsExceptionOnBigIndex succeeded (1 millis)
//✓ Test getReturnsElementUnderIndex succeeded (0 millis)
//✓ Test setReturnsDeletedElement succeeded (0 millis)
//✓ Test addIndexShouldAddElement succeeded (0 millis)
//✓ Test removeIndexShouldRemoveElement succeeded (0 millis)
//✓ Test indexShouldThrowExceptionOnNull succeeded (0 millis)
//✓ Test indexShouldReturnNegativeIfNotExists succeeded (0 millis)
//✓ Test indexShouldReturnIndexIfExists succeeded (0 millis)
//✓ Test listIndexShouldReturnNegativeIfNotExists succeeded (0 millis)
//✓ Test listIndexShouldReturnLastIfExists succeeded (0 millis)
//✓ Test sublistThrowsExceptionIfEndMoreThanStart succeeded (0 millis)
//✓ Test sublistReturnsElementsBetween succeeded (1 millis)
//✓ Test sublistIsEmptyIfIndicesEqual succeeded (0 millis)
//✓ Test emptyListsHaveSameHashcode succeeded (0 millis)
//✓ Test sameListsHaveSameHashcode succeeded (0 millis)
//✓ Test differentListsHaveDifferentHashcode succeeded (1 millis)
//✓ Test listsWithSameElementsAreEqual succeeded (0 millis)
//✓ Test listsWithDifferentElementsNotEqual succeeded (0 millis)
//        Total tests: 43
//        Passed tests: 43
//        Failed tests: 0
//        Total time: 16 millis
//        Success rate: 100.00 %

//        Class scanned: FibonacciAlgorithmsTests
//        Tests discovered: 1
//        Tests results:
//✓ Test algorithmsProduceSameResults succeeded (89 millis)
//        Total tests: 1
//        Passed tests: 1
//        Failed tests: 0
//        Total time: 89 millis
//        Success rate: 100.00 %
    }
}
