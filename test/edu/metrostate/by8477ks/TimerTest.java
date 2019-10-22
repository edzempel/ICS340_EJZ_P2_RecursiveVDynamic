package edu.metrostate.by8477ks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}