package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;



class GCounter {
    private final Map<Integer, Integer> counterMap;
    private final int myId;
    private final int n;


    GCounter(int myId, int n) {
        this.myId = myId;
        this.n = n;
        this.counterMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            counterMap.put(i, 0);
        }
    }


    public void increment() {
        counterMap.put(myId, counterMap.get(myId) + 1);
    }


    public int value() {
        int sum = 0;
        for (int v : counterMap.values()) {
            sum += v;
        }
        return sum;
    }


    public boolean compare(GCounter other) {
        for (int i = 0; i < n; i++) {
            if (this.counterMap.get(i) > other.counterMap.get(i)) {
                return false;
            }
        }
        return true;
    }


    public void merge(GCounter other) {
        for (int i = 0; i < n; i++) {
            this.counterMap.put(i, Math.max(this.counterMap.get(i), other.counterMap.get(i)));
        }
    }
}
