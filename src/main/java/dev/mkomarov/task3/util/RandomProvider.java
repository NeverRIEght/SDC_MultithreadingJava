package dev.mkomarov.task3.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomProvider {

    private RandomProvider() {
    }

    public static ThreadLocalRandom get() {
        return ThreadLocalRandom.current();
    }
}
