package dev.mkomarov.task2.entity;

import dev.mkomarov.task2.entity.traderequest.TradeRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the participant of trade process.
 */
public class Participant implements Runnable {
    private Exchange exchange;
    private final String name;
    private final List<Account> accounts;

    public Participant(Exchange exchange, String name, List<Account> accounts) {
        this.exchange = exchange;
        this.name = String.valueOf(name);
        this.accounts = new ArrayList<>(accounts);
    }

    public Participant(String name, List<Account> accounts) {
        this.name = String.valueOf(name);
        this.accounts = new ArrayList<>(accounts);
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public String getName() {
        return String.valueOf(name);
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void run() {
        exchange.registerParticipant(this);

        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (accounts.size() < 2) continue;

                Account acc1 = accounts.get((int)(Math.random() * accounts.size()));
                Account acc2 = accounts.get((int)(Math.random() * accounts.size()));
                while (acc1 == acc2) {
                    acc2 = accounts.get((int)(Math.random() * accounts.size()));
                }

                Currency firstCurrency = acc1.getCurrency();
                Currency secondCurrency = acc2.getCurrency();

                double amount1 = Math.min(10, acc1.getAmount());
                double amount2 = Math.min(10, acc2.getAmount());

                TradeRequest request = new TradeRequest(
                        this,
                        firstCurrency,
                        secondCurrency,
                        amount1,
                        amount2
                );

                Exchange.getInstance().submitRequest(request);

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isWillingToSell(TradeRequest request, Exchange exchange) {
        Currency desiredCurrency = request.getFromCurrency();
        Account accountFrom = getAccount(desiredCurrency);

        if (accountFrom == null || accountFrom.getLock().isLocked()) {
            return false;
        }

        return true;
    }

    public Account getAccount(Currency currency) {
        for (Account a : accounts) {
            if (a.getCurrency().equals(currency)) {
                return a;
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(name, that.name) && Objects.equals(accounts, that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, accounts);
    }
}
