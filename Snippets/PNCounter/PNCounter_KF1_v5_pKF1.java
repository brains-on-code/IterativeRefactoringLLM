package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class PNCounter {
    private final Map<Integer, Integer> positiveCountsByNode;
    private final Map<Integer, Integer> negativeCountsByNode;
    private final int localNodeId;
    private final int nodeCount;

    PNCounter(int localNodeId, int nodeCount) {
        this.localNodeId = localNodeId;
        this.nodeCount = nodeCount;
        this.positiveCountsByNode = new HashMap<>();
        this.negativeCountsByNode = new HashMap<>();

        for (int nodeId = 0; nodeId < nodeCount; nodeId++) {
            positiveCountsByNode.put(nodeId, 0);
            negativeCountsByNode.put(nodeId, 0);
        }
    }

    public void increment() {
        positiveCountsByNode.put(
            localNodeId,
            positiveCountsByNode.get(localNodeId) + 1
        );
    }

    public void decrement() {
        negativeCountsByNode.put(
            localNodeId,
            negativeCountsByNode.get(localNodeId) + 1
        );
    }

    public int getValue() {
        int totalPositive = positiveCountsByNode.values()
            .stream()
            .mapToInt(Integer::intValue)
            .sum();

        int totalNegative = negativeCountsByNode.values()
            .stream()
            .mapToInt(Integer::intValue)
            .sum();

        return totalPositive - totalNegative;
    }

    public boolean isLessThanOrEqualTo(PNCounter other) {
        if (this.nodeCount != other.nodeCount) {
            throw new IllegalArgumentException(
                "Cannot compare PN-Counters with different number of nodes"
            );
        }

        for (int nodeId = 0; nodeId < nodeCount; nodeId++) {
            boolean hasGreaterPositive =
                this.positiveCountsByNode.get(nodeId) >
                other.positiveCountsByNode.get(nodeId);

            boolean hasGreaterNegative =
                this.negativeCountsByNode.get(nodeId) >
                other.negativeCountsByNode.get(nodeId);

            if (hasGreaterPositive && hasGreaterNegative) {
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
            positiveCountsByNode.put(
                nodeId,
                Math.max(
                    this.positiveCountsByNode.get(nodeId),
                    other.positiveCountsByNode.get(nodeId)
                )
            );

            negativeCountsByNode.put(
                nodeId,
                Math.max(
                    this.negativeCountsByNode.get(nodeId),
                    other.negativeCountsByNode.get(nodeId)
                )
            );
        }
    }
}