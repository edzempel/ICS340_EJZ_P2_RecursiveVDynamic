package edu.metrostate.by8477ks.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecursiveATMTest {
    RecursiveATM recursiveATM;

    @BeforeEach
    void setUp() {
        recursiveATM = new RecursiveATM();
    }

    @Test
    void defaultBills() {
        int[] bills = {1, 5, 10, 20, 50, 100};
        for (int i = 0; i < bills.length; i++) {
            assertEquals(bills[i], recursiveATM.getBills()[i]);
        }
    }

    @Test
    void sortedBills() throws P2Exceptions.ImproperHeaderFileException {
        int[] sortedBills = {1, 2, 5, 10, 50, 100};
        int[] unsortedBills = {5, 100, 1, 2, 10, 50};
        recursiveATM.setBills(unsortedBills);
        for (int i = 0; i < sortedBills.length; i++) {
            assertEquals(sortedBills[i], recursiveATM.getBills()[i]);
        }
    }

    @Test
    void setBills() {
        int[] bills = {0, 1, 2, 3, 4, 5};
        assertThrows(P2Exceptions.ImproperHeaderFileException.class, () -> {
            recursiveATM.setBills(bills);
        });
    }

    @Test
    void rCombinations() {
        assertEquals(recursiveATM.rCombinations(10, 0), 4);
        assertEquals(recursiveATM.rCombinations(20, 0), 10);
        assertEquals(recursiveATM.rCombinations(35, 0), 26);
        assertEquals(recursiveATM.rCombinations(45, 0), 44);
    }
}