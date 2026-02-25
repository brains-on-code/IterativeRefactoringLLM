package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class VectorClock {
    private final Map<Integer, Integer> processEventCounts;
    private final int localProcessId;
    private final int totalProcesses;

    VectorClock(int localProcessId, int totalProcesses) {
        this.localProcessId = localProcessId;
        this.totalProcesses = totalProcesses;
        this.processEventCounts = new HashMap<>();

        for (int processId = 0; processId < totalProcesses; processId++) {
            processEventCounts.put(processId, 0);
        }
    }

    public void incrementLocalEventCount() {
        processEventCounts.put(localProcessId, processEventCounts.get(localProcessId) + 1);
    }

    public int getTotalEventCount() {
        int totalEventCount = 0;
        for (int eventCount : processEventCounts.values()) {
            totalEventCount += eventCount;
        }
        return totalEventCount;
    }

    public boolean isLessThanOrEqual(VectorClock otherClock) {
        for (int processId = 0; processId < totalProcesses; processId++) {
            if (this.processEventCounts.get(processId) > otherClock.processEventCounts.get(processId)) {
                return false;
            }
        }
        return true;
    }

    public void merge(VectorClock otherClock) {
        for (int processId = 0; processId < totalProcesses; processId++) {
            int mergedEventCount = Math.max(
                this.processEventCounts.get(processId),
                otherClock.processEventCounts.get(processId)
            );
            this.processEventCounts.put(processId, mergedEventCount);
        }
    }
}