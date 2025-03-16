package dev.mkomarov.subtask1.task6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TalkThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(TalkThread.class);
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            logger.info("{} talking... {}", Thread.currentThread().getName(), i);
        }
    }
}
