package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SRTFScheduling {

    protected final List<ProcessDetails> processes;
    protected final List<String> ready;

    public SRTFScheduling(List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.ready = new ArrayList<>();
    }

    public void evaluateScheduling() {
        int processCount = processes.size();
        if (processCount == 0) {
            return;
        }

        int[] remainingTime = new int[processCount];
        int totalBurstTime = 0;

        for (int i = 0; i < processCount; i++) {
            int burstTime = processes.get(i).getBurstTime();
            remainingTime[i] = burstTime;
            totalBurstTime += burstTime;
        }

        int firstArrivalTime = processes.get(0).getArrivalTime();
        int endTime = (firstArrivalTime == 0)
            ? totalBurstTime
            : firstArrivalTime + totalBurstTime;

        fillIdleTimeBeforeFirstArrival(firstArrivalTime);

        int currentProcessIndex = 0;
        for (int currentTime = firstArrivalTime; currentTime < endTime; currentTime++) {
            currentProcessIndex = findNextProcessIndex(currentTime, remainingTime, currentProcessIndex);
            ProcessDetails currentProcess = processes.get(currentProcessIndex);
            ready.add(currentProcess.getProcessId());
            remainingTime[currentProcessIndex]--;
        }
    }

    private void fillIdleTimeBeforeFirstArrival(int firstArrivalTime) {
        for (int time = 0; time < firstArrivalTime; time++) {
            ready.add(null);
        }
    }

    private int findNextProcessIndex(int currentTime, int[] remainingTime, int currentProcessIndex) {
        int processCount = processes.size();
        int shortestIndex = currentProcessIndex;

        for (int i = 0; i < processCount; i++) {
            ProcessDetails process = processes.get(i);
            boolean hasArrived = process.getArrivalTime() <= currentTime;
            boolean hasRemainingTime = remainingTime[i] > 0;
            boolean isCurrentShortestFinished = remainingTime[shortestIndex] == 0;
            boolean isShorter = remainingTime[i] < remainingTime[shortestIndex];

            if (hasArrived && hasRemainingTime && (isShorter || isCurrentShortestFinished)) {
                shortestIndex = i;
            }
        }

        return shortestIndex;
    }
}