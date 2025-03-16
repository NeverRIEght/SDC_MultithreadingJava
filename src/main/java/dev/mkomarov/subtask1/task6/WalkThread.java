package dev.mkomarov.subtask1.task6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WalkThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(WalkThread.class);
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            logger.info("{} walking... {}", Thread.currentThread().getName(), i);
        }
    }
}
