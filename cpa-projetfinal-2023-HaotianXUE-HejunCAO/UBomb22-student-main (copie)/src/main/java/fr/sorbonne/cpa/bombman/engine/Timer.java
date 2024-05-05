

package fr.sorbonne.cpa.bombman.engine;

import java.io.Serializable;

public class Timer {
    private long duration;
    private long startTime;
    private boolean running = false;
    private boolean requested = false;
    private long remaining;

    // Set a timer for a duration in ms
    public Timer(long duration) {
        this.duration = duration;
        remaining = duration;
    }

    public void update(long now) {
        // time is in ns
        if (running) {
            remaining = duration - (now - startTime) / 1000000;
            if (remaining < 0) {
                running = false;
            }
        } else if (requested) {
            running = true;
            requested = false;
            startTime = now;
            remaining = duration;
        }
    }

    public long remaining() {
        return remaining;
    }

    /**
     * Starts the game loop which handles all updates and rendering tasks at set intervals.
     * This method initializes the main loop of the game, which continuously checks for user input,
     * updates game states, and renders the graphics onto the stage.
     */
    public void start() {
        if (!running)
            requested = true;
    }

    public boolean isRunning() {
        return running || requested;
    }
    public void setRunning(boolean running){
        this.running = running;
    }

    public void setDuration(long duration){
        this.duration = duration;
    }

    public long getDuration(){
        return this.duration;
    }
}
