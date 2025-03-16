package dev.mkomarov.subtask1.task8;

class Main {
    public static void main(String[] args) {
        P producer = new P();
        producer.start();
        new C(producer).start();
    }
}
