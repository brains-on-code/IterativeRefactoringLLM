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
        initializeCounters();
    }

    private void initializeCounters() {
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
        return sumValues(positiveCounter) - sumValues(negativeCounter);
    }

    private int sumValues(Map<Integer, Integer> counter) {
        return counter.values().stream().mapToInt(Integer::intValue).sum();
    }

    public boolean compare(PNCounter other) {
        validateCompatible(other);

        for (int i = 0; i < nodeCount; i++) {
            int thisPositive = positiveCounter.get(i);
            int otherPositive = other.positiveCounter.get(i);
            int thisNegative = negativeCounter.get(i);
            int otherNegative = other.negativeCounter.get(i);

            if (isStrictlyGreater(thisPositive, otherPositive)
                    && isStrictlyGreater(thisNegative, otherNegative)) {
                return false;
            }
        }
        return true;
    }

    public void merge(PNCounter other) {
        validateCompatible(other);

        for (int i = 0; i < nodeCount; i++) {
            positiveCounter.put(i, max(positiveCounter.get(i), other.positiveCounter.get(i)));
            negativeCounter.put(i, max(negativeCounter.get(i), other.negativeCounter.get(i)));
        }
    }

    private void validateCompatible(PNCounter other) {
        if (this.nodeCount != other.nodeCount) {
            throw new IllegalArgumentException(
                    "Cannot operate on PN-Counters with different number of nodes");
        }
    }

    private boolean isStrictlyGreater(int a, int b) {
        return a > b;
    }

    private int max(int a, int b) {
        return Math.max(a, b);
    }
}