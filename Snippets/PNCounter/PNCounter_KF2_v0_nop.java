package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;



class PNCounter {
    private final Map<Integer, Integer> pCounter;
    private final Map<Integer, Integer> nCounter;
    private final int myId;
    private final int n;


    PNCounter(int myId, int n) {
        this.myId = myId;
        this.n = n;
        this.pCounter = new HashMap<>();
        this.nCounter = new HashMap<>();

        for (int i = 0; i < n; i++) {
            pCounter.put(i, 0);
            nCounter.put(i, 0);
        }
    }


    public void increment() {
        pCounter.put(myId, pCounter.get(myId) + 1);
    }


    public void decrement() {
        nCounter.put(myId, nCounter.get(myId) + 1);
    }


    public int value() {
        int sumP = pCounter.values().stream().mapToInt(Integer::intValue).sum();
        int sumN = nCounter.values().stream().mapToInt(Integer::intValue).sum();
        return sumP - sumN;
    }


    public boolean compare(PNCounter other) {
        if (this.n != other.n) {
            throw new IllegalArgumentException("Cannot compare PN-Counters with different number of nodes");
        }
        for (int i = 0; i < n; i++) {
            if (this.pCounter.get(i) > other.pCounter.get(i) && this.nCounter.get(i) > other.nCounter.get(i)) {
                return false;
            }
        }
        return true;
    }


    public void merge(PNCounter other) {
        if (this.n != other.n) {
            throw new IllegalArgumentException("Cannot merge PN-Counters with different number of nodes");
        }
        for (int i = 0; i < n; i++) {
            this.pCounter.put(i, Math.max(this.pCounter.get(i), other.pCounter.get(i)));
            this.nCounter.put(i, Math.max(this.nCounter.get(i), other.nCounter.get(i)));
        }
    }
}
