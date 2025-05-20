package dev.mkomarov.task2.entity;

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
}
