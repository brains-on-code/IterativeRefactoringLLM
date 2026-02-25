package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Shortest Remaining Time First Scheduling Algorithm.
 * In the SRTF scheduling algorithm, the process with the smallest amount of time
 * remaining until completion is selected to execute.
 *
 * More information: https://en.wikipedia.org/wiki/Shortest_remaining_time
 */
public class SRTFScheduling {

    protected final List<ProcessDetails> processes;
    protected final List<String> ready;

    /**
     * Constructor
     *
     * @param processes list of ProcessDetails given as input
     */
    public SRTFScheduling(List<ProcessDetails> processes) {
        this.processes = new ArrayList<>(processes);
        this.ready = new ArrayList<>();
    }

    public void evaluateScheduling() {
        if (processes.isEmpty()) {
            return;
        }

        int processCount = processes.size();
        int[] remainingTimes = new int[processCount];

        int totalTimeUnits = initializeRemainingTimesAndTotalUnits(remainingTimes);
        int firstArrivalTime = processes.get(0).getArrivalTime();

        if (firstArrivalTime > 0) {
            padIdleTimeBeforeFirstArrival(firstArrivalTime);
        }

        simulateScheduling(remainingTimes, totalTimeUnits, firstArrivalTime);
    }

    private int initializeRemainingTimesAndTotalUnits(int[] remainingTimes) {
        int totalTimeUnits = 0;

        for (int i = 0; i < processes.size(); i++) {
            int burstTime = processes.get(i).getBurstTime();
            remainingTimes[i] = burstTime;
            totalTimeUnits += burstTime;
        }

        int firstArrivalTime = processes.get(0).getArrivalTime();
        if (firstArrivalTime > 0) {
            totalTimeUnits += firstArrivalTime;
        }

        return totalTimeUnits;
    }

    private void padIdleTimeBeforeFirstArrival(int firstArrivalTime) {
        for (int i = 0; i < firstArrivalTime; i++) {
            ready.add(null);
        }
    }

    private void simulateScheduling(int[] remainingTimes, int totalTimeUnits, int startTime) {
        int currentProcessIndex = 0;

        for (int currentTime = startTime; currentTime < totalTimeUnits; currentTime++) {
            currentProcessIndex = selectNextProcess(currentTime, remainingTimes, currentProcessIndex);
            ready.add(processes.get(currentProcessIndex).getProcessId());
            remainingTimes[currentProcessIndex]--;
        }
    }

    private int selectNextProcess(int currentTime, int[] remainingTimes, int currentProcessIndex) {
        int processCount = processes.size();

        for (int i = 0; i < processCount; i++) {
            if (!hasArrived(i, currentTime) || !hasRemainingTime(remainingTimes[i])) {
                continue;
            }

            if (isBetterCandidate(remainingTimes, i, currentProcessIndex)) {
                currentProcessIndex = i;
            }
        }

        return currentProcessIndex;
    }

    private boolean hasArrived(int processIndex, int currentTime) {
        return processes.get(processIndex).getArrivalTime() <= currentTime;
    }

    private boolean hasRemainingTime(int remainingTimeForProcess) {
        return remainingTimeForProcess > 0;
    }

    private boolean isBetterCandidate(int[] remainingTimes, int candidateIndex, int currentProcessIndex) {
        return remainingTimes[currentProcessIndex] == 0
                || remainingTimes[candidateIndex] < remainingTimes[currentProcessIndex];
    }
}