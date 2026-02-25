package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class HighestResponseRatioNextScheduler {

    private static final int NO_ELIGIBLE_PROCESS_INDEX = -1;
    private static final double INITIAL_RESPONSE_RATIO = -1.0;

    private HighestResponseRatioNextScheduler() {
    }

    private static class Process {
        String name;
        int arrivalTime;
        int burstTime;
        int completionTime;
        boolean isCompleted;

        Process(String name, int arrivalTime, int burstTime) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.completionTime = 0;
            this.isCompleted = false;
        }

        double calculateResponseRatio(int currentTime) {
            int waitingTime = currentTime - arrivalTime;
            return (double) (waitingTime + burstTime) / burstTime;
        }
    }

    public static int[] calculateCompletionTimes(final String[] processNames,
                                                 final int[] arrivalTimes,
                                                 final int[] burstTimes,
                                                 final int processCount) {
        int currentTime = 0;
        int[] completionTimes = new int[processCount];
        Process[] processes = new Process[processCount];

        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            processes[processIndex] =
                new Process(processNames[processIndex], arrivalTimes[processIndex], burstTimes[processIndex]);
        }

        Arrays.sort(processes, Comparator.comparingInt(process -> process.arrivalTime));

        int completedProcessCount = 0;
        while (completedProcessCount < processCount) {
            int nextProcessIndex = selectNextProcessIndex(processes, currentTime);
            if (nextProcessIndex == NO_ELIGIBLE_PROCESS_INDEX) {
                currentTime++;
                continue;
            }

            Process processToRun = processes[nextProcessIndex];
            currentTime = Math.max(currentTime, processToRun.arrivalTime);
            processToRun.completionTime = currentTime + processToRun.burstTime - processToRun.arrivalTime;
            currentTime += processToRun.burstTime;
            processToRun.isCompleted = true;
            completedProcessCount++;
        }

        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            completionTimes[processIndex] = processes[processIndex].completionTime;
        }

        return completionTimes;
    }

    public static int[] calculateTurnaroundTimes(int[] completionTimes, int[] arrivalTimes) {
        int[] turnaroundTimes = new int[completionTimes.length];
        for (int index = 0; index < completionTimes.length; index++) {
            turnaroundTimes[index] = completionTimes[index] - arrivalTimes[index];
        }
        return turnaroundTimes;
    }

    private static int selectNextProcessIndex(Process[] processes, int currentTime) {
        return findProcessWithHighestResponseRatio(processes, currentTime);
    }

    private static int findProcessWithHighestResponseRatio(Process[] processes, int currentTime) {
        double highestResponseRatio = INITIAL_RESPONSE_RATIO;
        int highestResponseRatioProcessIndex = NO_ELIGIBLE_PROCESS_INDEX;

        for (int processIndex = 0; processIndex < processes.length; processIndex++) {
            Process process = processes[processIndex];
            if (!process.isCompleted && process.arrivalTime <= currentTime) {
                double responseRatio = process.calculateResponseRatio(currentTime);
                if (responseRatio > highestResponseRatio) {
                    highestResponseRatio = responseRatio;
                    highestResponseRatioProcessIndex = processIndex;
                }
            }
        }
        return highestResponseRatioProcessIndex;
    }
}