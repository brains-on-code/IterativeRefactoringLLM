package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * PN-Counter (Positive-Negative Counter) is a state-based CRDT (Conflict-free Replicated Data Type)
 * designed for tracking counts with both increments and decrements in a distributed and concurrent environment.
 * It combines two G-Counters, one for increments (P) and one for decrements (N).
 * The total count is obtained by subtracting the value of the decrement counter from the increment counter.
 * This implementation supports incrementing, decrementing, querying the total count,
 * comparing with other PN-Counters, and merging with another PN-Counter
 * to compute the element-wise maximum for both increment and decrement counters.
 * (https://en.wikipedia.org/wiki/Conflict-free_replicated_data_type)
 *
 * author itakurah (Niklas Hoefflin) (https://github.com/itakurah)
 */
class PNCounter {

    private final Map<Integer, Integer> positiveCounter;
    private final Map<Integer, Integer> negativeCounter;
    private final int nodeId;
    private final int nodeCount;

    /**
     * Constructs a PN-Counter for a cluster of n nodes.
     *
     * @param nodeId    The identifier of the current node.
     * @param nodeCount The number of nodes in the cluster.
     */
    PNCounter(int nodeId, int nodeCount) {
        this.nodeId = nodeId;
        this.nodeCount = nodeCount;
        this.positiveCounter = new HashMap<>();
        this.negativeCounter = new HashMap<>();
        initializeCounters();
    }

    private void initializeCounters() {
        for (int i = 0; i < nodeCount; i++) {
            positiveCounter.put(i, 0);
            negativeCounter.put(i, 0);
        }
    }

    /**
     * Increments the increment counter for the current node.
     */
    public void increment() {
        positiveCounter.put(nodeId, positiveCounter.get(nodeId) + 1);
    }

    /**
     * Increments the decrement counter for the current node.
     */
    public void decrement() {
        negativeCounter.put(nodeId, negativeCounter.get(nodeId) + 1);
    }

    /**
     * Gets the total value of the counter by subtracting the decrement counter from the increment counter.
     *
     * @return The total value of the counter.
     */
    public int value() {
        int sumPositive = sumCounter(positiveCounter);
        int sumNegative = sumCounter(negativeCounter);
        return sumPositive - sumNegative;
    }

    private int sumCounter(Map<Integer, Integer> counter) {
        return counter.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Compares the state of this PN-Counter with another PN-Counter.
     *
     * @param other The other PN-Counter to compare with.
     * @return True if the state of this PN-Counter is less than or equal to the state of the other PN-Counter.
     */
    public boolean compare(PNCounter other) {
        validateSameClusterSize(other);
        for (int i = 0; i < nodeCount; i++) {
            boolean positiveGreater = this.positiveCounter.get(i) > other.positiveCounter.get(i);
            boolean negativeGreater = this.negativeCounter.get(i) > other.negativeCounter.get(i);
            if (positiveGreater && negativeGreater) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges the state of this PN-Counter with another PN-Counter.
     *
     * @param other The other PN-Counter to merge with.
     */
    public void merge(PNCounter other) {
        validateSameClusterSize(other);
        for (int i = 0; i < nodeCount; i++) {
            positiveCounter.put(i, Math.max(this.positiveCounter.get(i), other.positiveCounter.get(i)));
            negativeCounter.put(i, Math.max(this.negativeCounter.get(i), other.negativeCounter.get(i)));
        }
    }

    private void validateSameClusterSize(PNCounter other) {
        if (this.nodeCount != other.nodeCount) {
            throw new IllegalArgumentException("Cannot operate on PN-Counters with different number of nodes");
        }
    }
}