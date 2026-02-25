package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public final class PriorityScheduler {

    private PriorityScheduler() {
        // Utility class; prevent instantiation
    }

    static class Process implements Comparable<Process> {
        final int id;
        final int arrivalTime;
        final int burstTime;
        final int priority;
        int startTime;

        Process(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.priority = priority;
            this.startTime = -1;
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

    public static Process[] schedule(Process[] processes) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>();
        Queue<Process> arrivalQueue = new LinkedList<>();
        Process[] scheduled = new Process[processes.length];

        Collections.addAll(arrivalQueue, processes);

        int currentTime = 0;
        int scheduledCount = 0;

        while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToReadyQueue(arrivalQueue, readyQueue, currentTime);

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                currentProcess.startTime = currentTime;
                scheduled[scheduledCount++] = currentProcess;
                currentTime += currentProcess.burstTime;
                continue;
            }

            if (!arrivalQueue.isEmpty()) {
                currentTime = arrivalQueue.peek().arrivalTime;
            }
        }

        return scheduled;
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

    public static double averageWaitingTime(Process[] original, Process[] scheduled) {
        int totalWaitingTime = 0;

        for (Process process : scheduled) {
            totalWaitingTime += process.startTime - process.arrivalTime;
        }

        return (double) totalWaitingTime / original.length;
    }

    public static double averageTurnaroundTime(Process[] original, Process[] scheduled) {
        int totalTurnaroundTime = 0;

        for (Process process : scheduled) {
            totalTurnaroundTime += process.startTime + process.burstTime - process.arrivalTime;
        }

        return (double) totalTurnaroundTime / original.length;
    }
}