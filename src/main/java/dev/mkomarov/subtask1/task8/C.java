package dev.mkomarov.subtask1.task8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

class C extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(C.class);

    private final P producer;
    private static final int K = 2000;
    private int elapsedTime = 0;

    C(P producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        while (elapsedTime < K) {
            synchronized (producer) {
                while (!producer.getBooleanState()) {
                    try {
                        producer.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }

            logger.info("Remaining time: {}", K - elapsedTime);
            try {
                TimeUnit.MILLISECONDS.sleep(producer.getM() / 10);
                elapsedTime += producer.getM() / 10;
            } catch (InterruptedException e) {
                return;
            }
        }

        producer.interrupt();
    }
}
