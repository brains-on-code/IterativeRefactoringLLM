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

    /** Processes to be scheduled. */
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

    /** Builds the SRTF schedule and populates {@link #ganttChart}. */
    public void buildSchedule() {
        int processCount = processes.size();
        int[] remainingBurstTimes = new int[processCount];

        int totalTime = initializeRemainingTimesAndTotalTime(remainingBurstTimes);

        int firstArrivalTime = processes.get(0).getArrivalTime();
        if (firstArrivalTime > 0) {
            totalTime += firstArrivalTime;
            addIdleTimeBeforeFirstArrival(firstArrivalTime);
        }

        int currentProcessIndex = 0;

        for (int currentTime = firstArrivalTime; currentTime < totalTime; currentTime++) {
            currentProcessIndex =
                    selectProcessWithShortestRemainingTime(currentTime, remainingBurstTimes, currentProcessIndex);

            ganttChart.add(processes.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }

    /**
     * Initializes remaining burst times and computes the total execution time.
     *
     * @param remainingBurstTimes array to be filled with each process's burst time
     * @return total time required to execute all processes (sum of burst times)
     */
    private int initializeRemainingTimesAndTotalTime(int[] remainingBurstTimes) {
        int totalTime = 0;
        for (int i = 0; i < processes.size(); i++) {
            int burstTime = processes.get(i).getBurstTime();
            remainingBurstTimes[i] = burstTime;
            totalTime += burstTime;
        }
        return totalTime;
    }

    /**
     * Adds idle slots to the Gantt chart for the time before the first process arrives.
     *
     * @param idleTime number of time units the CPU is idle
     */
    private void addIdleTimeBeforeFirstArrival(int idleTime) {
        for (int t = 0; t < idleTime; t++) {
            ganttChart.add(null);
        }
    }

    /**
     * Selects the index of the process with the shortest remaining time that has arrived.
     *
     * @param currentTime         current time unit in the schedule
     * @param remainingBurstTimes remaining burst times for each process
     * @param currentProcessIndex index of the process currently being executed
     * @return index of the process to execute at the current time
     */
    private int selectProcessWithShortestRemainingTime(
            int currentTime, int[] remainingBurstTimes, int currentProcessIndex) {

        for (int i = 0; i < processes.size(); i++) {
            boolean hasArrived = processes.get(i).getArrivalTime() <= currentTime;
            boolean hasShorterRemainingTime =
                    remainingBurstTimes[i] < remainingBurstTimes[currentProcessIndex]
                            && remainingBurstTimes[i] > 0;
            boolean currentProcessFinished = remainingBurstTimes[currentProcessIndex] == 0;

            if (hasArrived && (hasShorterRemainingTime || currentProcessFinished)) {
                currentProcessIndex = i;
            }
        }

        return currentProcessIndex;
    }
}