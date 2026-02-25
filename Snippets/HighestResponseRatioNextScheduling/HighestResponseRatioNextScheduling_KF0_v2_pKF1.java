package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The {@code HighestResponseRatioNextScheduling} class implements the
 * Highest Response Ratio Next (HRRN) scheduling algorithm.
 * HRRN is a non-preemptive scheduling algorithm that selects the process with
 * the highest response ratio for execution.
 * The response ratio is calculated as:
 *
 * <pre>
 *     Response Ratio = (waiting time + burst time) / burst time
 * </pre>
 *
 * HRRN is designed to reduce the average waiting time and improve overall
 * system performance by balancing between short and long processes,
 * minimizing process starvation.
 */
public final class HighestResponseRatioNextScheduling {

    private static final int PROCESS_NOT_FOUND = -1;
    private static final double INITIAL_MAX_RESPONSE_RATIO = -1.0;

    private HighestResponseRatioNextScheduling() {}

    /**
     * Represents a process in the scheduling algorithm.
     */
    private static class ScheduledProcess {
        String name;
        int arrivalTime;
        int burstTime;
        int turnAroundTime;
        boolean finished;

        ScheduledProcess(String name, int arrivalTime, int burstTime) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.turnAroundTime = 0;
            this.finished = false;
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
    }

    /**
     * Calculates the Turn Around Time (TAT) for each process.
     *
     * <p>Turn Around Time is calculated as the total time a process spends
     * in the system from arrival to completion. It is the sum of the burst time
     * and the waiting time.</p>
     *
     * @param processNames Array of process names.
     * @param arrivalTimes Array of arrival times corresponding to each process.
     * @param burstTimes Array of burst times for each process.
     * @param processCount The number of processes.
     * @return An array of Turn Around Times for each process.
     */
    public static int[] calculateTurnAroundTime(
            final String[] processNames,
            final int[] arrivalTimes,
            final int[] burstTimes,
            final int processCount) {

        int currentTime = 0;
        int[] turnAroundTimes = new int[processCount];
        ScheduledProcess[] scheduledProcesses = new ScheduledProcess[processCount];

        for (int i = 0; i < processCount; i++) {
            scheduledProcesses[i] =
                    new ScheduledProcess(processNames[i], arrivalTimes[i], burstTimes[i]);
        }

        Arrays.sort(
                scheduledProcesses,
                Comparator.comparingInt(process -> process.arrivalTime));

        int completedProcessCount = 0;
        while (completedProcessCount < processCount) {
            int nextProcessIndex =
                    findNextProcessIndex(scheduledProcesses, currentTime);
            if (nextProcessIndex == PROCESS_NOT_FOUND) {
                currentTime++;
                continue;
            }

            ScheduledProcess currentProcess = scheduledProcesses[nextProcessIndex];
            currentTime = Math.max(currentTime, currentProcess.arrivalTime);
            currentProcess.turnAroundTime =
                    currentTime + currentProcess.burstTime - currentProcess.arrivalTime;
            currentTime += currentProcess.burstTime;
            currentProcess.finished = true;
            completedProcessCount++;
        }

        for (int i = 0; i < processCount; i++) {
            turnAroundTimes[i] = scheduledProcesses[i].turnAroundTime;
        }

        return turnAroundTimes;
    }

    /**
     * Calculates the Waiting Time (WT) for each process.
     *
     * @param turnAroundTimes The Turn Around Times for each process.
     * @param burstTimes The burst times for each process.
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
     * @param processes Array of ScheduledProcess objects.
     * @param currentTime The current time in the scheduling process.
     * @return The index of the next process to be scheduled, or PROCESS_NOT_FOUND if no process is ready.
     */
    private static int findNextProcessIndex(ScheduledProcess[] processes, int currentTime) {
        return findHighestResponseRatioIndex(processes, currentTime);
    }

    /**
     * Finds the process with the highest response ratio.
     *
     * <p>The response ratio is calculated as:
     * (waiting time + burst time) / burst time
     * where waiting time = current time - arrival time</p>
     *
     * @param processes Array of ScheduledProcess objects.
     * @param currentTime The current time in the scheduling process.
     * @return The index of the process with the highest response ratio, or PROCESS_NOT_FOUND if no process is ready.
     */
    private static int findHighestResponseRatioIndex(
            ScheduledProcess[] processes, int currentTime) {
        double maxResponseRatio = INITIAL_MAX_RESPONSE_RATIO;
        int maxResponseRatioIndex = PROCESS_NOT_FOUND;

        for (int i = 0; i < processes.length; i++) {
            ScheduledProcess process = processes[i];
            if (!process.finished && process.arrivalTime <= currentTime) {
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