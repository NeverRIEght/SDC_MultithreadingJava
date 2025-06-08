package dev.mkomarov.task3.consumer;

import dev.mkomarov.task3.buffer.TaskBuffer;
import dev.mkomarov.task3.model.ThumbnailTask;
import dev.mkomarov.task3.util.RandomProvider;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ImageConsumer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ImageConsumer.class);

    private final Random random = RandomProvider.get();

    private static final int MIN_PROCESSING_MS = 1000;
    private static final int MAX_PROCESSING_MS = 3500;

    private final TaskBuffer buffer;
    private final String name;

    public ImageConsumer(TaskBuffer buffer, String name) {
        this.buffer = buffer;
        this.name = name;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ThumbnailTask task = buffer.takeTask();
            if (task == null) break;

            LOG.info("Consumer({}): got task: {}", name, task.getId());

            try {
                File outputFile = new File(task.getOutputPath());
                File outputDir = outputFile.getParentFile();
                if (!outputDir.exists() && !outputDir.mkdirs()) {
                    LOG.error("Consumer({}): failed to create dir: {}", name, outputDir.getAbsolutePath());
                    throw new IOException("Failed to create directory: " + outputDir.getAbsolutePath());
                }

                Thumbnails.of(new File(task.getInputPath()))
                        .size(task.getTargetWidth(), task.getTargetHeight())
                        .toFile(outputFile);

                LOG.info("Consumer({}): thumbnail created: {}", name, task.getOutputPath());

                int delay = MIN_PROCESSING_MS + random.nextInt(MAX_PROCESSING_MS - MIN_PROCESSING_MS);
                TimeUnit.MILLISECONDS.sleep(delay);

            } catch (IOException e) {
                LOG.error("Consumer({}): error while processing task: {}", name, task, e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.warn("Consumer({}): is interrupted", name);
                break;
            }
        }
    }
}
