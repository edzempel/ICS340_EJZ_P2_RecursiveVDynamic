package edu.metrostate.by8477ks.atm;

/**
 * Each instance is used for one line of the output file for the number of combinations needed to make a total with
 * the given bills/denominations
 *
 * @author ezempel
 */
public class CombinationEntry {
    private int amount;
    private int combinations;
    private long duration;

    /**
     * Constructor
     *
     * @param amount       target amount of money
     * @param combinations number of combinations to make the amount
     * @param duration     time in milliseconds
     */
    public CombinationEntry(int amount, int combinations, long duration) {
        this.amount = amount;
        this.combinations = combinations;
        this.duration = duration;
    }

    /**
     * Constructor, set the duration after instantiation
     *
     * @param amount       target amount of money
     * @param combinations number of combinations to make the amount
     */
    public CombinationEntry(int amount, int combinations) {
        this.amount = amount;
        this.combinations = combinations;
    }

    /**
     * Target amount of money in whole dollars
     *
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Target amount of money in whole dollars
     *
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Number of combinations to make the amount with the given denominations
     *
     * @return
     */
    public int getCombinations() {
        return combinations;
    }

    /**
     * Number of combinations to make the amount with the given denominations
     *
     * @param combinations
     */
    public void setCombinations(int combinations) {
        this.combinations = combinations;
    }

    /**
     * Duration of computation in milliseconds
     *
     * @return
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Duration of computation in milliseconds
     *
     * @param duration
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }
}
