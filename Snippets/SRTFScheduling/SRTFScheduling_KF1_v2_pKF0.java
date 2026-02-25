package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Remaining Time First (preemptive SJF) scheduler.
 *
 * Builds a Gantt chart (process execution order over time) in {@code ganttChart}.
 */
public class ShortestRemainingTimeFirstScheduler {

    /** List of processes to schedule. */
    protected final List<ProcessDetails> processes;

    /** Gantt chart: process IDs in the order they are executed at each time unit. */
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
     * Builds the Gantt chart using Shortest Remaining Time First scheduling.
     */
    public void buildGanttChart() {
        int processCount = processes.size();
        if (processCount == 0) {
            return;
        }

        int[] remainingBurstTimes = new int[processCount];
        int totalTime = initializeRemainingTimesAndTotalTime(remainingBurstTimes);

        int firstArrivalTime = processes.get(0).getArrivalTime();
        addInitialIdleTimeIfNeeded(firstArrivalTime);
        totalTime += firstArrivalTime;

        simulateScheduling(firstArrivalTime, totalTime, remainingBurstTimes);
    }

    private int initializeRemainingTimesAndTotalTime(int[] remainingBurstTimes) {
        int totalTime = 0;
        for (int i = 0; i < processes.size(); i++) {
            int burstTime = processes.get(i).getBurstTime();
            remainingBurstTimes[i] = burstTime;
            totalTime += burstTime;
        }
        return totalTime;
    }

    private void addInitialIdleTimeIfNeeded(int firstArrivalTime) {
        for (int t = 0; t < firstArrivalTime; t++) {
            ganttChart.add(null); // CPU is idle
        }
    }

    private void simulateScheduling(int startTime, int totalTime, int[] remainingBurstTimes) {
        int currentProcessIndex = 0;

        for (int currentTime = startTime; currentTime < totalTime; currentTime++) {
            currentProcessIndex = selectNextProcess(currentTime, currentProcessIndex, remainingBurstTimes);
            ganttChart.add(processes.get(currentProcessIndex).getProcessId());
            remainingBurstTimes[currentProcessIndex]--;
        }
    }

    private int selectNextProcess(int currentTime, int currentProcessIndex, int[] remainingBurstTimes) {
        int processCount = processes.size();

        for (int i = 0; i < processCount; i++) {
            boolean hasArrived = processes.get(i).getArrivalTime() <= currentTime;
            boolean hasShorterRemainingTime =
                    remainingBurstTimes[i] < remainingBurstTimes[currentProcessIndex]
                            && remainingBurstTimes[i] > 0;
            boolean currentFinished = remainingBurstTimes[currentProcessIndex] == 0;

            if (hasArrived && (hasShorterRemainingTime || currentFinished)) {
                currentProcessIndex = i;
            }
        }

        return currentProcessIndex;
    }
}