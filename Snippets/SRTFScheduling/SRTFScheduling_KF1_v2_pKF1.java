package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Remaining Time First (SRTF) scheduling implementation.
 */
public class ShortestRemainingTimeFirstScheduler {
    protected List<ProcessDetails> processes;
    protected List<String> executionTimeline;

    /**
     * Creates a scheduler with the given list of processes.
     *
     * @param processes list of processes to schedule
     */
    public ShortestRemainingTimeFirstScheduler(ArrayList<ProcessDetails> processes) {
        this.processes = new ArrayList<>();
        this.executionTimeline = new ArrayList<>();
        this.processes = processes;
    }

    public void schedule() {
        int totalExecutionTime = 0;
        int currentProcessIndex = 0;
        int processCount = processes.size();
        int[] remainingBurstTimes = new int[processCount];

        for (int i = 0; i < processCount; i++) {
            int burstTime = processes.get(i).getBurstTime();
            remainingBurstTimes[i] = burstTime;
            totalExecutionTime += burstTime;
        }

        int firstArrivalTime = processes.get(0).getArrivalTime();

        if (firstArrivalTime != 0) {
            totalExecutionTime += firstArrivalTime;
            for (int i = 0; i < firstArrivalTime; i++) {
                executionTimeline.add(null);
            }
        }

        for (int currentTime = firstArrivalTime; currentTime < totalExecutionTime; currentTime++) {
            for (int i = 0; i < processCount; i++) {
                boolean hasArrived = processes.get(i).getArrivalTime() <= currentTime;
                boolean hasShorterRemainingTime =
                        remainingBurstTimes[i] < remainingBurstTimes[currentProcessIndex] && remainingBurstTimes[i] > 0;
                boolean currentProcessFinished = remainingBurstTimes[currentProcessIndex] == 0;

                if (hasArrived && (hasShorterRemainingTime || currentProcessFinished)) {
                    currentProcessIndex = i;
                }
            }
            executionTimeline.add(processes.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }
}