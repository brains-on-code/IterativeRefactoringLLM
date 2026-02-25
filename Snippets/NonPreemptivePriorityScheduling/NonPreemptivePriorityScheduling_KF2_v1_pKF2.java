package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public final class NonPreemptivePriorityScheduling {

    private NonPreemptivePriorityScheduling() {
        // Utility class; prevent instantiation
    }

    /**
     * Represents a process in the scheduling system.
     * Comparable by priority, then by arrival time.
     */
    static class Process implements Comparable<Process> {
        int id;
        int arrivalTime;
        int startTime;
        int burstTime;
        int priority;

        Process(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.startTime = -1; // Indicates that the process has not started yet
            this.burstTime = burstTime;
            this.priority = priority;
        }

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
     * Schedules processes using non-preemptive priority scheduling.
     *
     * @param processes array of processes to schedule
     * @return array of processes in the order they are executed
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
            } else {
                // If no process is ready, jump to the next process's arrival time
                currentTime = arrivalQueue.peek().arrivalTime;
            }
        }

        return executionOrder;
    }

    /**
     * Calculates the average waiting time for the given execution order.
     *
     * @param processes       original array of processes
     * @param executionOrder  array of processes in the order they were executed
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
     * Calculates the average turnaround time for the given execution order.
     *
     * @param processes       original array of processes
     * @param executionOrder  array of processes in the order they were executed
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