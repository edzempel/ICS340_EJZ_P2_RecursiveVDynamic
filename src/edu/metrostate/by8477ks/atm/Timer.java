package edu.metrostate.by8477ks.atm;

public class Timer {
    long startTime = -1;
    long stopTime = -1;

    public Timer() {
        super();
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        this.stopTime = System.currentTimeMillis();
    }

    public long read() throws Exception {
        if (startTime < 0) {
            throw new Exception("Timer was not started");
        }
        if (stopTime < 0) {
            this.stop();
        }
        return stopTime - startTime;
    }

    @Override
    public String toString() {
        try {
            return String.format("%d ms", this.read());
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
