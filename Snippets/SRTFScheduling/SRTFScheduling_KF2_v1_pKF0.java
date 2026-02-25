package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SRTFScheduling {

    protected List<ProcessDetails> processes;
    protected List<String> ready;

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
        int currentTime = firstArrivalTime == 0 ? 0 : firstArrivalTime + totalBurstTime;

        if (firstArrivalTime > 0) {
            for (int i = 0; i < firstArrivalTime; i++) {
                ready.add(null);
            }
        }

        int currentProcessIndex = 0;
        for (int time = firstArrivalTime; time < currentTime; time++) {
            currentProcessIndex = findNextProcessIndex(time, remainingTime, currentProcessIndex);
            ready.add(processes.get(currentProcessIndex).getProcessId());
            remainingTime[currentProcessIndex]--;
        }
    }

    private int findNextProcessIndex(int currentTime, int[] remainingTime, int currentProcessIndex) {
        int processCount = processes.size();
        int shortestIndex = currentProcessIndex;

        for (int i = 0; i < processCount; i++) {
            boolean hasArrived = processes.get(i).getArrivalTime() <= currentTime;
            boolean hasRemainingTime = remainingTime[i] > 0;
            boolean isShorter =
                    remainingTime[i] < remainingTime[shortestIndex] || remainingTime[shortestIndex] == 0;

            if (hasArrived && hasRemainingTime && isShorter) {
                shortestIndex = i;
            }
        }

        return shortestIndex;
    }
}