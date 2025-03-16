package dev.mkomarov.subtask1.task8;

import java.util.concurrent.TimeUnit;

class P extends Thread {

    private static final int M = 200;
    private boolean state = false;

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                TimeUnit.MILLISECONDS.sleep(M);
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
        return M;
    }
}
