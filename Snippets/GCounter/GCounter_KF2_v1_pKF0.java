package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class GCounter {

    private final Map<Integer, Integer> counterMap;
    private final int nodeId;
    private final int nodeCount;

    GCounter(int nodeId, int nodeCount) {
        this.nodeId = nodeId;
        this.nodeCount = nodeCount;
        this.counterMap = new HashMap<>();

        for (int i = 0; i < nodeCount; i++) {
            counterMap.put(i, 0);
        }
    }

    public void increment() {
        counterMap.put(nodeId, counterMap.get(nodeId) + 1);
    }

    public int value() {
        int sum = 0;
        for (int count : counterMap.values()) {
            sum += count;
        }
        return sum;
    }

    public boolean compare(GCounter other) {
        for (int i = 0; i < nodeCount; i++) {
            if (this.counterMap.get(i) > other.counterMap.get(i)) {
                return false;
            }
        }
        return true;
    }

    public void merge(GCounter other) {
        for (int i = 0; i < nodeCount; i++) {
            int mergedValue = Math.max(this.counterMap.get(i), other.counterMap.get(i));
            this.counterMap.put(i, mergedValue);
        }
    }
}