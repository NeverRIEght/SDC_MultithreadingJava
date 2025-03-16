package dev.mkomarov.subtask1.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int NUMBER_OF_STRINGS = 1000;

    public static void main(String[] args) throws InterruptedException {
        CustomThread t0 = new CustomThread(NUMBER_OF_STRINGS);
        t0.start();

        // Waiting until t0 will be in terminated state
        t0.join();

        for (int i = 0; i < NUMBER_OF_STRINGS; i++) {
            logger.info("{} {}", Thread.currentThread().getName(), i);
        }
    }
}