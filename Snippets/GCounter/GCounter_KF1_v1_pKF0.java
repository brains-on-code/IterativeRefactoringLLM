package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * Vector clock implementation for tracking causal relationships between events
 * in a distributed system.
 */
class VectorClock {
    private final Map<Integer, Integer> clock;
    private final int processId;
    private final int processCount;

    /**
     * Creates a vector clock for a given process in a system with a fixed number
     * of processes.
     *
     * @param processId    the id of this process
     * @param processCount the total number of processes in the system
     */
    VectorClock(int processId, int processCount) {
        this.processId = processId;
        this.processCount = processCount;
        this.clock = new HashMap<>();

        for (int i = 0; i < processCount; i++) {
            clock.put(i, 0);
        }
    }

    /**
     * Increments the clock value for this process, representing a local event.
     */
    public void tick() {
        clock.put(processId, clock.get(processId) + 1);
    }

    /**
     * Returns the sum of all entries in the vector clock.
     *
     * @return the total of all clock values
     */
    public int getTotalEvents() {
        int total = 0;
        for (int value : clock.values()) {
            total += value;
        }
        return total;
    }

    /**
     * Checks if this vector clock is less than or equal to another vector clock
     * (i.e., if all components are less than or equal).
     *
     * @param other the other vector clock to compare against
     * @return true if this clock is less than or equal to the other, false otherwise
     */
    public boolean isLessThanOrEqual(VectorClock other) {
        for (int i = 0; i < processCount; i++) {
            if (this.clock.get(i) > other.clock.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges this vector clock with another by taking the component-wise maximum.
     *
     * @param other the other vector clock to merge with
     */
    public void merge(VectorClock other) {
        for (int i = 0; i < processCount; i++) {
            this.clock.put(i, Math.max(this.clock.get(i), other.clock.get(i)));
        }
    }
}