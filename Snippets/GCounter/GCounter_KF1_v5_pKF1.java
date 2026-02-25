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
        int currentLocalEventCount = processToEventCount.get(localProcessId);
        processToEventCount.put(localProcessId, currentLocalEventCount + 1);
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
            int thisEventCount = this.processToEventCount.get(processId);
            int otherEventCount = otherVectorClock.processToEventCount.get(processId);
            if (thisEventCount > otherEventCount) {
                return false;
            }
        }
        return true;
    }

    public void merge(VectorClock otherVectorClock) {
        for (int processId = 0; processId < numberOfProcesses; processId++) {
            int thisEventCount = this.processToEventCount.get(processId);
            int otherEventCount = otherVectorClock.processToEventCount.get(processId);
            int mergedEventCount = Math.max(thisEventCount, otherEventCount);
            this.processToEventCount.put(processId, mergedEventCount);
        }
    }
}