package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Remaining Time First (preemptive SJF) scheduler.
 *
 * Builds a Gantt chart (process execution order over time) in {@code ganttChart}.
 */
public class Class1 {

    /** List of processes to schedule. */
    protected List<ProcessDetails> processes;

    /** Gantt chart: process IDs in the order they are executed at each time unit. */
    protected List<String> ganttChart;

    /**
     * Creates a scheduler with the given list of processes.
     *
     * @param processes list of processes to schedule
     */
    public Class1(ArrayList<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.ganttChart = new ArrayList<>();
    }

    /**
     * Builds the Gantt chart using Shortest Remaining Time First scheduling.
     */
    public void method1() {
        int totalTime = 0;
        int currentProcessIndex = 0;
        int processCount = processes.size();
        int[] remainingBurstTimes = new int[processCount];

        // Initialize remaining burst times and compute total burst time
        for (int i = 0; i < processCount; i++) {
            int burstTime = processes.get(i).getBurstTime();
            remainingBurstTimes[i] = burstTime;
            totalTime += burstTime;
        }

        // Account for initial idle time if the first process doesn't arrive at time 0
        int firstArrivalTime = processes.get(0).getArrivalTime();
        if (firstArrivalTime != 0) {
            totalTime += firstArrivalTime;
            for (int t = 0; t < firstArrivalTime; t++) {
                ganttChart.add(null); // CPU is idle
            }
        }

        // Simulate time from first arrival until all processes finish
        for (int currentTime = firstArrivalTime; currentTime < totalTime; currentTime++) {
            // Select process with the shortest remaining time that has arrived
            for (int i = 0; i < processCount; i++) {
                boolean hasArrived = processes.get(i).getArrivalTime() <= currentTime;
                boolean shorterRemainingTime =
                        remainingBurstTimes[i] < remainingBurstTimes[currentProcessIndex]
                                && remainingBurstTimes[i] > 0;
                boolean currentFinished = remainingBurstTimes[currentProcessIndex] == 0;

                if (hasArrived && (shorterRemainingTime || currentFinished)) {
                    currentProcessIndex = i;
                }
            }

            // Execute the selected process for one time unit
            ganttChart.add(processes.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }
}