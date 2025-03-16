package dev.mkomarov.subtask1.task5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        executor.submit(() -> {
            try {
                logger.info("Sub-thread is waiting for an element");
                String element = queue.take();
                logger.info("Sub-thread received: {}", element);
            } catch (InterruptedException e) {
                logger.warn("Sub-thread was interrupted while waiting", e);
            }
        });

        try {
            TimeUnit.SECONDS.sleep(3);
            logger.info("Shutting down executor");
            executor.shutdownNow();
        } catch (InterruptedException e) {
            logger.error("Interrupted Exception");
            Thread.currentThread().interrupt();
        }
    }
}
