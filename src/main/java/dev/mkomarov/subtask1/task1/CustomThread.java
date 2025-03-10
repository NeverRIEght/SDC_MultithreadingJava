package dev.mkomarov.subtask1.task1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CustomThread extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(CustomThread.class);
    private final int numberOfStrings;

    CustomThread(int numberOfStrings) {
        this.numberOfStrings = numberOfStrings;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfStrings; i++) {
            LOG.info("{} {}", Thread.currentThread().getName(), i);
        }
    }
}
