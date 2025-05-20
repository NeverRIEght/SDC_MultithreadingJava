package dev.mkomarov.task2.entity;

import dev.mkomarov.task2.entity.traderequest.TradeRequest;
import dev.mkomarov.task2.entity.participant.Currency;
import dev.mkomarov.task2.entity.participant.Participant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Exchange {
    private static Exchange instance;
    private static final ReentrantLock singletonLock = new ReentrantLock();

    private final ArrayList<Participant> participants = new ArrayList<>();

    private final ReentrantLock registerLock = new ReentrantLock();
    private final Condition registrationFinished = registerLock.newCondition();

    private final ReentrantLock tradeLock = new ReentrantLock();
    private final Condition tradeFinished = tradeLock.newCondition();

    private final ReentrantLock rateLock = new ReentrantLock();
    private final Map<CurrencyPair, Double> rates = new HashMap<>();

    private TradeRequest currentRequest = null;

    private Exchange() {
    }

    public static Exchange getInstance() {
        if (instance == null) {
            singletonLock.lock();
            try {
                if (instance == null) {
                    instance = new Exchange();
                }
            } finally {
                singletonLock.unlock();
            }
        }
        return instance;
    }

    public void submitRequest(TradeRequest request) {
        tradeLock.lock();
        try {
            while (currentRequest != null) {
                tradeFinished.await();
            }

            currentRequest = request;
            System.out.println("[Exchange] Processing new trade by: " + request.getBuyer().getName());

            currentRequest.proceed(this);

            // Imitate the long processing of the request:
            TimeUnit.SECONDS.sleep(3);
            System.out.println("[Exchange] Trade completed, moving to the next one\n");

            currentRequest = null;
            tradeFinished.signalAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            tradeLock.unlock();
        }
    }

    public void registerParticipant(Participant participant) {
        registerLock.lock();
        try {
            while (currentRequest != null) {
                registrationFinished.await();
            }

            participants.add(participant);
            System.out.printf("Participant added: %s%n", participant.getName());
            registrationFinished.signalAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            registerLock.unlock();
        }
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setRate(Currency from, Currency to, double rate) {
        rateLock.lock();
        try {
            rates.put(new CurrencyPair(from, to), rate);
        } finally {
            rateLock.unlock();
        }
    }

    public double getRate(Currency from, Currency to) {
        rateLock.lock();
        try {
            CurrencyPair pair = new CurrencyPair(from, to);
            Double rate = rates.get(pair);

            if (rate == null) {
                throw new IllegalArgumentException("No rate found for: " + from + " -> " + to);
            }

            return rate;
        } finally {
            rateLock.unlock();
        }
    }
}
