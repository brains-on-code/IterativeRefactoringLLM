package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * PN-Counter (Positive-Negative Counter) is a state-based CRDT (Conflict-free Replicated Data Type)
 * that supports both increments and decrements in a distributed system.
 *
 * <p>It is implemented as two internal G-Counters:
 * <ul>
 *   <li>P-Counter: tracks increments</li>
 *   <li>N-Counter: tracks decrements</li>
 * </ul>
 *
 * <p>The observable value is {@code sum(P) - sum(N)}.
 *
 * <p>This implementation supports:
 * <ul>
 *   <li>Incrementing and decrementing the local replica</li>
 *   <li>Reading the current value</li>
 *   <li>Comparing two PN-Counters</li>
 *   <li>Merging two PN-Counters by element-wise maximum</li>
 * </ul>
 *
 * @author itakurah
 */
class PNCounter {

    /** Per-node increment (positive) component. */
    private final Map<Integer, Integer> pCounter;

    /** Per-node decrement (negative) component. */
    private final Map<Integer, Integer> nCounter;

    /** Identifier of this node/replica. */
    private final int myId;

    /** Total number of nodes/replicas in the cluster. */
    private final int nodeCount;

    /**
     * Creates a PN-Counter for a cluster of {@code nodeCount} nodes.
     *
     * @param myId      identifier of the current node (0-based index)
     * @param nodeCount total number of nodes in the cluster
     */
    PNCounter(int myId, int nodeCount) {
        this.myId = myId;
        this.nodeCount = nodeCount;
        this.pCounter = new HashMap<>();
        this.nCounter = new HashMap<>();

        for (int i = 0; i < nodeCount; i++) {
            pCounter.put(i, 0);
            nCounter.put(i, 0);
        }
    }

    /**
     * Increments the local positive component.
     */
    public void increment() {
        pCounter.put(myId, pCounter.get(myId) + 1);
    }

    /**
     * Increments the local negative component.
     */
    public void decrement() {
        nCounter.put(myId, nCounter.get(myId) + 1);
    }

    /**
     * Returns the current value of the PN-Counter:
     * {@code sum(P) - sum(N)}.
     *
     * @return current counter value
     */
    public int value() {
        int sumP = pCounter.values().stream().mapToInt(Integer::intValue).sum();
        int sumN = nCounter.values().stream().mapToInt(Integer::intValue).sum();
        return sumP - sumN;
    }

    /**
     * Checks whether this PN-Counter is less than or equal to another PN-Counter
     * in the CRDT partial order.
     *
     * <p>Formally, this returns {@code true} if for every node {@code i}:
     * <ul>
     *   <li>{@code this.P[i] <= other.P[i]}</li>
     *   <li>{@code this.N[i] <= other.N[i]}</li>
     * </ul>
     *
     * @param other PN-Counter to compare against
     * @return {@code true} if this counter is less than or equal to {@code other}
     * @throws IllegalArgumentException if the counters have different node counts
     */
    public boolean compare(PNCounter other) {
        ensureSameNodeCount(other);

        for (int i = 0; i < nodeCount; i++) {
            boolean pGreater = this.pCounter.get(i) > other.pCounter.get(i);
            boolean nGreater = this.nCounter.get(i) > other.nCounter.get(i);
            if (pGreater && nGreater) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges the state of {@code other} into this PN-Counter by taking the
     * element-wise maximum of both P and N components.
     *
     * @param other PN-Counter to merge from
     * @throws IllegalArgumentException if the counters have different node counts
     */
    public void merge(PNCounter other) {
        ensureSameNodeCount(other);

        for (int i = 0; i < nodeCount; i++) {
            pCounter.put(i, Math.max(this.pCounter.get(i), other.pCounter.get(i)));
            nCounter.put(i, Math.max(this.nCounter.get(i), other.nCounter.get(i)));
        }
    }

    /**
     * Ensures that the given counter has the same number of nodes as this one.
     *
     * @param other the counter to check
     * @throws IllegalArgumentException if node counts differ
     */
    private void ensureSameNodeCount(PNCounter other) {
        if (this.nodeCount != other.nodeCount) {
            throw new IllegalArgumentException("Cannot operate on PN-Counters with different number of nodes");
        }
    }
}