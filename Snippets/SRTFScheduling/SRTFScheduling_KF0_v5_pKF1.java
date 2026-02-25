package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Shortest Remaining Time First Scheduling Algorithm.
 * In the SRTF scheduling algorithm, the process with the smallest amount of time remaining until completion is selected to execute.
 * Example:
 * Consider the processes p1, p2 and the following table with info about their arrival and burst time:
 * Process | Burst Time | Arrival Time
 * P1      | 6 ms        | 0 ms
 * P2      | 2 ms        | 1 ms
 * In this example, P1 will be executed at time = 0 until time = 1 when P2 arrives. At time = 2, P2 will be executed until time = 4. At time 4, P2 is done, and P1 is executed again to be done.
 * That's a simple example of how the algorithm works.
 * More information you can find here -> https://en.wikipedia.org/wiki/Shortest_remaining_time
 */
public class SRTFScheduling {
    protected List<ProcessDetails> processes;
    protected List<String> executionTimeline;

    /**
     * Constructor
     * @param processes ArrayList of ProcessDetails given as input
     */
    public SRTFScheduling(ArrayList<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.executionTimeline = new ArrayList<>();
    }

    public void evaluateScheduling() {
        int totalExecutionTime = 0;
        int currentProcessIndex = 0;
        int processCount = processes.size();
        int[] remainingBurstTimes = new int[processCount];

        // Calculate remaining time of every process and total units of time
        for (int index = 0; index < processCount; index++) {
            int burstTime = processes.get(index).getBurstTime();
            remainingBurstTimes[index] = burstTime;
            totalExecutionTime += burstTime;
        }

        int firstArrivalTime = processes.get(0).getArrivalTime();

        // If the first process doesn't arrive at 0, we have more units of time
        if (firstArrivalTime != 0) {
            totalExecutionTime += firstArrivalTime;
        }

        // If the first process doesn't arrive at 0, fill the timeline with idle slots until it arrives
        if (firstArrivalTime != 0) {
            for (int time = 0; time < firstArrivalTime; time++) {
                executionTimeline.add(null);
            }
        }

        // Determine which process is executed at every unit of time
        for (int currentTime = firstArrivalTime; currentTime < totalExecutionTime; currentTime++) {
            // Check if there's a process with remaining time less than the current running process.
            // If found, that process executes.
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