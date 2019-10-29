package edu.metrostate.by8477ks.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Read the time between start and stop in millisecons.
 * https://howtodoinjava.com/junit5/expected-exception-example/
 * https://docs.oracle.com/javase/tutorial/java/data/numberformat.html
 * https://www.techiedelight.com/measure-elapsed-time-execution-time-java/
 * @author ezempel
 */
class TimerTest {

    Timer timer;

    @BeforeEach
    void setUp() {
        timer = new Timer();
    }

    @Test
    void start() {
        assertEquals(-1, timer.getStartTime());
        timer.start();
        timer.stop();
        assertTrue(timer.getStartTime() > 0);
    }

    @Test
    void stop() {
        assertEquals(-1, timer.getStopTime(), "Timer never stopped");
        timer.stop();
        assertTrue(timer.getStopTime() > 0);
    }

    @Test
    void read() {
        assertThrows(Exception.class, () -> {
            timer.read();
        }, "Timer was started and it shouldn't have been");
        timer.stop();
        assertThrows(Exception.class, () -> {
            timer.read();
        }, "Stopping the timer prevented it from throwing a not started error");
        timer.start();
        assertDoesNotThrow(() -> {
            timer.read();
        }, "Timer did not start as was expected");
    }


    @Test
    void testToString(){
        assertEquals("Timer was not started", timer.toString(), "Timer should not have started");
        timer.start();
        System.out.println(timer); // timer not started
        try {
            TimeUnit.SECONDS.sleep(3);
            timer.stop();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(timer); // timer after 3 milliseconds

    }

    @Test
    void testAccuracy(){
        timer.start();
        try {
            TimeUnit.SECONDS.sleep(3);
            timer.stop();
            assertTrue(timer.read() >= 3000 && timer.read() < 3005, timer.toString() + "is not between 3000 and 3005");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}