package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This class implements the Non-Preemptive Priority Scheduling algorithm.
 * Processes are executed in order of their priority. The process with the
 * highest priority (lower priority number) is executed first,
 * and once a process starts executing, it cannot be preempted.
 */
public final class NonPreemptivePriorityScheduling {

    private NonPreemptivePriorityScheduling() {
        // Utility class; prevent instantiation
    }

    /**
     * Represents a process with an ID, burst time, priority, arrival time, and start time.
     */
    static class Process implements Comparable<Process> {
        final int id;
        final int arrivalTime;
        final int burstTime;
        final int priority;
        int startTime;

        /**
         * Constructs a Process instance with the specified parameters.
         *
         * @param id          Unique identifier for the process
         * @param arrivalTime Time when the process arrives in the system
         * @param burstTime   Time required for the process execution
         * @param priority    Priority of the process
         */
        Process(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.priority = priority;
            this.startTime = -1;
        }

        /**
         * Compare based on priority for scheduling. The process with the lowest
         * priority value is selected first.
         * If two processes have the same priority, the one that arrives earlier is selected.
         *
         * @param other The other process to compare against
         * @return A negative integer, zero, or a positive integer as this process
         *         is less than, equal to, or greater than the specified process.
         */
        @Override
        public int compareTo(Process other) {
            int priorityComparison = Integer.compare(this.priority, other.priority);
            if (priorityComparison != 0) {
                return priorityComparison;
            }
            return Integer.compare(this.arrivalTime, other.arrivalTime);
        }
    }

    /**
     * Schedules processes based on their priority in a non-preemptive manner, considering their arrival times.
     *
     * @param processes Array of processes to be scheduled.
     * @return Array of processes in the order they are executed.
     */
    public static Process[] scheduleProcesses(Process[] processes) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>();
        Queue<Process> arrivalQueue = new LinkedList<>();
        Collections.addAll(arrivalQueue, processes);

        int currentTime = 0;
        int executionIndex = 0;
        Process[] executionOrder = new Process[processes.length];

        while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()) {
            // Move all processes that have arrived by currentTime into the ready queue
            while (!arrivalQueue.isEmpty() && arrivalQueue.peek().arrivalTime <= currentTime) {
                readyQueue.add(arrivalQueue.poll());
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                currentProcess.startTime = currentTime;
                executionOrder[executionIndex++] = currentProcess;
                currentTime += currentProcess.burstTime;
            } else if (!arrivalQueue.isEmpty()) {
                // If no process is ready, jump to the next process arrival time
                currentTime = arrivalQueue.peek().arrivalTime;
            }
        }

        return executionOrder;
    }

    /**
     * Calculates the average waiting time of the processes.
     *
     * @param processes      Array of processes.
     * @param executionOrder Array of processes in execution order.
     * @return Average waiting time.
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
     * Calculates the average turn-around time of the processes.
     *
     * @param processes      Array of processes.
     * @param executionOrder Array of processes in execution order.
     * @return Average turn-around time.
     */
    public static double calculateAverageTurnaroundTime(Process[] processes, Process[] executionOrder) {
        int totalTurnaroundTime = 0;

        for (Process process : executionOrder) {
            int turnaroundTime = process.startTime + process.burstTime - process.arrivalTime;
            totalTurnaroundTime += turnaroundTime;
        }

        return (double) totalTurnaroundTime / processes.length;
    }
}