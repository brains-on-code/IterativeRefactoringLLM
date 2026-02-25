package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * G-Counter (Grow-only Counter) is a state-based CRDT (Conflict-free Replicated Data Type)
 * designed for tracking counts in a distributed and concurrent environment.
 * Each process maintains its own counter, allowing only increments. The total count
 * is obtained by summing individual process counts.
 * This implementation supports incrementing, querying the total count,
 * comparing with other G-Counters, and merging with another G-Counter
 * to compute the element-wise maximum.
 * (https://en.wikipedia.org/wiki/Conflict-free_replicated_data_type)
 *
 * author itakurah (https://github.com/itakurah)
 */
class GCounter {
    private final Map<Integer, Integer> nodeCounters;
    private final int nodeId;
    private final int clusterSize;

    /**
     * Constructs a G-Counter for a cluster of nodes.
     *
     * @param nodeId      The identifier of this node.
     * @param clusterSize The number of nodes in the cluster.
     */
    GCounter(int nodeId, int clusterSize) {
        this.nodeId = nodeId;
        this.clusterSize = clusterSize;
        this.nodeCounters = new HashMap<>();

        for (int i = 0; i < clusterSize; i++) {
            nodeCounters.put(i, 0);
        }
    }

    /**
     * Increments the counter for the current node.
     */
    public void increment() {
        nodeCounters.put(nodeId, nodeCounters.get(nodeId) + 1);
    }

    /**
     * Gets the total value of the counter by summing up values from all nodes.
     *
     * @return The total value of the counter.
     */
    public int value() {
        int totalCount = 0;
        for (int nodeCount : nodeCounters.values()) {
            totalCount += nodeCount;
        }
        return totalCount;
    }

    /**
     * Compares the state of this G-Counter with another G-Counter.
     *
     * @param otherCounter The other G-Counter to compare with.
     * @return True if the state of this G-Counter is less than or equal to the state of the other G-Counter.
     */
    public boolean compare(GCounter otherCounter) {
        for (int nodeIndex = 0; nodeIndex < clusterSize; nodeIndex++) {
            if (this.nodeCounters.get(nodeIndex) > otherCounter.nodeCounters.get(nodeIndex)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges the state of this G-Counter with another G-Counter.
     *
     * @param otherCounter The other G-Counter to merge with.
     */
    public void merge(GCounter otherCounter) {
        for (int nodeIndex = 0; nodeIndex < clusterSize; nodeIndex++) {
            int mergedValue = Math.max(
                this.nodeCounters.get(nodeIndex),
                otherCounter.nodeCounters.get(nodeIndex)
            );
            this.nodeCounters.put(nodeIndex, mergedValue);
        }
    }
}