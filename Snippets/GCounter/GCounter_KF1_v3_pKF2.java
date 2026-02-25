package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * Vector clock for tracking causal relationships between events
 * in a distributed system.
 */
class VectorClock {

    /** Logical time per process ID. */
    private final Map<Integer, Integer> clock;

    /** ID of this process. */
    private final int processId;

    /** Total number of processes. */
    private final int processCount;

    /**
     * Creates a vector clock for a given process in a system
     * with a fixed number of processes. All entries start at 0.
     *
     * @param processId    ID of this process
     * @param processCount total number of processes
     */
    VectorClock(int processId, int processCount) {
        this.processId = processId;
        this.processCount = processCount;
        this.clock = new HashMap<>(processCount);

        for (int i = 0; i < processCount; i++) {
            clock.put(i, 0);
        }
    }

    /** Increments this process's logical time. */
    public void tick() {
        clock.put(processId, clock.get(processId) + 1);
    }

    /**
     * Returns the sum of all logical times.
     *
     * @return sum of all clock entries
     */
    public int totalTime() {
        int sum = 0;
        for (int value : clock.values()) {
            sum += value;
        }
        return sum;
    }

    /**
     * Checks if this clock is component-wise less than or equal to another.
     *
     * @param other clock to compare against
     * @return {@code true} if this[i] <= other[i] for all i; {@code false} otherwise
     */
    public boolean isLessThanOrEqual(VectorClock other) {
        for (int i = 0; i < processCount; i++) {
            if (clock.get(i) > other.clock.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges another clock into this one using component-wise maximum.
     *
     * @param other clock to merge from
     */
    public void merge(VectorClock other) {
        for (int i = 0; i < processCount; i++) {
            clock.put(i, Math.max(clock.get(i), other.clock.get(i)));
        }
    }
}