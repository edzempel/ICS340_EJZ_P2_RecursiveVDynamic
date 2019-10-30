package edu.metrostate.by8477ks.atm;


import java.util.Arrays;

/**
 * Use dynamic programming to count the combinations of given values that total an amount
 *
 * @author ezempel
 * based on code from https://www.youtube.com/watch?v=jaNZ83Q3QGc
 */
class DynamicATM {

    /**
     * Test driver for DynamicATM
     * @param args command line arguments
     */
    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            try {
                System.out.printf("$%d has %d combinations\n", i, dCombinations(i, new int[]{5, 10, 20, 50, 100}));
            } catch (P2Exceptions.ImproperHeaderFileException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Uses Dynamic programming to solve the Coin Change Problem.
     * Uncomment the prints for more detail.
     *
     * @param amount Total amount of money
     * @param bills  available bills/denominations
     * @return combinations of bills that total the amount
     */

    public static int dCombinations(int amount, int[] bills) throws P2Exceptions.ImproperHeaderFileException {
        if (bills != null) {
            Arrays.sort(bills);
            if (Arrays.binarySearch(bills, 0) >= 0) {
                throw new P2Exceptions.ImproperHeaderFileException("Bill value of $0 is not allowed");
            }
        }
        else
            throw new P2Exceptions.ImproperHeaderFileException("List of bills cannot be empty");

        int[] combinations = new int[amount + 1];

        combinations[0] = 1;

        for (int bill : bills) {
            for (int i = 1; i < combinations.length; i++) {
                if (i >= bill) {
                    combinations[i] += combinations[i - bill];
                    //printAmount(combinations);
                }
            }
            //System.out.println();
        }

        return combinations[amount];
    }

    /**
     * Prints out the current state of the combinations array.
     *
     * @param arr Combinations Array to be printed out
     */

    public static void printAmount(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
