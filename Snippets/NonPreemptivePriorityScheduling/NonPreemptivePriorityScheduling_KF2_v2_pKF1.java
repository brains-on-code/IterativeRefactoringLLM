package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public final class NonPreemptivePriorityScheduling {

    private NonPreemptivePriorityScheduling() {
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
        Queue<Process> notArrivedQueue = new LinkedList<>();
        int currentTime = 0;
        int executionOrderIndex = 0;
        Process[] executionOrder = new Process[processes.length];

        Collections.addAll(notArrivedQueue, processes);

        while (!notArrivedQueue.isEmpty() || !readyQueue.isEmpty()) {
            while (!notArrivedQueue.isEmpty() && notArrivedQueue.peek().arrivalTime <= currentTime) {
                readyQueue.add(notArrivedQueue.poll());
            }

            if (!readyQueue.isEmpty()) {
                Process nextProcess = readyQueue.poll();
                nextProcess.startTime = currentTime;
                executionOrder[executionOrderIndex++] = nextProcess;
                currentTime += nextProcess.burstTime;
            } else if (!notArrivedQueue.isEmpty()) {
                currentTime = notArrivedQueue.peek().arrivalTime;
            }
        }

        return executionOrder;
    }

    public static double calculateAverageWaitingTime(Process[] processes, Process[] executionOrder) {
        int totalWaitingTime = 0;

        for (Process process : executionOrder) {
            int waitingTime = process.startTime - process.arrivalTime;
            totalWaitingTime += waitingTime;
        }

        return (double) totalWaitingTime / processes.length;
    }

    public static double calculateAverageTurnaroundTime(Process[] processes, Process[] executionOrder) {
        int totalTurnaroundTime = 0;

        for (Process process : executionOrder) {
            int turnaroundTime = process.startTime + process.burstTime - process.arrivalTime;
            totalTurnaroundTime += turnaroundTime;
        }

        return (double) totalTurnaroundTime / processes.length;
    }
}