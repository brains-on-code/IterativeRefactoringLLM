package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * A Grow-only Counter (G-Counter) CRDT implementation.
 * Each replica maintains its own entry in a vector of counters.
 * The overall value is the sum of all entries.
 */
class GCounter {

    /** Per-replica counter values, keyed by replica ID. */
    private final Map<Integer, Integer> replicaCounters;

    /** ID of this replica. */
    private final int replicaId;

    /** Total number of replicas in the system. */
    private final int replicaCount;

    /**
     * Creates a new G-Counter for a given replica.
     *
     * @param replicaId    the ID of this replica (0-based index)
     * @param replicaCount total number of replicas
     */
    GCounter(int replicaId, int replicaCount) {
        this.replicaId = replicaId;
        this.replicaCount = replicaCount;
        this.replicaCounters = new HashMap<>();

        // Initialize all replica counters to zero
        for (int id = 0; id < replicaCount; id++) {
            replicaCounters.put(id, 0);
        }
    }

    /**
     * Increments the counter for this replica by one.
     */
    public void increment() {
        replicaCounters.put(replicaId, replicaCounters.get(replicaId) + 1);
    }

    /**
     * Returns the total value of the counter (sum of all replica counters).
     *
     * @return the total counter value
     */
    public int value() {
        int sum = 0;
        for (int count : replicaCounters.values()) {
            sum += count;
        }
        return sum;
    }

    /**
     * Checks if this counter is less than or equal to another counter
     * in the partial order defined by per-replica counts.
     *
     * @param other another GCounter to compare against
     * @return true if this counter is less than or equal to {@code other}
     */
    public boolean compare(GCounter other) {
        for (int id = 0; id < replicaCount; id++) {
            if (this.replicaCounters.get(id) > other.replicaCounters.get(id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges another counter into this one by taking the element-wise maximum
     * of the per-replica counters.
     *
     * @param other another GCounter to merge from
     */
    public void merge(GCounter other) {
        for (int id = 0; id < replicaCount; id++) {
            int mergedValue = Math.max(
                this.replicaCounters.get(id),
                other.replicaCounters.get(id)
            );
            this.replicaCounters.put(id, mergedValue);
        }
    }
}