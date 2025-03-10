package dev.mkomarov.subtask1.task5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException{
        Thread subThread = new Thread(() -> {
            try {
                log.info("Sub-thread falls to sleep");
                TimeUnit.SECONDS.sleep(3);
                log.info("Sub-thread is awake");
            } catch (InterruptedException e) {
                log.warn("Sub-thread was interrupted while sleeping", e);
            }
        });

        subThread.start();
        TimeUnit.SECONDS.sleep(1);
        log.info("Interrupting sub-thread");
        subThread.interrupt();
    }
}