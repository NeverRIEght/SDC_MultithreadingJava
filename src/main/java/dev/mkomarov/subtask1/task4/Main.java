package dev.mkomarov.subtask1.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int NUMBER_OF_THREADS = 4;
    private static final int NUMBER_OF_MESSAGES_PER_THREAD = 10;

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            PrinterThread thread = new PrinterThread(generateMessages(i));
            threads.add(thread);
            thread.start();
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                logger.error("Main Thread in interrupted!");
            }
        });
    }

    private static List<String> generateMessages(int threadNumber) {
        List<String> output = new ArrayList<>();
        if (threadNumber < 0 || threadNumber >= NUMBER_OF_THREADS) {
            logger.error("Invalid threadNumber");
            return output;
        }

        for (int i = 1; i <= NUMBER_OF_MESSAGES_PER_THREAD; i++) {
            output.add("Message" + i * (threadNumber + 1));
        }

        return output;
    }
}