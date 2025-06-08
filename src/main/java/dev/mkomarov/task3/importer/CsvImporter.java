package dev.mkomarov.task3.importer;

import dev.mkomarov.task3.model.ThumbnailTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CsvImporter {
    private static final Logger logger = LoggerFactory.getLogger(CsvImporter.class);

    private static final String DATA_DIR_PATH = "data" + File.separator;
    private static final String THUMBNAILS_DIR_PATH = DATA_DIR_PATH + "thumbnails" + File.separator;
    private static final int THUMBNAIL_SIZE_PX = 190;

    public CsvImporter() {

    }

    public List<ThumbnailTask> importData(Path filePath) {
        List<ThumbnailTask> tasks = new ArrayList<>();
        try (Stream<String> linesStream = Files.lines(filePath)) {
            tasks = linesStream
                    .skip(1)
                    .map(imageName -> new ThumbnailTask(
                            Paths.get(DATA_DIR_PATH, imageName).toAbsolutePath().toString(),
                            Paths.get(THUMBNAILS_DIR_PATH, imageName).toAbsolutePath().toString(),
                            THUMBNAIL_SIZE_PX,
                            THUMBNAIL_SIZE_PX
                    ))
                    .toList();
        } catch (IOException e) {
            logger.error("IOException while trying to import data from file with path: {}", filePath);
        }

        return tasks;
    }
}
