package dev.mkomarov.subtask1.task8;

public class Main {
    public static void main(String[] args) {
        int m = 200;
        P producer = new P();
        C consumer = new C(producer);

        producer.start();
        consumer.start();
    }
}
