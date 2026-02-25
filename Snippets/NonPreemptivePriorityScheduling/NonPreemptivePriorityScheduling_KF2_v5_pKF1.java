package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public final class NonPreemptivePriorityScheduling {

    private NonPreemptivePriorityScheduling() {}

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
        public int compareTo(Process otherProcess) {
            if (this.priority == otherProcess.priority) {
                return Integer.compare(this.arrivalTime, otherProcess.arrivalTime);
            }
            return Integer.compare(this.priority, otherProcess.priority);
        }
    }

    public static Process[] scheduleProcesses(Process[] processes) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>();
        Queue<Process> notArrivedQueue = new LinkedList<>();
        int currentTime = 0;
        int scheduledCount = 0;
        Process[] scheduledProcesses = new Process[processes.length];

        Collections.addAll(notArrivedQueue, processes);

        while (!notArrivedQueue.isEmpty() || !readyQueue.isEmpty()) {
            while (!notArrivedQueue.isEmpty() && notArrivedQueue.peek().arrivalTime <= currentTime) {
                readyQueue.add(notArrivedQueue.poll());
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                currentProcess.startTime = currentTime;
                scheduledProcesses[scheduledCount++] = currentProcess;
                currentTime += currentProcess.burstTime;
            } else if (!notArrivedQueue.isEmpty()) {
                currentTime = notArrivedQueue.peek().arrivalTime;
            }
        }

        return scheduledProcesses;
    }

    public static double calculateAverageWaitingTime(Process[] processes, Process[] scheduledProcesses) {
        int totalWaitingTime = 0;

        for (Process process : scheduledProcesses) {
            int waitingTime = process.startTime - process.arrivalTime;
            totalWaitingTime += waitingTime;
        }

        return (double) totalWaitingTime / processes.length;
    }

    public static double calculateAverageTurnaroundTime(Process[] processes, Process[] scheduledProcesses) {
        int totalTurnaroundTime = 0;

        for (Process process : scheduledProcesses) {
            int turnaroundTime = process.startTime + process.burstTime - process.arrivalTime;
            totalTurnaroundTime += turnaroundTime;
        }

        return (double) totalTurnaroundTime / processes.length;
    }
}