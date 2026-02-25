package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Remaining Time First (SRTF) scheduling implementation.
 *
 * Builds a Gantt chart (process execution order over time) based on:
 * - Arrival time of each process
 * - Burst time of each process
 */
public class Class1 {
    /** List of processes to be scheduled. */
    protected List<ProcessDetails> processes;

    /** Gantt chart: process IDs in the order they are executed over time. */
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
     * Builds the SRTF schedule and populates {@link #ganttChart}.
     */
    public void method1() {
        int totalTime = 0;
        int currentProcessIndex = 0;
        int processCount = processes.size();
        int[] remainingBurstTimes = new int[processCount];

        // Initialize remaining burst times and compute total burst time
        for (int i = 0; i < processCount; i++) {
            remainingBurstTimes[i] = processes.get(i).getBurstTime();
            totalTime += processes.get(i).getBurstTime();
        }

        // If the first process does not arrive at time 0, account for initial idle time
        int firstArrival = processes.get(0).getArrivalTime();
        if (firstArrival != 0) {
            totalTime += firstArrival;
        }

        // Fill initial idle slots (CPU idle before first process arrives)
        if (firstArrival != 0) {
            for (int t = 0; t < firstArrival; t++) {
                ganttChart.add(null);
            }
        }

        // Build the schedule from first arrival time up to totalTime
        for (int currentTime = firstArrival; currentTime < totalTime; currentTime++) {
            // Select process with shortest remaining time among arrived processes
            for (int i = 0; i < processCount; i++) {
                boolean hasArrived = processes.get(i).getArrivalTime() <= currentTime;
                boolean shorterRemainingTime =
                        remainingBurstTimes[i] < remainingBurstTimes[currentProcessIndex] && remainingBurstTimes[i] > 0;
                boolean currentFinished = remainingBurstTimes[currentProcessIndex] == 0;

                if (hasArrived && (shorterRemainingTime || currentFinished)) {
                    currentProcessIndex = i;
                }
            }

            // Execute selected process for one time unit
            ganttChart.add(processes.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }
}