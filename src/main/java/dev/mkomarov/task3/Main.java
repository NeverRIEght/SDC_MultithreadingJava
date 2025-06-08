package dev.mkomarov.task3;

import dev.mkomarov.task3.buffer.TaskBuffer;
import dev.mkomarov.task3.consumer.ImageConsumer;
import dev.mkomarov.task3.importer.CsvImporter;
import dev.mkomarov.task3.model.ThumbnailTask;
import dev.mkomarov.task3.producer.ImageProducer;

import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<ThumbnailTask> allTasks = new CsvImporter()
                .importData(Paths.get("src/main/resources/images.csv"));
        TaskBuffer buffer = new TaskBuffer(2);

        int chunkSize = allTasks.size() / 3;

        for (int i = 0; i < 3; i++) {
            int from = i * chunkSize;
            int to;

            if (i == 2) {
                to = allTasks.size(); //all remaining tasks
            } else {
                to = from + chunkSize;
            }

            List<ThumbnailTask> chunk = allTasks.subList(from, to);
            new Thread(new ImageProducer(buffer, chunk)).start();
        }

        for (int i = 0; i < 3; i++) {
            ImageConsumer consumer = new ImageConsumer(buffer, "cons" + i);
            new Thread(consumer).start();
        }
    }
}
