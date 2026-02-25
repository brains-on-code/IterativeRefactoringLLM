package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class GCounter {
    private final Map<Integer, Integer> replicaIdToCountMap;
    private final int localReplicaId;
    private final int totalReplicaCount;

    GCounter(int localReplicaId, int totalReplicaCount) {
        this.localReplicaId = localReplicaId;
        this.totalReplicaCount = totalReplicaCount;
        this.replicaIdToCountMap = new HashMap<>();

        for (int replicaId = 0; replicaId < totalReplicaCount; replicaId++) {
            replicaIdToCountMap.put(replicaId, 0);
        }
    }

    public void increment() {
        replicaIdToCountMap.put(localReplicaId, replicaIdToCountMap.get(localReplicaId) + 1);
    }

    public int value() {
        int totalCount = 0;
        for (int replicaCount : replicaIdToCountMap.values()) {
            totalCount += replicaCount;
        }
        return totalCount;
    }

    public boolean isLessThanOrEqualTo(GCounter otherCounter) {
        for (int replicaId = 0; replicaId < totalReplicaCount; replicaId++) {
            if (this.replicaIdToCountMap.get(replicaId) > otherCounter.replicaIdToCountMap.get(replicaId)) {
                return false;
            }
        }
        return true;
    }

    public void merge(GCounter otherCounter) {
        for (int replicaId = 0; replicaId < totalReplicaCount; replicaId++) {
            this.replicaIdToCountMap.put(
                replicaId,
                Math.max(this.replicaIdToCountMap.get(replicaId), otherCounter.replicaIdToCountMap.get(replicaId))
            );
        }
    }
}