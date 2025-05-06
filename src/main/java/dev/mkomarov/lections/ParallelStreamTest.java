package dev.mkomarov.lections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class ParallelStreamTest {
    private static final Logger logger = LoggerFactory.getLogger(ParallelStreamTest.class);


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 1_000_000; i++) {
            list.add(i);
        }

        long startTime = System.currentTimeMillis();
        list.parallelStream().forEach(element -> Math.sin(element * 24));
        long endTime = System.currentTimeMillis();
        logger.info("Parallel: {}", endTime - startTime);

        startTime = System.currentTimeMillis();
        list.forEach(element -> Math.sin(element * 24));
        endTime = System.currentTimeMillis();
        logger.info("Not parallel: {}", endTime - startTime);
    }
}
