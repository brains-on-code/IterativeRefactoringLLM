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
        int[] remainingTime = new int[processCount];

        int totalTimeUnits = initializeRemainingTimesAndTotalUnits(remainingTime);
        int firstArrivalTime = processes.get(0).getArrivalTime();

        if (firstArrivalTime > 0) {
            padIdleTimeBeforeFirstArrival(firstArrivalTime);
        }

        simulateScheduling(remainingTime, totalTimeUnits, firstArrivalTime);
    }

    private int initializeRemainingTimesAndTotalUnits(int[] remainingTime) {
        int totalTimeUnits = 0;

        for (int i = 0; i < processes.size(); i++) {
            int burstTime = processes.get(i).getBurstTime();
            remainingTime[i] = burstTime;
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

    private void simulateScheduling(int[] remainingTime, int totalTimeUnits, int startTime) {
        int currentRunningIndex = 0;

        for (int currentTime = startTime; currentTime < totalTimeUnits; currentTime++) {
            currentRunningIndex = selectNextProcess(currentTime, remainingTime, currentRunningIndex);
            ready.add(processes.get(currentRunningIndex).getProcessId());
            remainingTime[currentRunningIndex]--;
        }
    }

    private int selectNextProcess(int currentTime, int[] remainingTime, int currentRunningIndex) {
        int processCount = processes.size();

        for (int i = 0; i < processCount; i++) {
            if (!hasArrived(i, currentTime) || !hasRemainingTime(remainingTime[i])) {
                continue;
            }

            if (isBetterCandidate(remainingTime, i, currentRunningIndex)) {
                currentRunningIndex = i;
            }
        }

        return currentRunningIndex;
    }

    private boolean hasArrived(int processIndex, int currentTime) {
        return processes.get(processIndex).getArrivalTime() <= currentTime;
    }

    private boolean hasRemainingTime(int remainingTimeForProcess) {
        return remainingTimeForProcess > 0;
    }

    private boolean isBetterCandidate(int[] remainingTime, int candidateIndex, int currentRunningIndex) {
        return remainingTime[currentRunningIndex] == 0
                || remainingTime[candidateIndex] < remainingTime[currentRunningIndex];
    }
}