package hw05;

import hw01.CustomList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MultithreadedCustomListTests {
    private CustomList<String> list;
    private SynchronizedCustomList<String> syncList;
    private ReadWriteLockCustomList<String> readWriteList;

    @BeforeEach
    public void setUp() {
        this.list = new CustomList<>();
        this.syncList = new SynchronizedCustomList<>();
        this.readWriteList = new ReadWriteLockCustomList<>();
    }

    public Thread addMillionOneThread(CustomList<String> alist) {
        return Thread.ofVirtual().start(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                alist.add(String.valueOf(i));
            }
        });
    }

    public int getAddMillionSize(CustomList<String> alist) throws InterruptedException {
        Thread thread1 = addMillionOneThread(alist);
        Thread thread2 = addMillionOneThread(alist);
        thread1.join();
        thread2.join();
        return alist.size();
    }

    @Test
    void checkListSizeAfterAddMillion() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            setUp();
            int listSize = getAddMillionSize(list);
            Assertions.assertEquals(2_000_000, listSize);
        }
    }
    //sometimes fails with ArrayIndexOutOfBoundsException due to concurrency breaking the list resizing
    //sometimes gives size less than 2 billions

    @Test
    void checkSyncSizeAfterAddMillion() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            setUp();
            int listSize = getAddMillionSize(syncList);
            Assertions.assertEquals(2_000_000, listSize);
        }
    }
    //takes 15 seconds (which is pretty much)
    //always gives correct sizes

    @Test
    void checkReadWriteSizeAfterAddMillion() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            setUp();
            int listSize = getAddMillionSize(readWriteList);
            Assertions.assertEquals(2_000_000, listSize);
        }
    }
    //takes 26 seconds (which is even more the synchronized)
    //always gives correct sizes
}
