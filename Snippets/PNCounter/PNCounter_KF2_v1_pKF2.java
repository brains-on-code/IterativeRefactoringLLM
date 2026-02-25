package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * A PN-Counter (Positive-Negative Counter) CRDT implementation.
 * Maintains separate grow-only counters for increments (P) and decrements (N)
 * across multiple replicas identified by an integer ID.
 */
class PNCounter {

    /** Per-replica positive (increment) counts. */
    private final Map<Integer, Integer> positiveCounts;

    /** Per-replica negative (decrement) counts. */
    private final Map<Integer, Integer> negativeCounts;

    /** ID of this replica. */
    private final int replicaId;

    /** Total number of replicas in the system. */
    private final int replicaCount;

    /**
     * Creates a PN-Counter for a given replica.
     *
     * @param replicaId    the ID of this replica (0-based index)
     * @param replicaCount total number of replicas
     */
    PNCounter(int replicaId, int replicaCount) {
        this.replicaId = replicaId;
        this.replicaCount = replicaCount;
        this.positiveCounts = new HashMap<>();
        this.negativeCounts = new HashMap<>();

        for (int i = 0; i < replicaCount; i++) {
            positiveCounts.put(i, 0);
            negativeCounts.put(i, 0);
        }
    }

    /** Increments the counter at this replica. */
    public void increment() {
        positiveCounts.put(replicaId, positiveCounts.get(replicaId) + 1);
    }

    /** Decrements the counter at this replica. */
    public void decrement() {
        negativeCounts.put(replicaId, negativeCounts.get(replicaId) + 1);
    }

    /**
     * Returns the current value of the counter, computed as
     * the sum of all positive counts minus the sum of all negative counts.
     *
     * @return current counter value
     */
    public int value() {
        int sumPositive = positiveCounts.values().stream().mapToInt(Integer::intValue).sum();
        int sumNegative = negativeCounts.values().stream().mapToInt(Integer::intValue).sum();
        return sumPositive - sumNegative;
    }

    /**
     * Compares this PN-Counter with another to determine if this counter
     * is less than or equal to the other in the partial order defined by
     * per-replica counts.
     *
     * @param other the other PN-Counter
     * @return true if this counter is less than or equal to {@code other}, false otherwise
     * @throws IllegalArgumentException if the counters have different replica counts
     */
    public boolean compare(PNCounter other) {
        ensureSameReplicaCount(other);

        for (int i = 0; i < replicaCount; i++) {
            boolean greaterPositive = this.positiveCounts.get(i) > other.positiveCounts.get(i);
            boolean greaterNegative = this.negativeCounts.get(i) > other.negativeCounts.get(i);
            if (greaterPositive && greaterNegative) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges another PN-Counter into this one by taking the element-wise maximum
     * of the per-replica positive and negative counts.
     *
     * @param other the other PN-Counter to merge
     * @throws IllegalArgumentException if the counters have different replica counts
     */
    public void merge(PNCounter other) {
        ensureSameReplicaCount(other);

        for (int i = 0; i < replicaCount; i++) {
            positiveCounts.put(i, Math.max(this.positiveCounts.get(i), other.positiveCounts.get(i)));
            negativeCounts.put(i, Math.max(this.negativeCounts.get(i), other.negativeCounts.get(i)));
        }
    }

    /**
     * Ensures that the given counter has the same replica count as this one.
     *
     * @param other the other PN-Counter
     * @throws IllegalArgumentException if the replica counts differ
     */
    private void ensureSameReplicaCount(PNCounter other) {
        if (this.replicaCount != other.replicaCount) {
            throw new IllegalArgumentException("Cannot operate on PN-Counters with different numbers of replicas");
        }
    }
}