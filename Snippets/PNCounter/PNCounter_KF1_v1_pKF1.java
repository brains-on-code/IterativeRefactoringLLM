package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class PNCounter {
    private final Map<Integer, Integer> positiveCounts;
    private final Map<Integer, Integer> negativeCounts;
    private final int nodeId;
    private final int numberOfNodes;

    PNCounter(int nodeId, int numberOfNodes) {
        this.nodeId = nodeId;
        this.numberOfNodes = numberOfNodes;
        this.positiveCounts = new HashMap<>();
        this.negativeCounts = new HashMap<>();

        for (int i = 0; i < numberOfNodes; i++) {
            positiveCounts.put(i, 0);
            negativeCounts.put(i, 0);
        }
    }

    public void increment() {
        positiveCounts.put(nodeId, positiveCounts.get(nodeId) + 1);
    }

    public void decrement() {
        negativeCounts.put(nodeId, negativeCounts.get(nodeId) + 1);
    }

    public int getValue() {
        int totalPositive = positiveCounts.values().stream().mapToInt(Integer::intValue).sum();
        int totalNegative = negativeCounts.values().stream().mapToInt(Integer::intValue).sum();
        return totalPositive - totalNegative;
    }

    public boolean isLessThanOrEqualTo(PNCounter other) {
        if (this.numberOfNodes != other.numberOfNodes) {
            throw new IllegalArgumentException("Cannot compare PN-Counters with different number of nodes");
        }
        for (int i = 0; i < numberOfNodes; i++) {
            if (this.positiveCounts.get(i) > other.positiveCounts.get(i)
                    && this.negativeCounts.get(i) > other.negativeCounts.get(i)) {
                return false;
            }
        }
        return true;
    }

    public void merge(PNCounter other) {
        if (this.numberOfNodes != other.numberOfNodes) {
            throw new IllegalArgumentException("Cannot merge PN-Counters with different number of nodes");
        }
        for (int i = 0; i < numberOfNodes; i++) {
            this.positiveCounts.put(i, Math.max(this.positiveCounts.get(i), other.positiveCounts.get(i)));
            this.negativeCounts.put(i, Math.max(this.negativeCounts.get(i), other.negativeCounts.get(i)));
        }
    }
}