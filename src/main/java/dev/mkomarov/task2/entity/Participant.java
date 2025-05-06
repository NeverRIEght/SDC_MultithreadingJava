package dev.mkomarov.task2.entity;

/**
 * Represents the participant of trade process.
 */
public class Participant {
    private final String name;
    private double eurAmount;
    private double usdAmount;
    private double mntAmount;

    public Participant(String name, double eurAmount, double usdAmount, double mntAmount) {
        this.name = name;
        this.eurAmount = eurAmount;
        this.usdAmount = usdAmount;
        this.mntAmount = mntAmount;
    }
}
