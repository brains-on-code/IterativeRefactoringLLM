package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class HighestResponseRatioNextScheduling {

    private static final int PROCESS_NOT_FOUND = -1;
    private static final double INITIAL_MAX_RESPONSE_RATIO = -1.0;

    private HighestResponseRatioNextScheduling() {}

    private static class Process {
        String processName;
        int arrivalTime;
        int burstTime;
        int turnaroundTime;
        boolean isFinished;

        Process(String processName, int arrivalTime, int burstTime) {
            this.processName = processName;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.turnaroundTime = 0;
            this.isFinished = false;
        }

        double calculateResponseRatio(int currentTime) {
            return (double) (burstTime + currentTime - arrivalTime) / burstTime;
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

        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int finishedProcessCount = 0;
        while (finishedProcessCount < processCount) {
            int nextProcessIndex = findNextProcessIndex(processes, currentTime);
            if (nextProcessIndex == PROCESS_NOT_FOUND) {
                currentTime++;
                continue;
            }

            Process currentProcess = processes[nextProcessIndex];
            currentTime = Math.max(currentTime, currentProcess.arrivalTime);
            currentProcess.turnaroundTime =
                    currentTime + currentProcess.burstTime - currentProcess.arrivalTime;
            currentTime += currentProcess.burstTime;
            currentProcess.isFinished = true;
            finishedProcessCount++;
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

    private static int findNextProcessIndex(Process[] processes, int currentTime) {
        return findHighestResponseRatioIndex(processes, currentTime);
    }

    private static int findHighestResponseRatioIndex(Process[] processes, int currentTime) {
        double maxResponseRatio = INITIAL_MAX_RESPONSE_RATIO;
        int maxResponseRatioIndex = PROCESS_NOT_FOUND;

        for (int i = 0; i < processes.length; i++) {
            Process process = processes[i];
            if (!process.isFinished && process.arrivalTime <= currentTime) {
                double responseRatio = process.calculateResponseRatio(currentTime);
                if (responseRatio > maxResponseRatio) {
                    maxResponseRatio = responseRatio;
                    maxResponseRatioIndex = i;
                }
            }
        }
        return maxResponseRatioIndex;
    }
}