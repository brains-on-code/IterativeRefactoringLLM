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
        if (nodeCount <= 0) {
            throw new IllegalArgumentException("nodeCount must be positive");
        }
        if (nodeId < 0 || nodeId >= nodeCount) {
            throw new IllegalArgumentException("nodeId must be in range [0, nodeCount)");
        }

        this.nodeId = nodeId;
        this.nodeCount = nodeCount;
        this.increments = new HashMap<>(nodeCount);
        this.decrements = new HashMap<>(nodeCount);

        initializeCounters();
    }

    private void initializeCounters() {
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
        int totalIncrements = sumValues(increments);
        int totalDecrements = sumValues(decrements);
        return totalIncrements - totalDecrements;
    }

    private int sumValues(Map<Integer, Integer> map) {
        return map.values().stream().mapToInt(Integer::intValue).sum();
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
            boolean incrementsGreater = this.increments.get(i) > other.increments.get(i);
            boolean decrementsGreater = this.decrements.get(i) > other.decrements.get(i);
            if (incrementsGreater && decrementsGreater) {
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
            increments.put(i, Math.max(increments.get(i), other.increments.get(i)));
            decrements.put(i, Math.max(decrements.get(i), other.decrements.get(i)));
        }
    }

    private void ensureSameNodeCount(PNCounter other) {
        if (other == null) {
            throw new IllegalArgumentException("Other PN-Counter must not be null");
        }
        if (this.nodeCount != other.nodeCount) {
            throw new IllegalArgumentException(
                "Cannot operate on PN-Counters with different number of nodes"
            );
        }
    }
}