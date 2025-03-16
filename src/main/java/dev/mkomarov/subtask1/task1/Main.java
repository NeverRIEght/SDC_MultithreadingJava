package dev.mkomarov.subtask1.task1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int NUMBER_OF_STRINGS = 1000;

    public static void main(String[] args) {
        new CustomThread(NUMBER_OF_STRINGS).start();

        for (int i = 0; i < NUMBER_OF_STRINGS; i++) {
            logger.info("{} {}", Thread.currentThread().getName(), i);
        }
    }
}