package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * Grow-only counter (G-Counter) CRDT.
 *
 * Each replica maintains its own component of the counter and can only increment it.
 * The global value is the sum of all per-replica components.
 *
 * Operations:
 * - {@link #increment()} : increment this replica's component
 * - {@link #value()}     : read the global counter value
 * - {@link #compare(GCounter)} : partial order comparison between two counters
 * - {@link #merge(GCounter)}   : merge another counter into this one (element-wise max)
 *
 * (https://en.wikipedia.org/wiki/Conflict-free_replicated_data_type)
 */
class GCounter {

    /** Per-replica counts keyed by replica id. */
    private final Map<Integer, Integer> replicaCounts;

    /** Id of this replica. Must be in range [0, replicaCount). */
    private final int replicaId;

    /** Total number of replicas in the system. */
    private final int replicaCount;

    /**
     * Creates a G-Counter for a system with the given number of replicas.
     *
     * @param replicaId    id of this replica (0-based index)
     * @param replicaCount total number of replicas
     */
    GCounter(int replicaId, int replicaCount) {
        this.replicaId = replicaId;
        this.replicaCount = replicaCount;
        this.replicaCounts = new HashMap<>();

        initializeReplicaCounts();
    }

    /** Initializes all replica counts to zero. */
    private void initializeReplicaCounts() {
        for (int id = 0; id < replicaCount; id++) {
            replicaCounts.put(id, 0);
        }
    }

    /** Increments the component of the counter owned by this replica. */
    public void increment() {
        replicaCounts.put(replicaId, replicaCounts.get(replicaId) + 1);
    }

    /**
     * Returns the global value of the counter (sum of all replica components).
     *
     * @return total counter value
     */
    public int value() {
        int sum = 0;
        for (int count : replicaCounts.values()) {
            sum += count;
        }
        return sum;
    }

    /**
     * Checks whether this counter is less than or equal to another counter
     * in the G-Counter partial order.
     *
     * This holds iff for every replica id i: this[i] <= other[i].
     *
     * @param other another G-Counter
     * @return {@code true} if this counter is less than or equal to {@code other},
     *         {@code false} otherwise
     */
    public boolean compare(GCounter other) {
        for (int id = 0; id < replicaCount; id++) {
            if (replicaCounts.get(id) > other.replicaCounts.get(id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges another counter into this one by taking the element-wise maximum
     * of the per-replica components.
     *
     * After merge, for every replica id i: this[i] = max(this[i], other[i]).
     *
     * @param other another G-Counter to merge from
     */
    public void merge(GCounter other) {
        for (int id = 0; id < replicaCount; id++) {
            int mergedValue = Math.max(
                replicaCounts.get(id),
                other.replicaCounts.get(id)
            );
            replicaCounts.put(id, mergedValue);
        }
    }
}