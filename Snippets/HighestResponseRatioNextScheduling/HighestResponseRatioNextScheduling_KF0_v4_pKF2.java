package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Highest Response Ratio Next (HRRN) non-preemptive scheduling.
 *
 * Response Ratio = (waiting time + burst time) / burst time
 */
public final class HighestResponseRatioNextScheduling {

    private static final int PROCESS_NOT_FOUND = -1;
    private static final double INITIAL_MAX_RESPONSE_RATIO = -1.0;

    private HighestResponseRatioNextScheduling() {
        // Prevent instantiation
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

        double responseRatioAt(int currentTime) {
            int waitingTime = currentTime - arrivalTime;
            return (double) (waitingTime + burstTime) / burstTime;
        }
    }

    /**
     * Computes turnaround time for each process using HRRN scheduling.
     *
     * Turnaround time = completion time - arrival time.
     */
    public static int[] calculateTurnAroundTime(
            final String[] processNames,
            final int[] arrivalTimes,
            final int[] burstTimes,
            final int noOfProcesses
    ) {
        int currentTime = 0;
        int[] turnAroundTimes = new int[noOfProcesses];
        Process[] processes = new Process[noOfProcesses];

        for (int i = 0; i < noOfProcesses; i++) {
            processes[i] = new Process(processNames[i], arrivalTimes[i], burstTimes[i]);
        }

        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int finishedCount = 0;
        while (finishedCount < noOfProcesses) {
            int nextIndex = findHighestResponseRatioIndex(processes, currentTime);

            if (nextIndex == PROCESS_NOT_FOUND) {
                currentTime++;
                continue;
            }

            Process currentProcess = processes[nextIndex];
            currentTime = Math.max(currentTime, currentProcess.arrivalTime);

            int completionTime = currentTime + currentProcess.burstTime;
            currentProcess.turnAroundTime = completionTime - currentProcess.arrivalTime;

            currentTime = completionTime;
            currentProcess.finished = true;
            finishedCount++;
        }

        for (int i = 0; i < noOfProcesses; i++) {
            turnAroundTimes[i] = processes[i].turnAroundTime;
        }

        return turnAroundTimes;
    }

    /**
     * Calculates waiting time for each process.
     *
     * Waiting time = turnaround time - burst time.
     */
    public static int[] calculateWaitingTime(int[] turnAroundTimes, int[] burstTimes) {
        int[] waitingTimes = new int[turnAroundTimes.length];
        for (int i = 0; i < turnAroundTimes.length; i++) {
            waitingTimes[i] = turnAroundTimes[i] - burstTimes[i];
        }
        return waitingTimes;
    }

    private static int findHighestResponseRatioIndex(Process[] processes, int currentTime) {
        double maxResponseRatio = INITIAL_MAX_RESPONSE_RATIO;
        int maxIndex = PROCESS_NOT_FOUND;

        for (int i = 0; i < processes.length; i++) {
            Process process = processes[i];

            if (process.finished || process.arrivalTime > currentTime) {
                continue;
            }

            double responseRatio = process.responseRatioAt(currentTime);
            if (responseRatio > maxResponseRatio) {
                maxResponseRatio = responseRatio;
                maxIndex = i;
            }
        }

        return maxIndex;
    }
}