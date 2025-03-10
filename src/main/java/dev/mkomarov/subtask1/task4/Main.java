package dev.mkomarov.subtask1.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    static int numberOfThreads = 4;

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            PrinterThread thread = new PrinterThread(generateMessages(i));
            threads.add(thread);
            thread.start();
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                log.error("Main Thread in interrupted!");
            }
        });
    }

    private static List<String> generateMessages(int threadNumber) {
        List<String> output = new ArrayList<>();
        if (threadNumber < 0 || threadNumber >= numberOfThreads) {
            log.error("Invalid threadNumber");
            return output;
        }

        int numberOfMessages = 10;

        for (int i = 1; i <= numberOfMessages; i++) {
            output.add("Message" + i * (threadNumber + 1));
        }

        return output;
    }
}