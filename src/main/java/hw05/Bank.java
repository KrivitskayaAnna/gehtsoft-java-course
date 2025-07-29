package hw05;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private final int numberOfAccounts;
    private final HashMap<Integer, Long> accountBalances;
    private Lock[] accountLocks;

    public Bank(int numberOfAccounts, long minBalance, long maxBalance) {
        this.numberOfAccounts = numberOfAccounts;
        HashMap<Integer, Long> balances = new HashMap<>();
        accountLocks = new ReentrantLock[numberOfAccounts];
        for (int i = 0; i < numberOfAccounts; i++) {
            balances.put(i, new Random().nextLong(minBalance, maxBalance));
            accountLocks[i] = new ReentrantLock();
        }
        this.accountBalances = balances;
    }

    public int pickRandomAccountId() {
        return new Random().nextInt(0, numberOfAccounts);
    }

    public long getAccountBalance(int accountId) {
        return accountBalances.get(accountId);
    }

    public void setAccountBalance(int accountId, long newBalance) {
        accountBalances.put(accountId, newBalance);
    }

    public BigInteger getSumOfAllAccounts() {
        return accountBalances.values().stream().map(BigInteger::valueOf).reduce(BigInteger.ZERO, BigInteger::add);
    }

    public void transferRandomSync(int accountIdFrom, int accountIdTo) {
        synchronized (accountBalances) {
            if (getAccountBalance(accountIdFrom) > 0) {
                long transferSum = new Random().nextLong(0, getAccountBalance(accountIdFrom));
                long balFrom = getAccountBalance(accountIdFrom) - transferSum;
                setAccountBalance(accountIdFrom, balFrom);
                long balTo = getAccountBalance(accountIdTo) + transferSum;
                setAccountBalance(accountIdTo, balTo);
            }
        }
    }

    public void transferRandomReentrant(int accountIdFrom, int accountIdTo) {
        int firstAccountToLock = Math.min(accountIdFrom, accountIdTo);
        int secondAccountToLock = Math.max(accountIdFrom, accountIdTo);
        accountLocks[firstAccountToLock].lock();
        accountLocks[secondAccountToLock].lock();
        long balFrom = getAccountBalance(accountIdFrom);
        if (balFrom > 0) {
            long transferSum = new Random().nextLong(0, balFrom);
            setAccountBalance(accountIdFrom, balFrom - transferSum);
            setAccountBalance(accountIdTo, getAccountBalance(accountIdTo) + transferSum);
        }
        accountLocks[secondAccountToLock].unlock();
        accountLocks[firstAccountToLock].unlock();
    }
}
