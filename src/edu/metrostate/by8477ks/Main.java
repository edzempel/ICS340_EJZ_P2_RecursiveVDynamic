package edu.metrostate.by8477ks;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {


        Timer timer = new Timer();
        System.out.println(timer);
        timer.start();
        try {
            TimeUnit.SECONDS.sleep(3);
            timer.stop();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(timer);

        // Start user interface
//        @SuppressWarnings("unused")
//        View userInterface = new View();
    }
}
