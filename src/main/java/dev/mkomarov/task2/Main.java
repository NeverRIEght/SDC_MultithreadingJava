package dev.mkomarov.task2;

import dev.mkomarov.task2.entity.participant.Currency;
import dev.mkomarov.task2.entity.Exchange;
import dev.mkomarov.task2.entity.participant.Participant;
import dev.mkomarov.task2.importer.CsvImporter;
import dev.mkomarov.task2.printer.BalancePrinter;

import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Exchange exchange = Exchange.getInstance();
        List<Participant> participants = new CsvImporter(',')
                .importData(Paths.get("src/main/resources/participants.csv"));

        exchange.setRate(Currency.EUR, Currency.USD, 1.0);
        exchange.setRate(Currency.EUR, Currency.MNT, 1.0);
        exchange.setRate(Currency.USD, Currency.EUR, 1.0);
        exchange.setRate(Currency.USD, Currency.MNT, 1.0);
        exchange.setRate(Currency.MNT, Currency.EUR, 1.0);
        exchange.setRate(Currency.MNT, Currency.USD, 1.0);

        for (Participant p : participants) {
            p.setExchange(exchange);
        }
        for (Participant p : participants) {
            new Thread(p).start();
        }

        new Thread(new BalancePrinter(participants)).start();
    }
}
