package dev.mkomarov.task3.producer;

import dev.mkomarov.task3.buffer.TaskBuffer;
import dev.mkomarov.task3.model.ThumbnailTask;
import dev.mkomarov.task3.util.RandomProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ImageProducer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ImageProducer.class);

    private final Random random = RandomProvider.get();

    private static final int MIN_DELAY_MS = 1000;
    private static final int MAX_DELAY_MS = 3500;

    private final TaskBuffer buffer;
    private final List<ThumbnailTask> tasks;


    public ImageProducer(TaskBuffer buffer, List<ThumbnailTask> tasks) {
        this.buffer = buffer;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        for (ThumbnailTask task : tasks) {
            buffer.addTask(task);

            try {
                int delay = MIN_DELAY_MS + random.nextInt(MAX_DELAY_MS - MIN_DELAY_MS);
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.warn("Producer is interrupted");
                break;
            }
        }

        LOG.info("Producer is out of tasks, shutting down...");
    }
}
