package dev.mkomarov.task2;

import dev.mkomarov.task2.entity.Participant;
import dev.mkomarov.task2.importer.CsvImporter;

import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Participant> participants = new CsvImporter(',')
                .importData(Paths.get("src/main/resources/participants.csv"));
        System.out.println(participants);
    }
}
