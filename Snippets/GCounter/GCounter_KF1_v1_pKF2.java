package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple vector clock implementation for tracking causal relationships
 * between events in a distributed system.
 */
class VectorClock {
    /** Map from process ID to its logical time. */
    private final Map<Integer, Integer> clock;
    /** The ID of this process. */
    private final int processId;
    /** Total number of processes in the system. */
    private final int processCount;

    /**
     * Creates a vector clock for a given process in a system with a fixed
     * number of processes. All clock entries are initialized to 0.
     *
     * @param processId    the ID of this process
     * @param processCount the total number of processes
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
     * Increments the logical time of this process in the vector clock.
     */
    public void tick() {
        clock.put(processId, clock.get(processId) + 1);
    }

    /**
     * Returns the sum of all logical times in the vector clock.
     *
     * @return the total of all entries in the vector clock
     */
    public int totalTime() {
        int sum = 0;
        for (int value : clock.values()) {
            sum += value;
        }
        return sum;
    }

    /**
     * Checks whether this vector clock is less than or equal to another
     * vector clock (component-wise).
     *
     * @param other the vector clock to compare against
     * @return true if this clock is less than or equal to {@code other}
     *         for every component; false otherwise
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
     * Merges another vector clock into this one by taking the component-wise
     * maximum of the two clocks.
     *
     * @param other the vector clock to merge from
     */
    public void merge(VectorClock other) {
        for (int i = 0; i < processCount; i++) {
            this.clock.put(i, Math.max(this.clock.get(i), other.clock.get(i)));
        }
    }
}