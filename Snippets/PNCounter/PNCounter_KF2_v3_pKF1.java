package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class PNCounter {
    private final Map<Integer, Integer> incrementsByNodeId;
    private final Map<Integer, Integer> decrementsByNodeId;
    private final int localNodeId;
    private final int nodeCount;

    PNCounter(int localNodeId, int nodeCount) {
        this.localNodeId = localNodeId;
        this.nodeCount = nodeCount;
        this.incrementsByNodeId = new HashMap<>();
        this.decrementsByNodeId = new HashMap<>();

        for (int nodeId = 0; nodeId < nodeCount; nodeId++) {
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

    public boolean isLessThanOrEqualTo(PNCounter other) {
        if (this.nodeCount != other.nodeCount) {
            throw new IllegalArgumentException(
                "Cannot compare PN-Counters with different number of nodes"
            );
        }

        for (int nodeId = 0; nodeId < nodeCount; nodeId++) {
            boolean localIncrementsGreater =
                this.incrementsByNodeId.get(nodeId) >
                other.incrementsByNodeId.get(nodeId);
            boolean localDecrementsGreater =
                this.decrementsByNodeId.get(nodeId) >
                other.decrementsByNodeId.get(nodeId);

            if (localIncrementsGreater && localDecrementsGreater) {
                return false;
            }
        }
        return true;
    }

    public void merge(PNCounter other) {
        if (this.nodeCount != other.nodeCount) {
            throw new IllegalArgumentException(
                "Cannot merge PN-Counters with different number of nodes"
            );
        }

        for (int nodeId = 0; nodeId < nodeCount; nodeId++) {
            this.incrementsByNodeId.put(
                nodeId,
                Math.max(
                    this.incrementsByNodeId.get(nodeId),
                    other.incrementsByNodeId.get(nodeId)
                )
            );
            this.decrementsByNodeId.put(
                nodeId,
                Math.max(
                    this.decrementsByNodeId.get(nodeId),
                    other.decrementsByNodeId.get(nodeId)
                )
            );
        }
    }
}