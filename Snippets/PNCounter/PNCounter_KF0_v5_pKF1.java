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
    private final Map<Integer, Integer> positiveCountsByReplicaId;
    private final Map<Integer, Integer> negativeCountsByReplicaId;
    private final int localReplicaId;
    private final int replicaCount;

    /**
     * Constructs a PN-Counter for a cluster of n replicas.
     *
     * @param localReplicaId The identifier of the current replica.
     * @param replicaCount   The number of replicas in the cluster.
     */
    PNCounter(int localReplicaId, int replicaCount) {
        this.localReplicaId = localReplicaId;
        this.replicaCount = replicaCount;
        this.positiveCountsByReplicaId = new HashMap<>();
        this.negativeCountsByReplicaId = new HashMap<>();

        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            positiveCountsByReplicaId.put(replicaId, 0);
            negativeCountsByReplicaId.put(replicaId, 0);
        }
    }

    /**
     * Increments the increment counter for the current replica.
     */
    public void increment() {
        positiveCountsByReplicaId.put(
            localReplicaId,
            positiveCountsByReplicaId.get(localReplicaId) + 1
        );
    }

    /**
     * Increments the decrement counter for the current replica.
     */
    public void decrement() {
        negativeCountsByReplicaId.put(
            localReplicaId,
            negativeCountsByReplicaId.get(localReplicaId) + 1
        );
    }

    /**
     * Gets the total value of the counter by subtracting the decrement counter from the increment counter.
     *
     * @return The total value of the counter.
     */
    public int value() {
        int totalPositive = positiveCountsByReplicaId.values().stream().mapToInt(Integer::intValue).sum();
        int totalNegative = negativeCountsByReplicaId.values().stream().mapToInt(Integer::intValue).sum();
        return totalPositive - totalNegative;
    }

    /**
     * Compares the state of this PN-Counter with another PN-Counter.
     *
     * @param otherCounter The other PN-Counter to compare with.
     * @return True if the state of this PN-Counter is less than or equal to the state of the other PN-Counter.
     */
    public boolean compare(PNCounter otherCounter) {
        if (this.replicaCount != otherCounter.replicaCount) {
            throw new IllegalArgumentException("Cannot compare PN-Counters with different number of replicas");
        }
        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            boolean hasGreaterPositive =
                this.positiveCountsByReplicaId.get(replicaId) > otherCounter.positiveCountsByReplicaId.get(replicaId);
            boolean hasGreaterNegative =
                this.negativeCountsByReplicaId.get(replicaId) > otherCounter.negativeCountsByReplicaId.get(replicaId);
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
        if (this.replicaCount != otherCounter.replicaCount) {
            throw new IllegalArgumentException("Cannot merge PN-Counters with different number of replicas");
        }
        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            this.positiveCountsByReplicaId.put(
                replicaId,
                Math.max(
                    this.positiveCountsByReplicaId.get(replicaId),
                    otherCounter.positiveCountsByReplicaId.get(replicaId)
                )
            );
            this.negativeCountsByReplicaId.put(
                replicaId,
                Math.max(
                    this.negativeCountsByReplicaId.get(replicaId),
                    otherCounter.negativeCountsByReplicaId.get(replicaId)
                )
            );
        }
    }
}