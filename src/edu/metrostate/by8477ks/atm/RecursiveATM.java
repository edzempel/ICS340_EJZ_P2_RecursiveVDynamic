package edu.metrostate.by8477ks.atm;

/**
 * Calculate the number of combinations of bills that add up to a given amount
 * RecursiveATM objects default to U.S. currency {$1, $5, $10, $20, $50, $100},
 * but can be altered to any country's currency
 * @author ezempel
 * Based on code found at https://www.youtube.com/watch?v=k4y5Pr0YVhg
 */
public class RecursiveATM {

    private int[] bills = {1, 5, 10, 20, 50, 100};

    public void setBills(int[] bills) {
        this.bills = bills;
    }

    public static void main(String[] args) {
        for(int i = 0; i < 200; i++){
        System.out.printf("$%d has %d combinations\n", i, new RecursiveATM().rCombinations(i, 0));}

    }

    /**
     *
     * @param amount Total amount of money
     * @param currentBill The starting bill in the
     * @return
     */
    public int rCombinations(int amount, int currentBill) {
        if (amount == 0) // found valid combination
            return 1;
        if (amount < 0) // invalid combination
            return 0;

        int nCombos = 0; // number of combinations
        // if above zero keep subtracting bills
        for (int bill = currentBill; bill < bills.length; bill++) { // for each of the possible bills starting at the current bill
            nCombos += rCombinations(amount - bills[bill], bill); // recursive call combinations on the amount remaining after subtracting the current bill value and add that to the running total of combinations
        }
        return nCombos; // finally return the total combinations
    }


}
