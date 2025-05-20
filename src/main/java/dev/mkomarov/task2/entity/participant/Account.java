package dev.mkomarov.task2.entity.participant;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private final Currency currency;
    private double amount;
    private final ReentrantLock lock;

    public Account(Currency currency, double amount) {
        this.currency = Currency.valueOf(currency.name());
        this.amount = amount;
        this.lock = new ReentrantLock();
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public ReentrantLock getLock() {
        return lock;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(amount, account.amount) == 0
                && currency == account.currency
                && Objects.equals(lock, account.lock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount, lock);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "currency=" + currency +
                ", amount=" + amount +
                ", lock=" + lock +
                '}';
    }
}
