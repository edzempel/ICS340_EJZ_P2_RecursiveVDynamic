package edu.metrostate.by8477ks.atm;

public class CombinationEntry {
    private int amount;
    private int combinations;
    private long duration;

    public CombinationEntry(int amount, int combinations, long duration) {
        this.amount = amount;
        this.combinations = combinations;
        this.duration = duration;
    }

    public CombinationEntry(int amount, int combinations) {
        this.amount = amount;
        this.combinations = combinations;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCombinations() {
        return combinations;
    }

    public void setCombinations(int combinations) {
        this.combinations = combinations;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
