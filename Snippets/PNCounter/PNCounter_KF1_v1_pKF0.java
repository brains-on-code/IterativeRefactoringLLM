package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * PN-Counter (Positive-Negative Counter) CRDT implementation.
 *
 * Each node maintains two grow-only counters:
 * - increments: counts all increments performed by each node
 * - decrements: counts all decrements performed by each node
 *
 * The value of the counter is the sum of all increments minus the sum of all decrements.
 */
class PNCounter {

    private final Map<Integer, Integer> increments;
    private final Map<Integer, Integer> decrements;
    private final int nodeId;
    private final int nodeCount;

    /**
     * Creates a PN-Counter for a given node in a fixed-size cluster.
     *
     * @param nodeId    the id of this node (0-based index)
     * @param nodeCount total number of nodes in the cluster
     */
    PNCounter(int nodeId, int nodeCount) {
        this.nodeId = nodeId;
        this.nodeCount = nodeCount;
        this.increments = new HashMap<>();
        this.decrements = new HashMap<>();

        for (int i = 0; i < nodeCount; i++) {
            increments.put(i, 0);
            decrements.put(i, 0);
        }
    }

    /** Increments the counter for this node. */
    public void increment() {
        increments.put(nodeId, increments.get(nodeId) + 1);
    }

    /** Decrements the counter for this node. */
    public void decrement() {
        decrements.put(nodeId, decrements.get(nodeId) + 1);
    }

    /** Returns the current value of the PN-Counter. */
    public int getValue() {
        int totalIncrements = increments.values().stream().mapToInt(Integer::intValue).sum();
        int totalDecrements = decrements.values().stream().mapToInt(Integer::intValue).sum();
        return totalIncrements - totalDecrements;
    }

    /**
     * Checks if this PN-Counter is less than or equal to another PN-Counter
     * in the partial order defined for PN-Counters.
     *
     * @param other another PN-Counter
     * @return true if this counter is less than or equal to the other, false otherwise
     */
    public boolean isLessThanOrEqual(PNCounter other) {
        ensureSameNodeCount(other);

        for (int i = 0; i < nodeCount; i++) {
            if (this.increments.get(i) > other.increments.get(i)
                && this.decrements.get(i) > other.decrements.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges another PN-Counter into this one by taking the element-wise maximum
     * of increments and decrements.
     *
     * @param other another PN-Counter
     */
    public void merge(PNCounter other) {
        ensureSameNodeCount(other);

        for (int i = 0; i < nodeCount; i++) {
            this.increments.put(i, Math.max(this.increments.get(i), other.increments.get(i)));
            this.decrements.put(i, Math.max(this.decrements.get(i), other.decrements.get(i)));
        }
    }

    private void ensureSameNodeCount(PNCounter other) {
        if (this.nodeCount != other.nodeCount) {
            throw new IllegalArgumentException("Cannot operate on PN-Counters with different number of nodes");
        }
    }
}