package dev.mkomarov.subtask1.task6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WalkThread implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(WalkThread.class);
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            log.info("{} walking... {}", Thread.currentThread().getName(), i);
        }
    }
}
