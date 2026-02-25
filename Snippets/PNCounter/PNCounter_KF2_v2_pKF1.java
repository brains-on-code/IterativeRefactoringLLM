package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class PNCounter {
    private final Map<Integer, Integer> positiveCountsByNodeId;
    private final Map<Integer, Integer> negativeCountsByNodeId;
    private final int localNodeId;
    private final int totalNodeCount;

    PNCounter(int localNodeId, int totalNodeCount) {
        this.localNodeId = localNodeId;
        this.totalNodeCount = totalNodeCount;
        this.positiveCountsByNodeId = new HashMap<>();
        this.negativeCountsByNodeId = new HashMap<>();

        for (int nodeId = 0; nodeId < totalNodeCount; nodeId++) {
            positiveCountsByNodeId.put(nodeId, 0);
            negativeCountsByNodeId.put(nodeId, 0);
        }
    }

    public void increment() {
        positiveCountsByNodeId.put(
            localNodeId,
            positiveCountsByNodeId.get(localNodeId) + 1
        );
    }

    public void decrement() {
        negativeCountsByNodeId.put(
            localNodeId,
            negativeCountsByNodeId.get(localNodeId) + 1
        );
    }

    public int value() {
        int totalPositive = positiveCountsByNodeId.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
        int totalNegative = negativeCountsByNodeId.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
        return totalPositive - totalNegative;
    }

    public boolean isLessThanOrEqualTo(PNCounter otherCounter) {
        if (this.totalNodeCount != otherCounter.totalNodeCount) {
            throw new IllegalArgumentException(
                "Cannot compare PN-Counters with different number of nodes"
            );
        }

        for (int nodeId = 0; nodeId < totalNodeCount; nodeId++) {
            boolean localPositiveGreater =
                this.positiveCountsByNodeId.get(nodeId) >
                otherCounter.positiveCountsByNodeId.get(nodeId);
            boolean localNegativeGreater =
                this.negativeCountsByNodeId.get(nodeId) >
                otherCounter.negativeCountsByNodeId.get(nodeId);

            if (localPositiveGreater && localNegativeGreater) {
                return false;
            }
        }
        return true;
    }

    public void merge(PNCounter otherCounter) {
        if (this.totalNodeCount != otherCounter.totalNodeCount) {
            throw new IllegalArgumentException(
                "Cannot merge PN-Counters with different number of nodes"
            );
        }

        for (int nodeId = 0; nodeId < totalNodeCount; nodeId++) {
            this.positiveCountsByNodeId.put(
                nodeId,
                Math.max(
                    this.positiveCountsByNodeId.get(nodeId),
                    otherCounter.positiveCountsByNodeId.get(nodeId)
                )
            );
            this.negativeCountsByNodeId.put(
                nodeId,
                Math.max(
                    this.negativeCountsByNodeId.get(nodeId),
                    otherCounter.negativeCountsByNodeId.get(nodeId)
                )
            );
        }
    }
}