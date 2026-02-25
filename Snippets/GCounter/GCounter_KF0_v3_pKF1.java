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
    private final Map<Integer, Integer> perReplicaCounts;
    private final int localReplicaId;
    private final int numberOfReplicas;

    /**
     * Constructs a G-Counter for a cluster of replicas.
     *
     * @param localReplicaId   The identifier of this replica.
     * @param numberOfReplicas The number of replicas in the cluster.
     */
    GCounter(int localReplicaId, int numberOfReplicas) {
        this.localReplicaId = localReplicaId;
        this.numberOfReplicas = numberOfReplicas;
        this.perReplicaCounts = new HashMap<>();

        for (int replicaId = 0; replicaId < numberOfReplicas; replicaId++) {
            perReplicaCounts.put(replicaId, 0);
        }
    }

    /**
     * Increments the counter for the current replica.
     */
    public void increment() {
        perReplicaCounts.put(localReplicaId, perReplicaCounts.get(localReplicaId) + 1);
    }

    /**
     * Gets the total value of the counter by summing up values from all replicas.
     *
     * @return The total value of the counter.
     */
    public int value() {
        int totalCount = 0;
        for (int replicaCount : perReplicaCounts.values()) {
            totalCount += replicaCount;
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
        for (int replicaId = 0; replicaId < numberOfReplicas; replicaId++) {
            if (this.perReplicaCounts.get(replicaId) > otherCounter.perReplicaCounts.get(replicaId)) {
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
        for (int replicaId = 0; replicaId < numberOfReplicas; replicaId++) {
            int mergedValue = Math.max(
                this.perReplicaCounts.get(replicaId),
                otherCounter.perReplicaCounts.get(replicaId)
            );
            this.perReplicaCounts.put(replicaId, mergedValue);
        }
    }
}