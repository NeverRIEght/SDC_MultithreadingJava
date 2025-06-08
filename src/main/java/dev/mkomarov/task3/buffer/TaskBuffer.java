package dev.mkomarov.task3.buffer;

import dev.mkomarov.task3.model.ThumbnailTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TaskBuffer {
    private static final Logger LOG = LoggerFactory.getLogger(TaskBuffer.class);

    private final BlockingQueue<ThumbnailTask> queue;

    public TaskBuffer(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    public void addTask(ThumbnailTask task) {
        try {
            queue.put(task);
            LOG.info("Task added to buffer: {}", task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error("Buffer is interrupted while adding task: {}", task.getId(), e);
        }
    }

    public ThumbnailTask takeTask() {
        try {
            ThumbnailTask task = queue.take();
            LOG.info("Task taken from buffer: {}", task);
            return task;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error("Buffer is interrupted while taking task", e);
            return null;
        }
    }
}
