package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class HighestResponseRatioNextScheduling {

    private static final int NO_PROCESS_INDEX = -1;
    private static final double INITIAL_MAX_RESPONSE_RATIO = -1.0;

    private HighestResponseRatioNextScheduling() {}

    private static class Process {
        String name;
        int arrivalTime;
        int burstTime;
        int turnaroundTime;
        boolean finished;

        Process(String name, int arrivalTime, int burstTime) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.turnaroundTime = 0;
            this.finished = false;
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

        for (int index = 0; index < processCount; index++) {
            processes[index] =
                    new Process(processNames[index], arrivalTimes[index], burstTimes[index]);
        }

        Arrays.sort(processes, Comparator.comparingInt(process -> process.arrivalTime));

        int completedProcessCount = 0;
        while (completedProcessCount < processCount) {
            int nextProcessIndex = findNextProcessIndex(processes, currentTime);
            if (nextProcessIndex == NO_PROCESS_INDEX) {
                currentTime++;
                continue;
            }

            Process currentProcess = processes[nextProcessIndex];
            currentTime = Math.max(currentTime, currentProcess.arrivalTime);
            currentProcess.turnaroundTime =
                    currentTime + currentProcess.burstTime - currentProcess.arrivalTime;
            currentTime += currentProcess.burstTime;
            currentProcess.finished = true;
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

    private static int findNextProcessIndex(Process[] processes, int currentTime) {
        return findHighestResponseRatioIndex(processes, currentTime);
    }

    private static int findHighestResponseRatioIndex(Process[] processes, int currentTime) {
        double maxResponseRatio = INITIAL_MAX_RESPONSE_RATIO;
        int maxResponseRatioIndex = NO_PROCESS_INDEX;

        for (int index = 0; index < processes.length; index++) {
            Process process = processes[index];
            if (!process.finished && process.arrivalTime <= currentTime) {
                double responseRatio = process.calculateResponseRatio(currentTime);
                if (responseRatio > maxResponseRatio) {
                    maxResponseRatio = responseRatio;
                    maxResponseRatioIndex = index;
                }
            }
        }
        return maxResponseRatioIndex;
    }
}