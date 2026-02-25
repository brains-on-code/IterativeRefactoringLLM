package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class GCounter {
    private final Map<Integer, Integer> replicaCountsById;
    private final int localReplicaId;
    private final int replicaCount;

    GCounter(int localReplicaId, int replicaCount) {
        this.localReplicaId = localReplicaId;
        this.replicaCount = replicaCount;
        this.replicaCountsById = new HashMap<>();

        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            replicaCountsById.put(replicaId, 0);
        }
    }

    public void increment() {
        replicaCountsById.put(localReplicaId, replicaCountsById.get(localReplicaId) + 1);
    }

    public int getValue() {
        int total = 0;
        for (int replicaCount : replicaCountsById.values()) {
            total += replicaCount;
        }
        return total;
    }

    public boolean isLessThanOrEqualTo(GCounter other) {
        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            if (this.replicaCountsById.get(replicaId) > other.replicaCountsById.get(replicaId)) {
                return false;
            }
        }
        return true;
    }

    public void merge(GCounter other) {
        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            this.replicaCountsById.put(
                replicaId,
                Math.max(this.replicaCountsById.get(replicaId), other.replicaCountsById.get(replicaId))
            );
        }
    }
}