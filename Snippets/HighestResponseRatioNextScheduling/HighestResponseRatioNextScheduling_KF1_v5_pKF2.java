package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Highest Response Ratio Next (HRRN) scheduling utility.
 *
 * Given processes with arrival and burst times, this class computes:
 * - Completion times using HRRN scheduling.
 * - Turnaround times from completion and arrival times.
 */
public final class HrrnScheduler {

    private static final int NO_INDEX = -1;
    private static final double NO_RATIO = -1.0;

    private HrrnScheduler() {
        // Utility class; prevent instantiation.
    }

    /**
     * Represents a process in the HRRN scheduling algorithm.
     */
    private static class Process {
        final String name;
        final int arrivalTime;
        final int burstTime;
        int completionTime;
        boolean isCompleted;

        Process(String name, int arrivalTime, int burstTime) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
        }

        /**
         * Response Ratio:
         * (Waiting Time + Burst Time) / Burst Time
         * = (currentTime - arrivalTime + burstTime) / burstTime
         */
        double responseRatio(int currentTime) {
            return (double) (burstTime + currentTime - arrivalTime) / burstTime;
        }
    }

    /**
     * Computes completion times for processes using HRRN scheduling.
     *
     * @param names        process names
     * @param arrivalTimes arrival times for each process
     * @param burstTimes   burst times for each process
     * @param n            number of processes
     * @return completion times for each process, in the same order as input
     */
    public static int[] calculateCompletionTimes(
        final String[] names,
        final int[] arrivalTimes,
        final int[] burstTimes,
        final int n
    ) {
        int currentTime = 0;
        int[] completionTimes = new int[n];
        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            processes[i] = new Process(names[i], arrivalTimes[i], burstTimes[i]);
        }

        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int completedCount = 0;
        while (completedCount < n) {
            int index = selectNextProcess(processes, currentTime);
            if (index == NO_INDEX) {
                currentTime++;
                continue;
            }

            Process process = processes[index];
            currentTime = Math.max(currentTime, process.arrivalTime);
            process.completionTime = currentTime + process.burstTime - process.arrivalTime;
            currentTime += process.burstTime;
            process.isCompleted = true;
            completedCount++;
        }

        for (int i = 0; i < n; i++) {
            completionTimes[i] = processes[i].completionTime;
        }

        return completionTimes;
    }

    /**
     * Computes turnaround times from completion and arrival times.
     *
     * Turnaround Time = Completion Time - Arrival Time
     *
     * @param completionTimes completion times
     * @param arrivalTimes    arrival times
     * @return turnaround times
     */
    public static int[] calculateTurnaroundTimes(int[] completionTimes, int[] arrivalTimes) {
        int[] turnaroundTimes = new int[completionTimes.length];
        for (int i = 0; i < completionTimes.length; i++) {
            turnaroundTimes[i] = completionTimes[i] - arrivalTimes[i];
        }
        return turnaroundTimes;
    }

    /**
     * Selects the next process to schedule.
     *
     * @param processes   processes
     * @param currentTime current time in the schedule
     * @return index of the selected process, or NO_INDEX if none is available
     */
    private static int selectNextProcess(Process[] processes, int currentTime) {
        return selectProcessByHighestResponseRatio(processes, currentTime);
    }

    /**
     * Selects the index of the process with the highest response ratio among
     * all processes that have arrived and are not yet completed.
     *
     * @param processes   processes
     * @param currentTime current time in the schedule
     * @return index of the process with the highest response ratio, or NO_INDEX if none is available
     */
    private static int selectProcessByHighestResponseRatio(Process[] processes, int currentTime) {
        double bestRatio = NO_RATIO;
        int bestIndex = NO_INDEX;

        for (int i = 0; i < processes.length; i++) {
            Process process = processes[i];
            if (!process.isCompleted && process.arrivalTime <= currentTime) {
                double ratio = process.responseRatio(currentTime);
                if (ratio > bestRatio) {
                    bestRatio = ratio;
                    bestIndex = i;
                }
            }
        }
        return bestIndex;
    }
}