package dev.mkomarov.subtask1.task8;

import java.util.concurrent.TimeUnit;

public class P extends Thread {

    private static final int m = 200;
    private boolean state = false;

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                TimeUnit.MILLISECONDS.sleep(m);
                toggleBooleanState();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized void toggleBooleanState() {
        state = !state;
        notifyAll();
    }

    synchronized boolean getBooleanState() {
        return state;
    }

    int getM() {
        return m;
    }
}
