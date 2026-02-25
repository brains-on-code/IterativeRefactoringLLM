package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class VectorClock {
    private final Map<Integer, Integer> clock;
    private final int processId;
    private final int processCount;

    VectorClock(int processId, int processCount) {
        this.processId = processId;
        this.processCount = processCount;
        this.clock = new HashMap<>();

        for (int i = 0; i < processCount; i++) {
            clock.put(i, 0);
        }
    }

    public void increment() {
        clock.put(processId, clock.get(processId) + 1);
    }

    public int getTotalEvents() {
        int totalEvents = 0;
        for (int eventCount : clock.values()) {
            totalEvents += eventCount;
        }
        return totalEvents;
    }

    public boolean isLessThanOrEqual(VectorClock other) {
        for (int i = 0; i < processCount; i++) {
            if (this.clock.get(i) > other.clock.get(i)) {
                return false;
            }
        }
        return true;
    }

    public void merge(VectorClock other) {
        for (int i = 0; i < processCount; i++) {
            this.clock.put(i, Math.max(this.clock.get(i), other.clock.get(i)));
        }
    }
}