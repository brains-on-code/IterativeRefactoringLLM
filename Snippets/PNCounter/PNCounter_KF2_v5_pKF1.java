package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class PNCounter {
    private final Map<Integer, Integer> incrementsByNodeId;
    private final Map<Integer, Integer> decrementsByNodeId;
    private final int localNodeId;
    private final int totalNodeCount;

    PNCounter(int localNodeId, int totalNodeCount) {
        this.localNodeId = localNodeId;
        this.totalNodeCount = totalNodeCount;
        this.incrementsByNodeId = new HashMap<>();
        this.decrementsByNodeId = new HashMap<>();

        for (int nodeId = 0; nodeId < totalNodeCount; nodeId++) {
            incrementsByNodeId.put(nodeId, 0);
            decrementsByNodeId.put(nodeId, 0);
        }
    }

    public void increment() {
        incrementsByNodeId.put(
            localNodeId,
            incrementsByNodeId.get(localNodeId) + 1
        );
    }

    public void decrement() {
        decrementsByNodeId.put(
            localNodeId,
            decrementsByNodeId.get(localNodeId) + 1
        );
    }

    public int value() {
        int totalIncrements = incrementsByNodeId.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
        int totalDecrements = decrementsByNodeId.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
        return totalIncrements - totalDecrements;
    }

    public boolean isLessThanOrEqualTo(PNCounter otherCounter) {
        if (this.totalNodeCount != otherCounter.totalNodeCount) {
            throw new IllegalArgumentException(
                "Cannot compare PN-Counters with different number of replicas"
            );
        }

        for (int nodeId = 0; nodeId < totalNodeCount; nodeId++) {
            boolean localIncrementsGreater =
                this.incrementsByNodeId.get(nodeId) >
                otherCounter.incrementsByNodeId.get(nodeId);
            boolean localDecrementsGreater =
                this.decrementsByNodeId.get(nodeId) >
                otherCounter.decrementsByNodeId.get(nodeId);

            if (localIncrementsGreater && localDecrementsGreater) {
                return false;
            }
        }
        return true;
    }

    public void merge(PNCounter otherCounter) {
        if (this.totalNodeCount != otherCounter.totalNodeCount) {
            throw new IllegalArgumentException(
                "Cannot merge PN-Counters with different number of replicas"
            );
        }

        for (int nodeId = 0; nodeId < totalNodeCount; nodeId++) {
            this.incrementsByNodeId.put(
                nodeId,
                Math.max(
                    this.incrementsByNodeId.get(nodeId),
                    otherCounter.incrementsByNodeId.get(nodeId)
                )
            );
            this.decrementsByNodeId.put(
                nodeId,
                Math.max(
                    this.decrementsByNodeId.get(nodeId),
                    otherCounter.decrementsByNodeId.get(nodeId)
                )
            );
        }
    }
}