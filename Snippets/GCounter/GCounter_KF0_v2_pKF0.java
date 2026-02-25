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

    private final Map<Integer, Integer> counterMap;
    private final int nodeId;
    private final int nodeCount;

    /**
     * Constructs a G-Counter for a cluster of {@code nodeCount} nodes.
     *
     * @param nodeId    the id of this node
     * @param nodeCount the number of nodes in the cluster
     */
    GCounter(int nodeId, int nodeCount) {
        this.nodeId = nodeId;
        this.nodeCount = nodeCount;
        this.counterMap = new HashMap<>(nodeCount);
        initializeCounters();
    }

    private void initializeCounters() {
        for (int i = 0; i < nodeCount; i++) {
            counterMap.put(i, 0);
        }
    }

    /**
     * Increments the counter for the current node.
     */
    public void increment() {
        counterMap.merge(nodeId, 1, Integer::sum);
    }

    /**
     * Gets the total value of the counter by summing up values from all nodes.
     *
     * @return the total value of the counter
     */
    public int value() {
        return counterMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Compares the state of this G-Counter with another G-Counter.
     *
     * @param other the other G-Counter to compare with
     * @return {@code true} if the state of this G-Counter is less than or equal
     *         to the state of the other G-Counter
     */
    public boolean compare(GCounter other) {
        for (int i = 0; i < nodeCount; i++) {
            if (getValueForNode(i) > other.getValueForNode(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges the state of this G-Counter with another G-Counter.
     *
     * @param other the other G-Counter to merge with
     */
    public void merge(GCounter other) {
        for (int i = 0; i < nodeCount; i++) {
            int mergedValue = Math.max(getValueForNode(i), other.getValueForNode(i));
            counterMap.put(i, mergedValue);
        }
    }

    private int getValueForNode(int nodeIndex) {
        return counterMap.getOrDefault(nodeIndex, 0);
    }
}