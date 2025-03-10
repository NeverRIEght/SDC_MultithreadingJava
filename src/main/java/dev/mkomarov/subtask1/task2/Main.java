package dev.mkomarov.subtask1.task2;

public class Main {
    public static void main(String[] args) {
        T1Thread thread = new T1Thread(new T2Thread());
        thread.start();

        // Result: Runnable-based task will be executed in the same "Thread-0", because the task will be delegated
        // to super.run(). While we are creating "new Thread(Runnable task)"
        // we are just making the Thread use super.run() on Runnable
    }
}
