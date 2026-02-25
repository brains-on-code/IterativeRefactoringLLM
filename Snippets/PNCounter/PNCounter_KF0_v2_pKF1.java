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
    private final Map<Integer, Integer> positiveCountsByNodeId;
    private final Map<Integer, Integer> negativeCountsByNodeId;
    private final int localNodeId;
    private final int clusterSize;

    /**
     * Constructs a PN-Counter for a cluster of n nodes.
     *
     * @param localNodeId The identifier of the current node.
     * @param clusterSize The number of nodes in the cluster.
     */
    PNCounter(int localNodeId, int clusterSize) {
        this.localNodeId = localNodeId;
        this.clusterSize = clusterSize;
        this.positiveCountsByNodeId = new HashMap<>();
        this.negativeCountsByNodeId = new HashMap<>();

        for (int nodeId = 0; nodeId < clusterSize; nodeId++) {
            positiveCountsByNodeId.put(nodeId, 0);
            negativeCountsByNodeId.put(nodeId, 0);
        }
    }

    /**
     * Increments the increment counter for the current node.
     */
    public void increment() {
        positiveCountsByNodeId.put(localNodeId, positiveCountsByNodeId.get(localNodeId) + 1);
    }

    /**
     * Increments the decrement counter for the current node.
     */
    public void decrement() {
        negativeCountsByNodeId.put(localNodeId, negativeCountsByNodeId.get(localNodeId) + 1);
    }

    /**
     * Gets the total value of the counter by subtracting the decrement counter from the increment counter.
     *
     * @return The total value of the counter.
     */
    public int value() {
        int totalPositive = positiveCountsByNodeId.values().stream().mapToInt(Integer::intValue).sum();
        int totalNegative = negativeCountsByNodeId.values().stream().mapToInt(Integer::intValue).sum();
        return totalPositive - totalNegative;
    }

    /**
     * Compares the state of this PN-Counter with another PN-Counter.
     *
     * @param otherCounter The other PN-Counter to compare with.
     * @return True if the state of this PN-Counter is less than or equal to the state of the other PN-Counter.
     */
    public boolean compare(PNCounter otherCounter) {
        if (this.clusterSize != otherCounter.clusterSize) {
            throw new IllegalArgumentException("Cannot compare PN-Counters with different number of nodes");
        }
        for (int nodeId = 0; nodeId < clusterSize; nodeId++) {
            boolean hasGreaterPositive =
                this.positiveCountsByNodeId.get(nodeId) > otherCounter.positiveCountsByNodeId.get(nodeId);
            boolean hasGreaterNegative =
                this.negativeCountsByNodeId.get(nodeId) > otherCounter.negativeCountsByNodeId.get(nodeId);
            if (hasGreaterPositive && hasGreaterNegative) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges the state of this PN-Counter with another PN-Counter.
     *
     * @param otherCounter The other PN-Counter to merge with.
     */
    public void merge(PNCounter otherCounter) {
        if (this.clusterSize != otherCounter.clusterSize) {
            throw new IllegalArgumentException("Cannot merge PN-Counters with different number of nodes");
        }
        for (int nodeId = 0; nodeId < clusterSize; nodeId++) {
            this.positiveCountsByNodeId.put(
                nodeId,
                Math.max(
                    this.positiveCountsByNodeId.get(nodeId),
                    otherCounter.positiveCountsByNodeId.get(nodeId)
                )
            );
            this.negativeCountsByNodeId.put(
                nodeId,
                Math.max(
                    this.negativeCountsByNodeId.get(nodeId),
                    otherCounter.negativeCountsByNodeId.get(nodeId)
                )
            );
        }
    }
}