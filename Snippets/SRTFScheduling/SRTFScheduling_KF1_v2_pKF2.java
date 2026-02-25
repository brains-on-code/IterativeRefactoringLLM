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
public class ShortestRemainingTimeFirstScheduler {

    /** List of processes to be scheduled. */
    protected final List<ProcessDetails> processes;

    /** Gantt chart: process IDs in the order they are executed over time. */
    protected final List<String> ganttChart;

    /**
     * Creates a scheduler with the given list of processes.
     *
     * @param processes list of processes to schedule
     */
    public ShortestRemainingTimeFirstScheduler(List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.ganttChart = new ArrayList<>();
    }

    /**
     * Builds the SRTF schedule and populates {@link #ganttChart}.
     */
    public void buildSchedule() {
        int processCount = processes.size();
        int[] remainingBurstTimes = new int[processCount];

        int totalTime = 0;
        for (int i = 0; i < processCount; i++) {
            int burstTime = processes.get(i).getBurstTime();
            remainingBurstTimes[i] = burstTime;
            totalTime += burstTime;
        }

        int firstArrivalTime = processes.get(0).getArrivalTime();
        if (firstArrivalTime > 0) {
            totalTime += firstArrivalTime;
            for (int t = 0; t < firstArrivalTime; t++) {
                ganttChart.add(null); // CPU is idle before the first process arrives
            }
        }

        int currentProcessIndex = 0;

        for (int currentTime = firstArrivalTime; currentTime < totalTime; currentTime++) {
            for (int i = 0; i < processCount; i++) {
                boolean hasArrived = processes.get(i).getArrivalTime() <= currentTime;
                boolean hasShorterRemainingTime =
                        remainingBurstTimes[i] < remainingBurstTimes[currentProcessIndex]
                                && remainingBurstTimes[i] > 0;
                boolean currentProcessFinished = remainingBurstTimes[currentProcessIndex] == 0;

                if (hasArrived && (hasShorterRemainingTime || currentProcessFinished)) {
                    currentProcessIndex = i;
                }
            }

            ganttChart.add(processes.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }
}