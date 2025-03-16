package dev.mkomarov.subtask1.task7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 1_000_000; i++) {
                Math.sin(i * 24);
            }
            long endTime = System.currentTimeMillis();
            logger.info("Without yield: {}", endTime - startTime);
        }).start();

        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 1_000_000; i++) {
                Math.sin(i * 24);
                Thread.yield();
            }
            long endTime = System.currentTimeMillis();
            logger.info("With yield: {}", endTime - startTime);
        }).start();
    }
}
