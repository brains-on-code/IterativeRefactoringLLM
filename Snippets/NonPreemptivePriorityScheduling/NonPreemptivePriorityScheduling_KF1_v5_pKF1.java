package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public final class PriorityScheduling {

    private PriorityScheduling() {
    }

    static class Process implements Comparable<Process> {
        int id;
        int arrivalTime;
        int startTime;
        int burstTime;
        int priority;

        Process(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.startTime = -1;
            this.burstTime = burstTime;
            this.priority = priority;
        }

        @Override
        public int compareTo(Process other) {
            if (this.priority == other.priority) {
                return Integer.compare(this.arrivalTime, other.arrivalTime);
            }
            return Integer.compare(this.priority, other.priority);
        }
    }

    public static Process[] scheduleProcesses(Process[] processes) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>();
        Queue<Process> futureArrivalsQueue = new LinkedList<>();
        int currentTime = 0;
        int scheduledCount = 0;
        Process[] scheduledOrder = new Process[processes.length];

        Collections.addAll(futureArrivalsQueue, processes);

        while (!futureArrivalsQueue.isEmpty() || !readyQueue.isEmpty()) {
            while (!futureArrivalsQueue.isEmpty() && futureArrivalsQueue.peek().arrivalTime <= currentTime) {
                readyQueue.add(futureArrivalsQueue.poll());
            }

            if (!readyQueue.isEmpty()) {
                Process nextProcess = readyQueue.poll();
                nextProcess.startTime = currentTime;
                scheduledOrder[scheduledCount++] = nextProcess;
                currentTime += nextProcess.burstTime;
            } else {
                currentTime = futureArrivalsQueue.peek().arrivalTime;
            }
        }

        return scheduledOrder;
    }

    public static double calculateAverageWaitingTime(Process[] processes, Process[] scheduledOrder) {
        int totalWaitingTime = 0;

        for (Process process : scheduledOrder) {
            int waitingTime = process.startTime - process.arrivalTime;
            totalWaitingTime += waitingTime;
        }

        return (double) totalWaitingTime / processes.length;
    }

    public static double calculateAverageTurnaroundTime(Process[] processes, Process[] scheduledOrder) {
        int totalTurnaroundTime = 0;

        for (Process process : scheduledOrder) {
            int turnaroundTime = process.startTime + process.burstTime - process.arrivalTime;
            totalTurnaroundTime += turnaroundTime;
        }

        return (double) totalTurnaroundTime / processes.length;
    }
}