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
    private final Map<Integer, Integer> replicaCounters;
    private final int localReplicaId;
    private final int replicaCount;

    /**
     * Constructs a G-Counter for a cluster of replicas.
     *
     * @param localReplicaId The identifier of this replica.
     * @param replicaCount   The number of replicas in the cluster.
     */
    GCounter(int localReplicaId, int replicaCount) {
        this.localReplicaId = localReplicaId;
        this.replicaCount = replicaCount;
        this.replicaCounters = new HashMap<>();

        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            replicaCounters.put(replicaId, 0);
        }
    }

    /**
     * Increments the counter for the current replica.
     */
    public void increment() {
        replicaCounters.put(localReplicaId, replicaCounters.get(localReplicaId) + 1);
    }

    /**
     * Gets the total value of the counter by summing up values from all replicas.
     *
     * @return The total value of the counter.
     */
    public int getValue() {
        int totalCount = 0;
        for (int replicaValue : replicaCounters.values()) {
            totalCount += replicaValue;
        }
        return totalCount;
    }

    /**
     * Compares the state of this G-Counter with another G-Counter.
     *
     * @param other The other G-Counter to compare with.
     * @return True if the state of this G-Counter is less than or equal to the state of the other G-Counter.
     */
    public boolean isLessThanOrEqualTo(GCounter other) {
        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            if (this.replicaCounters.get(replicaId) > other.replicaCounters.get(replicaId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges the state of this G-Counter with another G-Counter.
     *
     * @param other The other G-Counter to merge with.
     */
    public void merge(GCounter other) {
        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            int mergedValue =
                Math.max(this.replicaCounters.get(replicaId), other.replicaCounters.get(replicaId));
            this.replicaCounters.put(replicaId, mergedValue);
        }
    }
}