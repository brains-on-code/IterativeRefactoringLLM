package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class HighestResponseRatioNextScheduling {

    private static final int NO_ELIGIBLE_PROCESS = -1;
    private static final double INITIAL_MAX_RESPONSE_RATIO = -1.0;

    private HighestResponseRatioNextScheduling() {}

    private static class Process {
        String name;
        int arrivalTime;
        int burstTime;
        int turnaroundTime;
        boolean isCompleted;

        Process(String name, int arrivalTime, int burstTime) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.turnaroundTime = 0;
            this.isCompleted = false;
        }

        double calculateResponseRatio(int currentTime) {
            int waitingTime = currentTime - arrivalTime;
            return (double) (waitingTime + burstTime) / burstTime;
        }
    }

    public static int[] calculateTurnaroundTimes(
            final String[] processNames,
            final int[] arrivalTimes,
            final int[] burstTimes,
            final int processCount) {

        int currentTime = 0;
        int[] turnaroundTimes = new int[processCount];
        Process[] processes = new Process[processCount];

        for (int i = 0; i < processCount; i++) {
            processes[i] = new Process(processNames[i], arrivalTimes[i], burstTimes[i]);
        }

        Arrays.sort(processes, Comparator.comparingInt(process -> process.arrivalTime));

        int completedProcesses = 0;
        while (completedProcesses < processCount) {
            int nextProcessIndex = selectNextProcessIndex(processes, currentTime);
            if (nextProcessIndex == NO_ELIGIBLE_PROCESS) {
                currentTime++;
                continue;
            }

            Process selectedProcess = processes[nextProcessIndex];
            currentTime = Math.max(currentTime, selectedProcess.arrivalTime);
            selectedProcess.turnaroundTime =
                    currentTime + selectedProcess.burstTime - selectedProcess.arrivalTime;
            currentTime += selectedProcess.burstTime;
            selectedProcess.isCompleted = true;
            completedProcesses++;
        }

        for (int i = 0; i < processCount; i++) {
            turnaroundTimes[i] = processes[i].turnaroundTime;
        }

        return turnaroundTimes;
    }

    public static int[] calculateWaitingTimes(int[] turnaroundTimes, int[] burstTimes) {
        int[] waitingTimes = new int[turnaroundTimes.length];
        for (int i = 0; i < turnaroundTimes.length; i++) {
            waitingTimes[i] = turnaroundTimes[i] - burstTimes[i];
        }
        return waitingTimes;
    }

    private static int selectNextProcessIndex(Process[] processes, int currentTime) {
        return findProcessWithHighestResponseRatio(processes, currentTime);
    }

    private static int findProcessWithHighestResponseRatio(Process[] processes, int currentTime) {
        double highestResponseRatio = INITIAL_MAX_RESPONSE_RATIO;
        int highestResponseRatioIndex = NO_ELIGIBLE_PROCESS;

        for (int i = 0; i < processes.length; i++) {
            Process process = processes[i];
            if (!process.isCompleted && process.arrivalTime <= currentTime) {
                double responseRatio = process.calculateResponseRatio(currentTime);
                if (responseRatio > highestResponseRatio) {
                    highestResponseRatio = responseRatio;
                    highestResponseRatioIndex = i;
                }
            }
        }
        return highestResponseRatioIndex;
    }
}