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

    public int getValue() {
        int totalPositiveCount = positiveCountsByNodeId.values()
            .stream()
            .mapToInt(Integer::intValue)
            .sum();

        int totalNegativeCount = negativeCountsByNodeId.values()
            .stream()
            .mapToInt(Integer::intValue)
            .sum();

        return totalPositiveCount - totalNegativeCount;
    }

    public boolean isLessThanOrEqualTo(PNCounter otherCounter) {
        if (this.totalNodeCount != otherCounter.totalNodeCount) {
            throw new IllegalArgumentException(
                "Cannot compare PN-Counters with different number of nodes"
            );
        }

        for (int nodeId = 0; nodeId < totalNodeCount; nodeId++) {
            boolean hasGreaterPositiveCount =
                this.positiveCountsByNodeId.get(nodeId) >
                otherCounter.positiveCountsByNodeId.get(nodeId);

            boolean hasGreaterNegativeCount =
                this.negativeCountsByNodeId.get(nodeId) >
                otherCounter.negativeCountsByNodeId.get(nodeId);

            if (hasGreaterPositiveCount && hasGreaterNegativeCount) {
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
            positiveCountsByNodeId.put(
                nodeId,
                Math.max(
                    this.positiveCountsByNodeId.get(nodeId),
                    otherCounter.positiveCountsByNodeId.get(nodeId)
                )
            );

            negativeCountsByNodeId.put(
                nodeId,
                Math.max(
                    this.negativeCountsByNodeId.get(nodeId),
                    otherCounter.negativeCountsByNodeId.get(nodeId)
                )
            );
        }
    }
}