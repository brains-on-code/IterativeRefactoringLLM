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
    private final Map<Integer, Integer> positiveCountsByReplica;
    private final Map<Integer, Integer> negativeCountsByReplica;
    private final int localReplicaId;
    private final int numberOfReplicas;

    /**
     * Constructs a PN-Counter for a cluster of n replicas.
     *
     * @param localReplicaId  The identifier of the current replica.
     * @param numberOfReplicas The number of replicas in the cluster.
     */
    PNCounter(int localReplicaId, int numberOfReplicas) {
        this.localReplicaId = localReplicaId;
        this.numberOfReplicas = numberOfReplicas;
        this.positiveCountsByReplica = new HashMap<>();
        this.negativeCountsByReplica = new HashMap<>();

        for (int replicaId = 0; replicaId < numberOfReplicas; replicaId++) {
            positiveCountsByReplica.put(replicaId, 0);
            negativeCountsByReplica.put(replicaId, 0);
        }
    }

    /**
     * Increments the increment counter for the current replica.
     */
    public void increment() {
        positiveCountsByReplica.put(
            localReplicaId,
            positiveCountsByReplica.get(localReplicaId) + 1
        );
    }

    /**
     * Increments the decrement counter for the current replica.
     */
    public void decrement() {
        negativeCountsByReplica.put(
            localReplicaId,
            negativeCountsByReplica.get(localReplicaId) + 1
        );
    }

    /**
     * Gets the total value of the counter by subtracting the decrement counter from the increment counter.
     *
     * @return The total value of the counter.
     */
    public int value() {
        int totalPositive = positiveCountsByReplica.values().stream().mapToInt(Integer::intValue).sum();
        int totalNegative = negativeCountsByReplica.values().stream().mapToInt(Integer::intValue).sum();
        return totalPositive - totalNegative;
    }

    /**
     * Compares the state of this PN-Counter with another PN-Counter.
     *
     * @param otherCounter The other PN-Counter to compare with.
     * @return True if the state of this PN-Counter is less than or equal to the state of the other PN-Counter.
     */
    public boolean compare(PNCounter otherCounter) {
        if (this.numberOfReplicas != otherCounter.numberOfReplicas) {
            throw new IllegalArgumentException("Cannot compare PN-Counters with different number of replicas");
        }
        for (int replicaId = 0; replicaId < numberOfReplicas; replicaId++) {
            boolean hasGreaterPositive =
                this.positiveCountsByReplica.get(replicaId) > otherCounter.positiveCountsByReplica.get(replicaId);
            boolean hasGreaterNegative =
                this.negativeCountsByReplica.get(replicaId) > otherCounter.negativeCountsByReplica.get(replicaId);
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
        if (this.numberOfReplicas != otherCounter.numberOfReplicas) {
            throw new IllegalArgumentException("Cannot merge PN-Counters with different number of replicas");
        }
        for (int replicaId = 0; replicaId < numberOfReplicas; replicaId++) {
            this.positiveCountsByReplica.put(
                replicaId,
                Math.max(
                    this.positiveCountsByReplica.get(replicaId),
                    otherCounter.positiveCountsByReplica.get(replicaId)
                )
            );
            this.negativeCountsByReplica.put(
                replicaId,
                Math.max(
                    this.negativeCountsByReplica.get(replicaId),
                    otherCounter.negativeCountsByReplica.get(replicaId)
                )
            );
        }
    }
}