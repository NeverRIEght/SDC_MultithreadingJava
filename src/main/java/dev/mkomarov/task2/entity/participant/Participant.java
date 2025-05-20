package dev.mkomarov.task2.entity.participant;

import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.traderequest.TradeRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Represents the participant of trade process.
 */
public class Participant implements Runnable {
    private Exchange exchange;
    private final String name;
    private final List<Account> accounts;

    public Participant(String name, List<Account> accounts) {
        this.name = String.valueOf(name);
        this.accounts = new ArrayList<>(accounts);
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

                Random random = new Random();

                Account acc1 = accounts.get(random.nextInt(accounts.size()));
                Account acc2 = accounts.get(random.nextInt(accounts.size()));
                while (acc1.equals(acc2)) {
                    acc2 = accounts.get(random.nextInt(accounts.size()));
                }

                Currency firstCurrency = acc1.getCurrency();
                Currency secondCurrency = acc2.getCurrency();

                double firstAmount = random.nextDouble(10);
                double secondAmount = random.nextDouble(10);

                TradeRequest request = new TradeRequest(
                        this,
                        firstCurrency,
                        secondCurrency,
                        firstAmount,
                        secondAmount
                );

                Exchange.getInstance().submitRequest(request);

                TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isWillingToSell(TradeRequest request) {
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

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "exchange=" + exchange +
                ", name='" + name + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
