package hw05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;

public class BankClientSimulation {
    public static void transWithoutSync(Bank bank) {
        int from = bank.pickRandomAccountId();
        int to = bank.pickRandomAccountId();
        if (bank.getAccountBalance(from) > 0) {
            long x = new Random().nextLong(0, bank.getAccountBalance(from));
            long balFrom = bank.getAccountBalance(from) - x;
            bank.setAccountBalance(from, balFrom);
            long balTo = bank.getAccountBalance(to) + x;
            bank.setAccountBalance(to, balTo);
        }
    }

    public static void transWithSync(Bank bank) {
        int from = bank.pickRandomAccountId();
        int to = bank.pickRandomAccountId();
        bank.transferRandomSync(from, to);
    }

    public static void transWithReentrant(Bank bank) {
        int from = bank.pickRandomAccountId();
        int to = bank.pickRandomAccountId();
        bank.transferRandomReentrant(from, to);
    }

    public static boolean isSumEqualAfterTransactions(Consumer<Bank> function) throws InterruptedException {
        Bank bank = new Bank(200, 0L, 1_000L);
        BigInteger initSumBalance = bank.getSumOfAllAccounts();
        System.out.println("Initial total: " + initSumBalance);

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            Thread thread = Thread.ofVirtual().start(() -> {
                function.accept(bank);
            });
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }

        BigInteger finalSumBalance = bank.getSumOfAllAccounts();
        System.out.println("Final total: " + finalSumBalance);
        return Objects.equals(initSumBalance, finalSumBalance);
    }


    public static void main(String[] args) throws InterruptedException {
        boolean result1 = isSumEqualAfterTransactions(BankClientSimulation::transWithoutSync);
        System.out.println("Total sum equal after non-sync operations: " + result1);

        boolean result2 = isSumEqualAfterTransactions(BankClientSimulation::transWithSync);
        System.out.println("Total sum equal after sync operations: " + result2);

        boolean result3 = isSumEqualAfterTransactions(BankClientSimulation::transWithReentrant);
        System.out.println("Total sum equal after re-entrant operations: " + result3);
        //first method (non-thread safe) gives false implying it's incorrectly handles concurrency
        //while calls to second and third methods always return correct account balances

//        results:
//        Initial total: 108418
//        Final total: 109679
//        Total sum equal after non-sync operations: false
//        Initial total: 95872
//        Final total: 95872
//        Total sum equal after sync operations: true
//        Initial total: 94117
//        Final total: 94117
//        Total sum equal after re-entrant operations: true
    }
}
