package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class HighestResponseRatioNextScheduling {

    private static final int PROCESS_NOT_FOUND = -1;
    private static final double INITIAL_MAX_RESPONSE_RATIO = -1.0;

    private HighestResponseRatioNextScheduling() {
        // Utility class
    }

    private static class Process {
        final String name;
        final int arrivalTime;
        final int burstTime;
        int turnAroundTime;
        boolean finished;

        Process(String name, int arrivalTime, int burstTime) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
        }

        double calculateResponseRatio(int currentTime) {
            int waitingTime = currentTime - arrivalTime;
            return (double) (waitingTime + burstTime) / burstTime;
        }
    }

    public static int[] calculateTurnAroundTime(
            final String[] processNames,
            final int[] arrivalTimes,
            final int[] burstTimes,
            final int noOfProcesses
    ) {
        Process[] processes = createAndSortProcesses(processNames, arrivalTimes, burstTimes, noOfProcesses);
        int[] turnAroundTimes = new int[noOfProcesses];

        int currentTime = 0;
        int finishedProcessCount = 0;

        while (finishedProcessCount < noOfProcesses) {
            int nextProcessIndex = findNextProcess(processes, currentTime);

            if (nextProcessIndex == PROCESS_NOT_FOUND) {
                currentTime++;
                continue;
            }

            Process currentProcess = processes[nextProcessIndex];
            currentTime = Math.max(currentTime, currentProcess.arrivalTime);

            currentProcess.turnAroundTime =
                    currentTime + currentProcess.burstTime - currentProcess.arrivalTime;

            currentTime += currentProcess.burstTime;
            currentProcess.finished = true;
            finishedProcessCount++;
        }

        for (int i = 0; i < noOfProcesses; i++) {
            turnAroundTimes[i] = processes[i].turnAroundTime;
        }

        return turnAroundTimes;
    }

    private static Process[] createAndSortProcesses(
            String[] processNames,
            int[] arrivalTimes,
            int[] burstTimes,
            int noOfProcesses
    ) {
        Process[] processes = new Process[noOfProcesses];

        for (int i = 0; i < noOfProcesses; i++) {
            processes[i] = new Process(processNames[i], arrivalTimes[i], burstTimes[i]);
        }

        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        return processes;
    }

    public static int[] calculateWaitingTime(int[] turnAroundTimes, int[] burstTimes) {
        int[] waitingTimes = new int[turnAroundTimes.length];

        for (int i = 0; i < turnAroundTimes.length; i++) {
            waitingTimes[i] = turnAroundTimes[i] - burstTimes[i];
        }

        return waitingTimes;
    }

    private static int findNextProcess(Process[] processes, int currentTime) {
        double maxResponseRatio = INITIAL_MAX_RESPONSE_RATIO;
        int maxIndex = PROCESS_NOT_FOUND;

        for (int i = 0; i < processes.length; i++) {
            Process process = processes[i];

            if (!process.finished && process.arrivalTime <= currentTime) {
                double responseRatio = process.calculateResponseRatio(currentTime);

                if (responseRatio > maxResponseRatio) {
                    maxResponseRatio = responseRatio;
                    maxIndex = i;
                }
            }
        }

        return maxIndex;
    }
}