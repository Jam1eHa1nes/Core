package com.core.qa.automation.common.utils;

/**
 * Simple utility class for measuring elapsed time between events.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     StopWatch sw = new StopWatch();
 *     sw.start();
 *     // ... code to time ...
 *     sw.stop();
 *     System.out.println(sw.getElapsedMillis() + " ms");
 * </pre>
 */
public class StopWatch {

    private long startTime = 0L;
    private long endTime = 0L;
    private boolean running = false;

    /**
     * Starts the stopwatch.
     *
     * @return this StopWatch for method chaining
     */
    public StopWatch start() {
        startTime = System.currentTimeMillis();
        running = true;
        return this;
    }

    /**
     * Stops the stopwatch.
     *
     * @return this StopWatch for method chaining
     */
    public StopWatch stop() {
        endTime = System.currentTimeMillis();
        running = false;
        return this;
    }

    /**
     * Resets the stopwatch.
     *
     * @return this StopWatch for method chaining
     */
    public StopWatch reset() {
        startTime = 0L;
        endTime = 0L;
        running = false;
        return this;
    }

    /**
     * Gets the elapsed time in milliseconds.
     *
     * @return elapsed time in milliseconds
     * @throws IllegalStateException if stopwatch was not properly started and stopped
     */
    public long getElapsedMillis() {
        if (startTime == 0L) {
            throw new IllegalStateException("StopWatch: start() must be called first");
        }
        if (running) {
            return System.currentTimeMillis() - startTime;
        }
        return endTime - startTime;
    }

    /**
     * Gets the elapsed time in seconds.
     *
     * @return elapsed time in seconds
     */
    public double getElapsedSeconds() {
        return getElapsedMillis() / 1000.0;
    }

    /**
     * Returns the elapsed time as a formatted string.
     *
     * @return formatted elapsed time (e.g., "123 milliseconds")
     */
    public String printElapsed() {
        return getElapsedMillis() + " milliseconds";
    }

    /**
     * Checks if the stopwatch is currently running.
     *
     * @return true if running, false otherwise
     */
    public boolean isRunning() {
        return running;
    }
}

