package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public final class PriorityScheduling {

    private PriorityScheduling() {
    }

    static class Process implements Comparable<Process> {
        int processId;
        int arrivalTime;
        int startTime;
        int burstTime;
        int priority;

        Process(int processId, int arrivalTime, int burstTime, int priority) {
            this.processId = processId;
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
        Queue<Process> arrivalQueue = new LinkedList<>();
        int currentTime = 0;
        int scheduledCount = 0;
        Process[] scheduledOrder = new Process[processes.length];

        Collections.addAll(arrivalQueue, processes);

        while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()) {
            while (!arrivalQueue.isEmpty() && arrivalQueue.peek().arrivalTime <= currentTime) {
                readyQueue.add(arrivalQueue.poll());
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                currentProcess.startTime = currentTime;
                scheduledOrder[scheduledCount++] = currentProcess;
                currentTime += currentProcess.burstTime;
            } else {
                currentTime = arrivalQueue.peek().arrivalTime;
            }
        }

        return scheduledOrder;
    }

    public static double calculateAverageWaitingTime(Process[] originalProcesses, Process[] scheduledProcesses) {
        int totalWaitingTime = 0;

        for (Process process : scheduledProcesses) {
            int waitingTime = process.startTime - process.arrivalTime;
            totalWaitingTime += waitingTime;
        }

        return (double) totalWaitingTime / originalProcesses.length;
    }

    public static double calculateAverageTurnaroundTime(Process[] originalProcesses, Process[] scheduledProcesses) {
        int totalTurnaroundTime = 0;

        for (Process process : scheduledProcesses) {
            int turnaroundTime = process.startTime + process.burstTime - process.arrivalTime;
            totalTurnaroundTime += turnaroundTime;
        }

        return (double) totalTurnaroundTime / originalProcesses.length;
    }
}