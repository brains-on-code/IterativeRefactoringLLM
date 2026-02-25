package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * PN-Counter (Positive-Negative Counter) CRDT.
 *
 * <p>Each replica maintains:
 * <ul>
 *   <li>a grow-only counter for increments (positiveCounts)</li>
 *   <li>a grow-only counter for decrements (negativeCounts)</li>
 * </ul>
 *
 * <p>The logical value is: sum(positiveCounts) - sum(negativeCounts).
 */
class PNCounter {

    /** Per-replica increment counts. */
    private final Map<Integer, Integer> positiveCounts;

    /** Per-replica decrement counts. */
    private final Map<Integer, Integer> negativeCounts;

    /** ID of this replica (0-based). */
    private final int replicaId;

    /** Total number of replicas. */
    private final int replicaCount;

    /**
     * Constructs a PN-Counter for a specific replica.
     *
     * @param replicaId    ID of this replica (0-based index)
     * @param replicaCount total number of replicas
     */
    PNCounter(int replicaId, int replicaCount) {
        this.replicaId = replicaId;
        this.replicaCount = replicaCount;
        this.positiveCounts = new HashMap<>();
        this.negativeCounts = new HashMap<>();
        initializeReplicaCounts();
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
     * Returns the current logical value of the counter.
     *
     * @return sum of all positive counts minus sum of all negative counts
     */
    public int value() {
        int sumPositive = sumCounts(positiveCounts);
        int sumNegative = sumCounts(negativeCounts);
        return sumPositive - sumNegative;
    }

    /**
     * Checks whether this PN-Counter is less than or equal to another
     * in the partial order defined by per-replica counts.
     *
     * @param other the other PN-Counter
     * @return {@code true} if this counter is less than or equal to {@code other}, otherwise {@code false}
     * @throws IllegalArgumentException if the counters have different replica counts
     */
    public boolean compare(PNCounter other) {
        ensureSameReplicaCount(other);

        for (int i = 0; i < replicaCount; i++) {
            boolean greaterPositive = positiveCounts.get(i) > other.positiveCounts.get(i);
            boolean greaterNegative = negativeCounts.get(i) > other.negativeCounts.get(i);
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
            positiveCounts.put(i, Math.max(positiveCounts.get(i), other.positiveCounts.get(i)));
            negativeCounts.put(i, Math.max(negativeCounts.get(i), other.negativeCounts.get(i)));
        }
    }

    /** Initializes per-replica counts to zero for all replicas. */
    private void initializeReplicaCounts() {
        for (int i = 0; i < replicaCount; i++) {
            positiveCounts.put(i, 0);
            negativeCounts.put(i, 0);
        }
    }

    /**
     * Ensures that the given counter has the same replica count as this one.
     *
     * @param other the other PN-Counter
     * @throws IllegalArgumentException if the replica counts differ
     */
    private void ensureSameReplicaCount(PNCounter other) {
        if (replicaCount != other.replicaCount) {
            throw new IllegalArgumentException(
                "Cannot operate on PN-Counters with different numbers of replicas"
            );
        }
    }

    /** Returns the sum of all counts in the given map. */
    private int sumCounts(Map<Integer, Integer> counts) {
        return counts.values().stream().mapToInt(Integer::intValue).sum();
    }
}