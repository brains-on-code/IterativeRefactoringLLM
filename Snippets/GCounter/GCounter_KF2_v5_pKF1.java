package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class GCounter {
    private final Map<Integer, Integer> replicaCounts;
    private final int localReplicaId;
    private final int replicaCount;

    GCounter(int localReplicaId, int replicaCount) {
        this.localReplicaId = localReplicaId;
        this.replicaCount = replicaCount;
        this.replicaCounts = new HashMap<>();

        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            replicaCounts.put(replicaId, 0);
        }
    }

    public void increment() {
        replicaCounts.put(localReplicaId, replicaCounts.get(localReplicaId) + 1);
    }

    public int getValue() {
        int total = 0;
        for (int count : replicaCounts.values()) {
            total += count;
        }
        return total;
    }

    public boolean isLessThanOrEqualTo(GCounter other) {
        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            if (this.replicaCounts.get(replicaId) > other.replicaCounts.get(replicaId)) {
                return false;
            }
        }
        return true;
    }

    public void merge(GCounter other) {
        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            this.replicaCounts.put(
                replicaId,
                Math.max(this.replicaCounts.get(replicaId), other.replicaCounts.get(replicaId))
            );
        }
    }
}