package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class GCounter {
    private final Map<Integer, Integer> countsByReplicaId;
    private final int localReplicaId;
    private final int numberOfReplicas;

    GCounter(int localReplicaId, int numberOfReplicas) {
        this.localReplicaId = localReplicaId;
        this.numberOfReplicas = numberOfReplicas;
        this.countsByReplicaId = new HashMap<>();

        for (int replicaId = 0; replicaId < numberOfReplicas; replicaId++) {
            countsByReplicaId.put(replicaId, 0);
        }
    }

    public void increment() {
        countsByReplicaId.put(localReplicaId, countsByReplicaId.get(localReplicaId) + 1);
    }

    public int getValue() {
        int totalCount = 0;
        for (int replicaCount : countsByReplicaId.values()) {
            totalCount += replicaCount;
        }
        return totalCount;
    }

    public boolean isLessThanOrEqualTo(GCounter otherCounter) {
        for (int replicaId = 0; replicaId < numberOfReplicas; replicaId++) {
            if (this.countsByReplicaId.get(replicaId) > otherCounter.countsByReplicaId.get(replicaId)) {
                return false;
            }
        }
        return true;
    }

    public void merge(GCounter otherCounter) {
        for (int replicaId = 0; replicaId < numberOfReplicas; replicaId++) {
            this.countsByReplicaId.put(
                replicaId,
                Math.max(this.countsByReplicaId.get(replicaId), otherCounter.countsByReplicaId.get(replicaId))
            );
        }
    }
}