package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The {@code HighestResponseRatioNextScheduling} class implements the
 * Highest Response Ratio Next (HRRN) scheduling algorithm.
 * HRRN is a non-preemptive scheduling algorithm that selects the process with
 * the highest response ratio for execution.
 *
 * <pre>
 *     Response Ratio = (waiting time + burst time) / burst time
 * </pre>
 */
public final class HighestResponseRatioNextScheduling {

    private static final int PROCESS_NOT_FOUND = -1;
    private static final double INITIAL_MAX_RESPONSE_RATIO = -1.0;

    private HighestResponseRatioNextScheduling() {
        // Utility class; prevent instantiation
    }

    /**
     * Represents a process in the scheduling algorithm.
     */
    private static final class Process {
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

        /**
         * Calculates the response ratio for this process.
         *
         * @param currentTime The current time in the scheduling process.
         * @return The response ratio for this process.
         */
        double calculateResponseRatio(int currentTime) {
            int waitingTime = currentTime - arrivalTime;
            return (double) (waitingTime + burstTime) / burstTime;
        }

        boolean isArrivedBy(int currentTime) {
            return arrivalTime <= currentTime;
        }

        boolean isPending() {
            return !finished;
        }
    }

    /**
     * Calculates the Turn Around Time (TAT) for each process.
     *
     * @param processNames  Array of process names.
     * @param arrivalTimes  Array of arrival times corresponding to each process.
     * @param burstTimes    Array of burst times for each process.
     * @param noOfProcesses The number of processes.
     * @return An array of Turn Around Times for each process.
     */
    public static int[] calculateTurnAroundTime(
        final String[] processNames,
        final int[] arrivalTimes,
        final int[] burstTimes,
        final int noOfProcesses
    ) {
        int currentTime = 0;
        int[] turnAroundTimes = new int[noOfProcesses];
        Process[] processes = createAndSortProcesses(processNames, arrivalTimes, burstTimes, noOfProcesses);

        int finishedProcessCount = 0;
        while (finishedProcessCount < noOfProcesses) {
            int nextProcessIndex = findNextProcessIndex(processes, currentTime);

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

    /**
     * Calculates the Waiting Time (WT) for each process.
     *
     * @param turnAroundTimes The Turn Around Times for each process.
     * @param burstTimes      The burst times for each process.
     * @return An array of Waiting Times for each process.
     */
    public static int[] calculateWaitingTime(int[] turnAroundTimes, int[] burstTimes) {
        int[] waitingTimes = new int[turnAroundTimes.length];

        for (int i = 0; i < turnAroundTimes.length; i++) {
            waitingTimes[i] = turnAroundTimes[i] - burstTimes[i];
        }

        return waitingTimes;
    }

    /**
     * Finds the next process to be scheduled based on arrival times and the current time.
     *
     * @param processes   Array of Process objects.
     * @param currentTime The current time in the scheduling process.
     * @return The index of the next process to be scheduled, or PROCESS_NOT_FOUND if no process is ready.
     */
    private static int findNextProcessIndex(Process[] processes, int currentTime) {
        double maxResponseRatio = INITIAL_MAX_RESPONSE_RATIO;
        int maxIndex = PROCESS_NOT_FOUND;

        for (int i = 0; i < processes.length; i++) {
            Process process = processes[i];

            if (!process.isPending() || !process.isArrivedBy(currentTime)) {
                continue;
            }

            double responseRatio = process.calculateResponseRatio(currentTime);
            if (responseRatio > maxResponseRatio) {
                maxResponseRatio = responseRatio;
                maxIndex = i;
            }
        }

        return maxIndex;
    }
}