package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class PNCounter {
    private final Map<Integer, Integer> incrementsByReplicaId;
    private final Map<Integer, Integer> decrementsByReplicaId;
    private final int localReplicaId;
    private final int replicaCount;

    PNCounter(int localReplicaId, int replicaCount) {
        this.localReplicaId = localReplicaId;
        this.replicaCount = replicaCount;
        this.incrementsByReplicaId = new HashMap<>();
        this.decrementsByReplicaId = new HashMap<>();

        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            incrementsByReplicaId.put(replicaId, 0);
            decrementsByReplicaId.put(replicaId, 0);
        }
    }

    public void increment() {
        incrementsByReplicaId.put(
            localReplicaId,
            incrementsByReplicaId.get(localReplicaId) + 1
        );
    }

    public void decrement() {
        decrementsByReplicaId.put(
            localReplicaId,
            decrementsByReplicaId.get(localReplicaId) + 1
        );
    }

    public int value() {
        int totalIncrements = incrementsByReplicaId.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
        int totalDecrements = decrementsByReplicaId.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
        return totalIncrements - totalDecrements;
    }

    public boolean isLessThanOrEqualTo(PNCounter otherCounter) {
        if (this.replicaCount != otherCounter.replicaCount) {
            throw new IllegalArgumentException(
                "Cannot compare PN-Counters with different number of replicas"
            );
        }

        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            boolean localIncrementsGreater =
                this.incrementsByReplicaId.get(replicaId) >
                otherCounter.incrementsByReplicaId.get(replicaId);
            boolean localDecrementsGreater =
                this.decrementsByReplicaId.get(replicaId) >
                otherCounter.decrementsByReplicaId.get(replicaId);

            if (localIncrementsGreater && localDecrementsGreater) {
                return false;
            }
        }
        return true;
    }

    public void merge(PNCounter otherCounter) {
        if (this.replicaCount != otherCounter.replicaCount) {
            throw new IllegalArgumentException(
                "Cannot merge PN-Counters with different number of replicas"
            );
        }

        for (int replicaId = 0; replicaId < replicaCount; replicaId++) {
            this.incrementsByReplicaId.put(
                replicaId,
                Math.max(
                    this.incrementsByReplicaId.get(replicaId),
                    otherCounter.incrementsByReplicaId.get(replicaId)
                )
            );
            this.decrementsByReplicaId.put(
                replicaId,
                Math.max(
                    this.decrementsByReplicaId.get(replicaId),
                    otherCounter.decrementsByReplicaId.get(replicaId)
                )
            );
        }
    }
}