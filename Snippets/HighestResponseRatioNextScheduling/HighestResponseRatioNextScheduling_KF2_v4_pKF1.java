package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class HighestResponseRatioNextScheduling {

    private static final int NO_ELIGIBLE_PROCESS_INDEX = -1;
    private static final double INITIAL_HIGHEST_RESPONSE_RATIO = -1.0;

    private HighestResponseRatioNextScheduling() {}

    private static class Process {
        String processId;
        int arrivalTime;
        int burstTime;
        int turnaroundTime;
        boolean completed;

        Process(String processId, int arrivalTime, int burstTime) {
            this.processId = processId;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.turnaroundTime = 0;
            this.completed = false;
        }

        double calculateResponseRatio(int currentTime) {
            int waitingTime = currentTime - arrivalTime;
            return (double) (waitingTime + burstTime) / burstTime;
        }
    }

    public static int[] calculateTurnaroundTimes(
            final String[] processIds,
            final int[] arrivalTimes,
            final int[] burstTimes,
            final int processCount) {

        int currentTime = 0;
        int[] turnaroundTimes = new int[processCount];
        Process[] processes = new Process[processCount];

        for (int index = 0; index < processCount; index++) {
            processes[index] = new Process(processIds[index], arrivalTimes[index], burstTimes[index]);
        }

        Arrays.sort(processes, Comparator.comparingInt(process -> process.arrivalTime));

        int completedProcessCount = 0;
        while (completedProcessCount < processCount) {
            int nextProcessIndex = selectNextProcessIndex(processes, currentTime);
            if (nextProcessIndex == NO_ELIGIBLE_PROCESS_INDEX) {
                currentTime++;
                continue;
            }

            Process selectedProcess = processes[nextProcessIndex];
            currentTime = Math.max(currentTime, selectedProcess.arrivalTime);
            selectedProcess.turnaroundTime =
                    currentTime + selectedProcess.burstTime - selectedProcess.arrivalTime;
            currentTime += selectedProcess.burstTime;
            selectedProcess.completed = true;
            completedProcessCount++;
        }

        for (int index = 0; index < processCount; index++) {
            turnaroundTimes[index] = processes[index].turnaroundTime;
        }

        return turnaroundTimes;
    }

    public static int[] calculateWaitingTimes(int[] turnaroundTimes, int[] burstTimes) {
        int[] waitingTimes = new int[turnaroundTimes.length];
        for (int index = 0; index < turnaroundTimes.length; index++) {
            waitingTimes[index] = turnaroundTimes[index] - burstTimes[index];
        }
        return waitingTimes;
    }

    private static int selectNextProcessIndex(Process[] processes, int currentTime) {
        return findProcessIndexWithHighestResponseRatio(processes, currentTime);
    }

    private static int findProcessIndexWithHighestResponseRatio(Process[] processes, int currentTime) {
        double highestResponseRatio = INITIAL_HIGHEST_RESPONSE_RATIO;
        int highestResponseRatioIndex = NO_ELIGIBLE_PROCESS_INDEX;

        for (int index = 0; index < processes.length; index++) {
            Process process = processes[index];
            if (!process.completed && process.arrivalTime <= currentTime) {
                double responseRatio = process.calculateResponseRatio(currentTime);
                if (responseRatio > highestResponseRatio) {
                    highestResponseRatio = responseRatio;
                    highestResponseRatioIndex = index;
                }
            }
        }
        return highestResponseRatioIndex;
    }
}