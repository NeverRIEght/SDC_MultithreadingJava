package dev.mkomarov.task2.importer;

import dev.mkomarov.task2.entity.participant.Account;
import dev.mkomarov.task2.entity.participant.Currency;
import dev.mkomarov.task2.entity.participant.Participant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CsvImporter implements Importer {
    private static final Logger logger = LoggerFactory.getLogger(CsvImporter.class);
    private final char delimiter;

    public CsvImporter(char delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public List<Participant> importData(Path filePath) {
        List<Participant> participants = new ArrayList<>();
        try (Stream<String> linesStream = Files.lines(filePath)) {
            participants = linesStream
                    .skip(1)
                    .map(line -> line.split(String.valueOf(delimiter)))
                    .map(parts -> {
                        List<Account> accounts = List.of(
                            new Account(Currency.USD, Integer.parseInt(parts[1])),
                            new Account(Currency.EUR, Integer.parseInt(parts[2])),
                            new Account(Currency.MNT, Integer.parseInt(parts[3]))
                        );
                        return new Participant(parts[0], accounts);
                    })
                    .toList();
        } catch (IOException e) {
            logger.error("IOException while trying to import data from file with path: {}", filePath);
        }

        return participants;
    }
}
