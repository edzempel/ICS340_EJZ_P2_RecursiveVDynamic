package edu.metrostate.by8477ks.atm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    FileWriter fout;
    File fin;
    Scanner src;

    @BeforeEach
    void setUp() {
        try {
            fout = new FileWriter("test.txt");
            fout.write("1 5 10 20 50 100\n9\n8\n7\n6");
            fout.close();


            fin = new File("Test.txt");
            src = new Scanner(fin);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    void tearDown() {
        src.close();
        fin.delete();
    }

    @Test
    void readHeaderLineTest() {
        try {
            int[] sourceHeader = {1, 5, 10, 20, 50, 100};
            ArrayList<Integer> firstLine = Controller.readHeaderLine(fin);
            assertEquals(sourceHeader.length, firstLine.size());
            for (int i = 0; i < sourceHeader.length; i++) {
                assertEquals(sourceHeader[i], firstLine.get(i));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    void readFileStartingAt() {
        try {
            int[] desired = {9, 8, 7, 6};
            ArrayList<Integer> fileArray = Controller.readFileStartingAt(fin, 1);
            assertEquals(desired.length, fileArray.size());
            for (int i = 0; i < desired.length; i++) {
                assertEquals(desired[i], fileArray.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}