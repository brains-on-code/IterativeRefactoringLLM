package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * A PN-Counter (Positive-Negative Counter) CRDT implementation.
 *
 * <p>This counter is replicated across multiple nodes. Each node maintains:
 * <ul>
 *     <li>A map of positive increments (P)</li>
 *     <li>A map of negative increments (N)</li>
 * </ul>
 *
 * <p>The value of the counter is computed as:
 * <pre>
 *     value = sum(P) - sum(N)
 * </pre>
 *
 * <p>Each instance of this class represents the state of the PN-Counter at a
 * particular node, identified by {@code nodeId}. The total number of nodes in
 * the system is {@code numberOfNodes}.
 */
class Class1 {

    /** Per-node positive increments (P). */
    private final Map<Integer, Integer> positiveCounts;

    /** Per-node negative increments (N). */
    private final Map<Integer, Integer> negativeCounts;

    /** Identifier of this node (index in the maps). */
    private final int nodeId;

    /** Total number of nodes participating in the counter. */
    private final int numberOfNodes;

    /**
     * Creates a PN-Counter replica for a specific node.
     *
     * @param nodeId        the identifier (index) of this node
     * @param numberOfNodes the total number of nodes in the system
     */
    Class1(int nodeId, int numberOfNodes) {
        this.nodeId = nodeId;
        this.numberOfNodes = numberOfNodes;
        this.positiveCounts = new HashMap<>();
        this.negativeCounts = new HashMap<>();

        for (int i = 0; i < numberOfNodes; i++) {
            positiveCounts.put(i, 0);
            negativeCounts.put(i, 0);
        }
    }

    /**
     * Increments the counter at this node.
     */
    public void method1() {
        positiveCounts.put(nodeId, positiveCounts.get(nodeId) + 1);
    }

    /**
     * Decrements the counter at this node.
     */
    public void method2() {
        negativeCounts.put(nodeId, negativeCounts.get(nodeId) + 1);
    }

    /**
     * Returns the current value of the PN-Counter.
     *
     * @return the sum of all positive counts minus the sum of all negative counts
     */
    public int method3() {
        int positiveSum = positiveCounts.values().stream().mapToInt(Integer::intValue).sum();
        int negativeSum = negativeCounts.values().stream().mapToInt(Integer::intValue).sum();
        return positiveSum - negativeSum;
    }

    /**
     * Checks whether this PN-Counter state is less than or equal to another
     * PN-Counter state in the partial order defined for PN-Counters.
     *
     * <p>Formally, this returns {@code true} if for every node {@code i}:
     * <pre>
     *     this.P[i] <= other.P[i]  AND  this.N[i] <= other.N[i]
     * </pre>
     *
     * @param other another PN-Counter instance
     * @return {@code true} if this state is less than or equal to {@code other}
     * @throws IllegalArgumentException if the counters have different numbers of nodes
     */
    public boolean method4(Class1 other) {
        if (this.numberOfNodes != other.numberOfNodes) {
            throw new IllegalArgumentException("Cannot compare PN-Counters with different number of nodes");
        }
        for (int i = 0; i < numberOfNodes; i++) {
            if (this.positiveCounts.get(i) > other.positiveCounts.get(i)
                && this.negativeCounts.get(i) > other.negativeCounts.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges another PN-Counter state into this one.
     *
     * <p>The merge operation is defined as the element-wise maximum of the
     * per-node positive and negative counts:
     * <pre>
     *     this.P[i] = max(this.P[i], other.P[i])
     *     this.N[i] = max(this.N[i], other.N[i])
     * </pre>
     *
     * @param other another PN-Counter instance to merge into this one
     * @throws IllegalArgumentException if the counters have different numbers of nodes
     */
    public void method5(Class1 other) {
        if (this.numberOfNodes != other.numberOfNodes) {
            throw new IllegalArgumentException("Cannot merge PN-Counters with different number of nodes");
        }
        for (int i = 0; i < numberOfNodes; i++) {
            this.positiveCounts.put(i, Math.max(this.positiveCounts.get(i), other.positiveCounts.get(i)));
            this.negativeCounts.put(i, Math.max(this.negativeCounts.get(i), other.negativeCounts.get(i)));
        }
    }
}