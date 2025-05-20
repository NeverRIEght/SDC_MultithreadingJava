package dev.mkomarov.task2.printer;

import dev.mkomarov.task2.entity.participant.Currency;
import dev.mkomarov.task2.entity.participant.Participant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BalancePrinter implements Runnable {
    private final List<Participant> participants;

    public BalancePrinter(List<Participant> participants) {
        this.participants = new ArrayList<>(participants);
    }

    @Override
    public void run() {
        printBalance();
    }

    private void printBalance() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("--------------------");
                for (Participant participant : participants) {
                    System.out.print(participant.getName());
                    System.out.print(" | ");
                    System.out.print("EUR: ");
                    System.out.print(participant.getAccount(Currency.EUR).getAmount());
                    System.out.print(" | ");
                    System.out.print("USD: ");
                    System.out.print(participant.getAccount(Currency.USD).getAmount());
                    System.out.print(" | ");
                    System.out.print("MNT: ");
                    System.out.print(participant.getAccount(Currency.MNT).getAmount());
                    System.out.println();
                }

                System.out.println("--------------------");

                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
