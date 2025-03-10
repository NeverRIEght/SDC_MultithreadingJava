package dev.mkomarov.subtask1.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T1Thread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(T1Thread.class);

    public T1Thread(Runnable task) {
        super(task);
    }

    @Override
    public void run() {
        log.info("Thread-based thread is running under \"{}\" name", Thread.currentThread().getName());
        super.run();
    }
}
