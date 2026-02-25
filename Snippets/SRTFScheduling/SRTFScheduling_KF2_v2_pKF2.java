package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

public class SRTFScheduling {

    /** Processes to be scheduled. */
    protected final List<ProcessDetails> processes;

    /**
     * Execution timeline.
     * Index = time unit, value = process ID running at that time, or {@code null} if CPU is idle.
     */
    protected final List<String> ready;

    public SRTFScheduling(List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.ready = new ArrayList<>();
    }

    /**
     * Computes the SRTF (Shortest Remaining Time First) schedule and fills {@link #ready}.
     */
    public void evaluateScheduling() {
        int processCount = processes.size();
        if (processCount == 0) {
            return;
        }

        int[] remainingTime = new int[processCount];
        int totalBurstTime = 0;

        // Initialize remaining times and total burst time
        for (int i = 0; i < processCount; i++) {
            int burstTime = processes.get(i).getBurstTime();
            remainingTime[i] = burstTime;
            totalBurstTime += burstTime;
        }

        int firstArrivalTime = processes.get(0).getArrivalTime();
        int totalTime = totalBurstTime + Math.max(firstArrivalTime, 0);

        // CPU idle before the first process arrives
        for (int t = 0; t < firstArrivalTime; t++) {
            ready.add(null);
        }

        int currentProcessIndex = 0;

        // Simulate CPU scheduling at each time unit
        for (int currentTime = firstArrivalTime; currentTime < totalTime; currentTime++) {
            currentProcessIndex = selectProcessWithShortestRemainingTime(
                    currentTime, remainingTime, currentProcessIndex);

            ready.add(processes.get(currentProcessIndex).getProcessId());
            remainingTime[currentProcessIndex]--;
        }
    }

    /**
     * Selects the index of the process with the shortest remaining time that has arrived
     * and still has remaining burst time.
     */
    private int selectProcessWithShortestRemainingTime(
            int currentTime, int[] remainingTime, int currentProcessIndex) {

        int processCount = processes.size();

        for (int i = 0; i < processCount; i++) {
            boolean hasArrived = processes.get(i).getArrivalTime() <= currentTime;
            boolean hasRemainingTime = remainingTime[i] > 0;
            boolean isCurrentFinished = remainingTime[currentProcessIndex] == 0;
            boolean isShorter = remainingTime[i] < remainingTime[currentProcessIndex];

            if (hasArrived && hasRemainingTime && (isShorter || isCurrentFinished)) {
                currentProcessIndex = i;
            }
        }

        return currentProcessIndex;
    }
}