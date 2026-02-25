package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class VectorClock {
    private final Map<Integer, Integer> processEventCounts;
    private final int localProcessId;
    private final int processCount;

    VectorClock(int localProcessId, int processCount) {
        this.localProcessId = localProcessId;
        this.processCount = processCount;
        this.processEventCounts = new HashMap<>();

        for (int processId = 0; processId < processCount; processId++) {
            processEventCounts.put(processId, 0);
        }
    }

    public void incrementLocalEventCount() {
        int currentCount = processEventCounts.get(localProcessId);
        processEventCounts.put(localProcessId, currentCount + 1);
    }

    public int getTotalEventCount() {
        int totalEventCount = 0;
        for (int eventCount : processEventCounts.values()) {
            totalEventCount += eventCount;
        }
        return totalEventCount;
    }

    public boolean isLessThanOrEqual(VectorClock otherClock) {
        for (int processId = 0; processId < processCount; processId++) {
            int thisCount = this.processEventCounts.get(processId);
            int otherCount = otherClock.processEventCounts.get(processId);
            if (thisCount > otherCount) {
                return false;
            }
        }
        return true;
    }

    public void merge(VectorClock otherClock) {
        for (int processId = 0; processId < processCount; processId++) {
            int thisCount = this.processEventCounts.get(processId);
            int otherCount = otherClock.processEventCounts.get(processId);
            int mergedEventCount = Math.max(thisCount, otherCount);
            this.processEventCounts.put(processId, mergedEventCount);
        }
    }
}