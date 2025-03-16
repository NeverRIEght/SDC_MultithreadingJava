package dev.mkomarov.subtask1.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class PrinterThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(PrinterThread.class);
    private final List<String> messages;

    PrinterThread(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public void run() {
        for (String message : messages) {
            logger.info("{} says: {}", Thread.currentThread().getName(), message);
        }
    }
}
