package dev.mkomarov.task2.importer;

import dev.mkomarov.task2.entity.Participant;

import java.nio.file.Path;
import java.util.List;

public interface Importer {
    List<Participant> importData(Path filePath);
}
