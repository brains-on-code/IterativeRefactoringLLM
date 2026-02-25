package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Non-preemptive priority scheduling.
 *
 * Processes are executed in order of priority (lower value = higher priority).
 * Once a process starts, it runs to completion.
 */
public final class NonPreemptivePriorityScheduling {

    private NonPreemptivePriorityScheduling() {
        // Utility class; prevent instantiation
    }

    /**
     * Represents a process in the scheduling system.
     */
    static class Process implements Comparable<Process> {
        int id;
        int arrivalTime;
        int startTime;
        int burstTime;
        int priority;

        /**
         * @param id          process identifier
         * @param arrivalTime time when the process arrives
         * @param burstTime   required CPU time
         * @param priority    lower value means higher priority
         */
        Process(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.startTime = -1;
            this.burstTime = burstTime;
            this.priority = priority;
        }

        /**
         * Compares processes by priority, then by arrival time.
         */
        @Override
        public int compareTo(Process other) {
            int byPriority = Integer.compare(this.priority, other.priority);
            if (byPriority != 0) {
                return byPriority;
            }
            return Integer.compare(this.arrivalTime, other.arrivalTime);
        }
    }

    /**
     * Schedules processes using non-preemptive priority scheduling, considering arrival times.
     *
     * @param processes processes to schedule
     * @return processes in execution order
     */
    public static Process[] scheduleProcesses(Process[] processes) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>();
        Queue<Process> arrivalQueue = new LinkedList<>();
        Process[] executionOrder = new Process[processes.length];

        int currentTime = 0;
        int executionIndex = 0;

        Collections.addAll(arrivalQueue, processes);

        while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()) {
            // Move all processes that have arrived into the ready queue
            while (!arrivalQueue.isEmpty() && arrivalQueue.peek().arrivalTime <= currentTime) {
                readyQueue.add(arrivalQueue.poll());
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                currentProcess.startTime = currentTime;
                executionOrder[executionIndex++] = currentProcess;
                currentTime += currentProcess.burstTime;
            } else if (!arrivalQueue.isEmpty()) {
                // No process is ready; jump to the next arrival time
                currentTime = arrivalQueue.peek().arrivalTime;
            }
        }

        return executionOrder;
    }

    /**
     * Calculates the average waiting time.
     *
     * Waiting time = start time - arrival time.
     *
     * @param processes      all processes
     * @param executionOrder processes in execution order
     * @return average waiting time
     */
    public static double calculateAverageWaitingTime(Process[] processes, Process[] executionOrder) {
        int totalWaitingTime = 0;

        for (Process process : executionOrder) {
            int waitingTime = process.startTime - process.arrivalTime;
            totalWaitingTime += waitingTime;
        }

        return (double) totalWaitingTime / processes.length;
    }

    /**
     * Calculates the average turnaround time.
     *
     * Turnaround time = completion time - arrival time,
     * where completion time = start time + burst time.
     *
     * @param processes      all processes
     * @param executionOrder processes in execution order
     * @return average turnaround time
     */
    public static double calculateAverageTurnaroundTime(Process[] processes, Process[] executionOrder) {
        int totalTurnaroundTime = 0;

        for (Process process : executionOrder) {
            int completionTime = process.startTime + process.burstTime;
            int turnaroundTime = completionTime - process.arrivalTime;
            totalTurnaroundTime += turnaroundTime;
        }

        return (double) totalTurnaroundTime / processes.length;
    }
}