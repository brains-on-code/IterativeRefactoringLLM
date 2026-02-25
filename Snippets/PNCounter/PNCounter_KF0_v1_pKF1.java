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
    private final Map<Integer, Integer> positiveCounterByNodeId;
    private final Map<Integer, Integer> negativeCounterByNodeId;
    private final int nodeId;
    private final int numberOfNodes;

    /**
     * Constructs a PN-Counter for a cluster of n nodes.
     *
     * @param nodeId        The identifier of the current node.
     * @param numberOfNodes The number of nodes in the cluster.
     */
    PNCounter(int nodeId, int numberOfNodes) {
        this.nodeId = nodeId;
        this.numberOfNodes = numberOfNodes;
        this.positiveCounterByNodeId = new HashMap<>();
        this.negativeCounterByNodeId = new HashMap<>();

        for (int currentNodeId = 0; currentNodeId < numberOfNodes; currentNodeId++) {
            positiveCounterByNodeId.put(currentNodeId, 0);
            negativeCounterByNodeId.put(currentNodeId, 0);
        }
    }

    /**
     * Increments the increment counter for the current node.
     */
    public void increment() {
        positiveCounterByNodeId.put(nodeId, positiveCounterByNodeId.get(nodeId) + 1);
    }

    /**
     * Increments the decrement counter for the current node.
     */
    public void decrement() {
        negativeCounterByNodeId.put(nodeId, negativeCounterByNodeId.get(nodeId) + 1);
    }

    /**
     * Gets the total value of the counter by subtracting the decrement counter from the increment counter.
     *
     * @return The total value of the counter.
     */
    public int value() {
        int totalPositive = positiveCounterByNodeId.values().stream().mapToInt(Integer::intValue).sum();
        int totalNegative = negativeCounterByNodeId.values().stream().mapToInt(Integer::intValue).sum();
        return totalPositive - totalNegative;
    }

    /**
     * Compares the state of this PN-Counter with another PN-Counter.
     *
     * @param otherCounter The other PN-Counter to compare with.
     * @return True if the state of this PN-Counter is less than or equal to the state of the other PN-Counter.
     */
    public boolean compare(PNCounter otherCounter) {
        if (this.numberOfNodes != otherCounter.numberOfNodes) {
            throw new IllegalArgumentException("Cannot compare PN-Counters with different number of nodes");
        }
        for (int currentNodeId = 0; currentNodeId < numberOfNodes; currentNodeId++) {
            boolean thisHasGreaterPositive =
                this.positiveCounterByNodeId.get(currentNodeId) > otherCounter.positiveCounterByNodeId.get(currentNodeId);
            boolean thisHasGreaterNegative =
                this.negativeCounterByNodeId.get(currentNodeId) > otherCounter.negativeCounterByNodeId.get(currentNodeId);
            if (thisHasGreaterPositive && thisHasGreaterNegative) {
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
        if (this.numberOfNodes != otherCounter.numberOfNodes) {
            throw new IllegalArgumentException("Cannot merge PN-Counters with different number of nodes");
        }
        for (int currentNodeId = 0; currentNodeId < numberOfNodes; currentNodeId++) {
            this.positiveCounterByNodeId.put(
                currentNodeId,
                Math.max(
                    this.positiveCounterByNodeId.get(currentNodeId),
                    otherCounter.positiveCounterByNodeId.get(currentNodeId)
                )
            );
            this.negativeCounterByNodeId.put(
                currentNodeId,
                Math.max(
                    this.negativeCounterByNodeId.get(currentNodeId),
                    otherCounter.negativeCounterByNodeId.get(currentNodeId)
                )
            );
        }
    }
}