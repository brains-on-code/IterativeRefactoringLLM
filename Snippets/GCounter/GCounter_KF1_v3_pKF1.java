package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class VectorClock {
    private final Map<Integer, Integer> processToEventCount;
    private final int localProcessId;
    private final int numberOfProcesses;

    VectorClock(int localProcessId, int numberOfProcesses) {
        this.localProcessId = localProcessId;
        this.numberOfProcesses = numberOfProcesses;
        this.processToEventCount = new HashMap<>();

        for (int processId = 0; processId < numberOfProcesses; processId++) {
            processToEventCount.put(processId, 0);
        }
    }

    public void incrementLocalEventCount() {
        int currentCount = processToEventCount.get(localProcessId);
        processToEventCount.put(localProcessId, currentCount + 1);
    }

    public int getTotalEventCount() {
        int totalEventCount = 0;
        for (int eventCount : processToEventCount.values()) {
            totalEventCount += eventCount;
        }
        return totalEventCount;
    }

    public boolean isLessThanOrEqual(VectorClock otherVectorClock) {
        for (int processId = 0; processId < numberOfProcesses; processId++) {
            int thisCount = this.processToEventCount.get(processId);
            int otherCount = otherVectorClock.processToEventCount.get(processId);
            if (thisCount > otherCount) {
                return false;
            }
        }
        return true;
    }

    public void merge(VectorClock otherVectorClock) {
        for (int processId = 0; processId < numberOfProcesses; processId++) {
            int thisCount = this.processToEventCount.get(processId);
            int otherCount = otherVectorClock.processToEventCount.get(processId);
            int mergedEventCount = Math.max(thisCount, otherCount);
            this.processToEventCount.put(processId, mergedEventCount);
        }
    }
}