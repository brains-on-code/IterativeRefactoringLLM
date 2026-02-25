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
        this.processes = new ArrayList<>(processes);
        this.executionTimeline = new ArrayList<>();
    }

    public void schedule() {
        int totalExecutionTime = 0;
        int currentProcessIndex = 0;
        int processCount = processes.size();
        int[] remainingBurstTimes = new int[processCount];

        for (int index = 0; index < processCount; index++) {
            int burstTime = processes.get(index).getBurstTime();
            remainingBurstTimes[index] = burstTime;
            totalExecutionTime += burstTime;
        }

        int earliestArrivalTime = processes.get(0).getArrivalTime();

        if (earliestArrivalTime != 0) {
            totalExecutionTime += earliestArrivalTime;
            for (int time = 0; time < earliestArrivalTime; time++) {
                executionTimeline.add(null);
            }
        }

        for (int currentTime = earliestArrivalTime; currentTime < totalExecutionTime; currentTime++) {
            for (int index = 0; index < processCount; index++) {
                boolean hasArrived = processes.get(index).getArrivalTime() <= currentTime;
                boolean hasShorterRemainingTime =
                        remainingBurstTimes[index] < remainingBurstTimes[currentProcessIndex]
                                && remainingBurstTimes[index] > 0;
                boolean currentProcessFinished = remainingBurstTimes[currentProcessIndex] == 0;

                if (hasArrived && (hasShorterRemainingTime || currentProcessFinished)) {
                    currentProcessIndex = index;
                }
            }
            executionTimeline.add(processes.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }
}