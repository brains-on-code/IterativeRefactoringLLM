package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * PN-Counter (Positive-Negative Counter) CRDT.
 *
 * <p>Each replica tracks:
 * <ul>
 *     <li>Per-node positive increments (P)</li>
 *     <li>Per-node negative increments (N)</li>
 * </ul>
 *
 * <p>Counter value:
 * <pre>
 *     value = sum(P) - sum(N)
 * </pre>
 */
class PNCounter {

    /** Per-node positive increments. */
    private final Map<Integer, Integer> positiveCounts;

    /** Per-node negative increments. */
    private final Map<Integer, Integer> negativeCounts;

    /** This node's identifier (index in the maps). */
    private final int nodeId;

    /** Total number of nodes in the system. */
    private final int numberOfNodes;

    /**
     * Creates a PN-Counter replica for a specific node.
     *
     * @param nodeId        identifier (index) of this node
     * @param numberOfNodes total number of nodes in the system
     */
    PNCounter(int nodeId, int numberOfNodes) {
        this.nodeId = nodeId;
        this.numberOfNodes = numberOfNodes;
        this.positiveCounts = new HashMap<>();
        this.negativeCounts = new HashMap<>();

        for (int i = 0; i < numberOfNodes; i++) {
            positiveCounts.put(i, 0);
            negativeCounts.put(i, 0);
        }
    }

    /** Increments the counter at this node. */
    public void increment() {
        positiveCounts.put(nodeId, positiveCounts.get(nodeId) + 1);
    }

    /** Decrements the counter at this node. */
    public void decrement() {
        negativeCounts.put(nodeId, negativeCounts.get(nodeId) + 1);
    }

    /**
     * Returns the current value of the counter.
     *
     * @return sum of all positive counts minus sum of all negative counts
     */
    public int getValue() {
        int positiveSum = positiveCounts.values().stream().mapToInt(Integer::intValue).sum();
        int negativeSum = negativeCounts.values().stream().mapToInt(Integer::intValue).sum();
        return positiveSum - negativeSum;
    }

    /**
     * Checks whether this state is less than or equal to another PN-Counter
     * in the PN-Counter partial order.
     *
     * <p>For every node {@code i}:
     * <pre>
     *     this.P[i] <= other.P[i]  AND  this.N[i] <= other.N[i]
     * </pre>
     *
     * @param other another PN-Counter instance
     * @return {@code true} if this state is less than or equal to {@code other}
     * @throws IllegalArgumentException if the counters have different numbers of nodes
     */
    public boolean isLessThanOrEqual(PNCounter other) {
        validateSameNumberOfNodes(other);

        for (int i = 0; i < numberOfNodes; i++) {
            int thisPositive = this.positiveCounts.get(i);
            int otherPositive = other.positiveCounts.get(i);
            int thisNegative = this.negativeCounts.get(i);
            int otherNegative = other.negativeCounts.get(i);

            if (thisPositive > otherPositive || thisNegative > otherNegative) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges another PN-Counter state into this one.
     *
     * <p>For every node {@code i}:
     * <pre>
     *     this.P[i] = max(this.P[i], other.P[i])
     *     this.N[i] = max(this.N[i], other.N[i])
     * </pre>
     *
     * @param other PN-Counter instance to merge into this one
     * @throws IllegalArgumentException if the counters have different numbers of nodes
     */
    public void merge(PNCounter other) {
        validateSameNumberOfNodes(other);

        for (int i = 0; i < numberOfNodes; i++) {
            int mergedPositive = Math.max(this.positiveCounts.get(i), other.positiveCounts.get(i));
            int mergedNegative = Math.max(this.negativeCounts.get(i), other.negativeCounts.get(i));

            this.positiveCounts.put(i, mergedPositive);
            this.negativeCounts.put(i, mergedNegative);
        }
    }

    /**
     * Ensures that two PN-Counters have the same number of nodes.
     *
     * @param other another PN-Counter instance
     * @throws IllegalArgumentException if the counters have different numbers of nodes
     */
    private void validateSameNumberOfNodes(PNCounter other) {
        if (this.numberOfNodes != other.numberOfNodes) {
            throw new IllegalArgumentException(
                "Cannot operate on PN-Counters with different number of nodes"
            );
        }
    }
}