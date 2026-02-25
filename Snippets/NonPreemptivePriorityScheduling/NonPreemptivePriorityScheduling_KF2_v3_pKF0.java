package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public final class NonPreemptivePriorityScheduling {

    private NonPreemptivePriorityScheduling() {
        // Utility class; prevent instantiation
    }

    static class Process implements Comparable<Process> {
        final int id;
        final int arrivalTime;
        final int burstTime;
        final int priority;
        int startTime = -1;

        Process(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
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

    public static Process[] scheduleProcesses(Process[] processes) {
        if (processes == null || processes.length == 0) {
            return new Process[0];
        }

        PriorityQueue<Process> readyQueue = new PriorityQueue<>();
        Queue<Process> arrivalQueue = new LinkedList<>();
        Collections.addAll(arrivalQueue, processes);

        int currentTime = 0;
        int executionIndex = 0;
        Process[] executionOrder = new Process[processes.length];

        while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToReadyQueue(arrivalQueue, readyQueue, currentTime);

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                currentProcess.startTime = currentTime;
                executionOrder[executionIndex++] = currentProcess;
                currentTime += currentProcess.burstTime;
                continue;
            }

            if (!arrivalQueue.isEmpty()) {
                currentTime = arrivalQueue.peek().arrivalTime;
            }
        }

        return executionOrder;
    }

    private static void moveArrivedProcessesToReadyQueue(
            Queue<Process> arrivalQueue,
            PriorityQueue<Process> readyQueue,
            int currentTime
    ) {
        while (!arrivalQueue.isEmpty() && arrivalQueue.peek().arrivalTime <= currentTime) {
            readyQueue.add(arrivalQueue.poll());
        }
    }

    public static double calculateAverageWaitingTime(Process[] processes, Process[] executionOrder) {
        if (processes == null || processes.length == 0 || executionOrder == null) {
            return 0.0;
        }

        int totalWaitingTime = 0;

        for (Process process : executionOrder) {
            int waitingTime = process.startTime - process.arrivalTime;
            totalWaitingTime += waitingTime;
        }

        return (double) totalWaitingTime / processes.length;
    }

    public static double calculateAverageTurnaroundTime(Process[] processes, Process[] executionOrder) {
        if (processes == null || processes.length == 0 || executionOrder == null) {
            return 0.0;
        }

        int totalTurnaroundTime = 0;

        for (Process process : executionOrder) {
            int turnaroundTime = process.startTime + process.burstTime - process.arrivalTime;
            totalTurnaroundTime += turnaroundTime;
        }

        return (double) totalTurnaroundTime / processes.length;
    }
}