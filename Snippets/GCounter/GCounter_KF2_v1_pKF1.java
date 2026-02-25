package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class GCounter {
    private final Map<Integer, Integer> replicaCounters;
    private final int replicaId;
    private final int replicaCount;

    GCounter(int replicaId, int replicaCount) {
        this.replicaId = replicaId;
        this.replicaCount = replicaCount;
        this.replicaCounters = new HashMap<>();

        for (int id = 0; id < replicaCount; id++) {
            replicaCounters.put(id, 0);
        }
    }

    public void increment() {
        replicaCounters.put(replicaId, replicaCounters.get(replicaId) + 1);
    }

    public int value() {
        int total = 0;
        for (int counterValue : replicaCounters.values()) {
            total += counterValue;
        }
        return total;
    }

    public boolean isLessThanOrEqualTo(GCounter other) {
        for (int id = 0; id < replicaCount; id++) {
            if (this.replicaCounters.get(id) > other.replicaCounters.get(id)) {
                return false;
            }
        }
        return true;
    }

    public void merge(GCounter other) {
        for (int id = 0; id < replicaCount; id++) {
            this.replicaCounters.put(
                id,
                Math.max(this.replicaCounters.get(id), other.replicaCounters.get(id))
            );
        }
    }
}