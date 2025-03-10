package dev.mkomarov.subtask1.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(CustomThread.class);
    private final int numberOfStrings;

    CustomThread(int numberOfStrings) {
        this.numberOfStrings = numberOfStrings;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfStrings; i++) {
            log.info("{} {}", Thread.currentThread().getName(), i);
        }
    }
}
