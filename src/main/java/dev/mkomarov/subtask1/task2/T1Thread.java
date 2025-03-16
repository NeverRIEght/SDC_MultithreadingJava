package dev.mkomarov.subtask1.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class T1Thread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(T1Thread.class);

    T1Thread(Runnable task) {
        super(task);
    }

    @Override
    public void run() {
        logger.info("Thread-based thread is running under \"{}\" name", Thread.currentThread().getName());
        super.run();
    }
}
