package com.thealgorithms.datastructures.crdt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class GCounter {

    private final Map<Integer, Integer> counterMap;
    private final int nodeId;
    private final int nodeCount;

    GCounter(int nodeId, int nodeCount) {
        this.nodeId = nodeId;
        this.nodeCount = nodeCount;
        this.counterMap = new HashMap<>(nodeCount);
        initializeCounters();
    }

    private void initializeCounters() {
        for (int i = 0; i < nodeCount; i++) {
            counterMap.put(i, 0);
        }
    }

    public void increment() {
        counterMap.merge(nodeId, 1, Integer::sum);
    }

    public int value() {
        return counterMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    public boolean compare(GCounter other) {
        for (int i = 0; i < nodeCount; i++) {
            if (getCount(i) > other.getCount(i)) {
                return false;
            }
        }
        return true;
    }

    public void merge(GCounter other) {
        for (int i = 0; i < nodeCount; i++) {
            int mergedValue = Math.max(getCount(i), other.getCount(i));
            counterMap.put(i, mergedValue);
        }
    }

    private int getCount(int nodeIndex) {
        return counterMap.getOrDefault(nodeIndex, 0);
    }

    public Map<Integer, Integer> getCounterMap() {
        return Collections.unmodifiableMap(counterMap);
    }
}