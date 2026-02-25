package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Remaining Time First (SRTF) scheduling implementation.
 */
public class ShortestRemainingTimeFirstScheduler {
    protected List<ProcessDetails> processList;
    protected List<String> executionTimeline;

    /**
     * Creates a scheduler with the given list of processes.
     *
     * @param processes list of processes to schedule
     */
    public ShortestRemainingTimeFirstScheduler(ArrayList<ProcessDetails> processes) {
        this.processList = new ArrayList<>();
        this.executionTimeline = new ArrayList<>();
        this.processList = processes;
    }

    public void schedule() {
        int totalExecutionTime = 0;
        int currentProcessIndex = 0;
        int processCount = processList.size();
        int[] remainingBurstTimes = new int[processCount];

        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            int burstTime = processList.get(processIndex).getBurstTime();
            remainingBurstTimes[processIndex] = burstTime;
            totalExecutionTime += burstTime;
        }

        int earliestArrivalTime = processList.get(0).getArrivalTime();

        if (earliestArrivalTime != 0) {
            totalExecutionTime += earliestArrivalTime;
            for (int time = 0; time < earliestArrivalTime; time++) {
                executionTimeline.add(null);
            }
        }

        for (int currentTime = earliestArrivalTime; currentTime < totalExecutionTime; currentTime++) {
            for (int processIndex = 0; processIndex < processCount; processIndex++) {
                boolean hasArrived = processList.get(processIndex).getArrivalTime() <= currentTime;
                boolean hasShorterRemainingTime =
                        remainingBurstTimes[processIndex] < remainingBurstTimes[currentProcessIndex]
                                && remainingBurstTimes[processIndex] > 0;
                boolean currentProcessFinished = remainingBurstTimes[currentProcessIndex] == 0;

                if (hasArrived && (hasShorterRemainingTime || currentProcessFinished)) {
                    currentProcessIndex = processIndex;
                }
            }
            executionTimeline.add(processList.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }
}