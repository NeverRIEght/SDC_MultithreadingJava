package dev.mkomarov.subtask1.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class T2Thread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(T2Thread.class);

    @Override
    public void run() {
        logger.info("Runnable-based thread is running under \"{}\" name", Thread.currentThread().getName());
    }
}
