package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class PNCounter {

    private final Map<Integer, Integer> positiveCounter;
    private final Map<Integer, Integer> negativeCounter;
    private final int nodeId;
    private final int nodeCount;

    PNCounter(int nodeId, int nodeCount) {
        this.nodeId = nodeId;
        this.nodeCount = nodeCount;
        this.positiveCounter = new HashMap<>();
        this.negativeCounter = new HashMap<>();

        for (int i = 0; i < nodeCount; i++) {
            positiveCounter.put(i, 0);
            negativeCounter.put(i, 0);
        }
    }

    public void increment() {
        positiveCounter.put(nodeId, positiveCounter.get(nodeId) + 1);
    }

    public void decrement() {
        negativeCounter.put(nodeId, negativeCounter.get(nodeId) + 1);
    }

    public int value() {
        int positiveSum = positiveCounter.values().stream().mapToInt(Integer::intValue).sum();
        int negativeSum = negativeCounter.values().stream().mapToInt(Integer::intValue).sum();
        return positiveSum - negativeSum;
    }

    public boolean compare(PNCounter other) {
        validateCompatible(other);

        for (int i = 0; i < nodeCount; i++) {
            boolean greaterPositive = this.positiveCounter.get(i) > other.positiveCounter.get(i);
            boolean greaterNegative = this.negativeCounter.get(i) > other.negativeCounter.get(i);
            if (greaterPositive && greaterNegative) {
                return false;
            }
        }
        return true;
    }

    public void merge(PNCounter other) {
        validateCompatible(other);

        for (int i = 0; i < nodeCount; i++) {
            positiveCounter.put(i, Math.max(positiveCounter.get(i), other.positiveCounter.get(i)));
            negativeCounter.put(i, Math.max(negativeCounter.get(i), other.negativeCounter.get(i)));
        }
    }

    private void validateCompatible(PNCounter other) {
        if (this.nodeCount != other.nodeCount) {
            throw new IllegalArgumentException("Cannot operate on PN-Counters with different number of nodes");
        }
    }
}