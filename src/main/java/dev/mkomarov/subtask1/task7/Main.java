package dev.mkomarov.subtask1.task7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Thread fastTask = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                log.info("{} fast step {}", Thread.currentThread().getName(), i);
            }
        });
        Thread slowTask = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                log.info("{} slow step {}", Thread.currentThread().getName(), i);
                Thread.yield();
            }
        });

        fastTask.start();
        slowTask.start();

        // Slow task will finish later than fast task
    }
}
