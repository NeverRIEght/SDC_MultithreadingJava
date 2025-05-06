package dev.mkomarov.task2.importer;

import dev.mkomarov.task2.entity.Participant;
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
                    .map(parts -> new Participant(
                            parts[0],
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3])
                    ))
                    .toList();
        } catch (IOException e) {
            logger.error("IOException while trying to import data from file with path: {}", filePath);
        }

        return participants;
    }
}
