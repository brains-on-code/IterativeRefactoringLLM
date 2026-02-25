package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    static class Class2 implements Comparable<Class2> {
        final int id;
        final int arrivalTime;
        final int burstTime;
        final int priority;
        int startTime;

        Class2(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.priority = priority;
            this.startTime = -1;
        }

        @Override
        public int compareTo(Class2 other) {
            int priorityComparison = Integer.compare(this.priority, other.priority);
            if (priorityComparison != 0) {
                return priorityComparison;
            }
            return Integer.compare(this.arrivalTime, other.arrivalTime);
        }
    }

    public static Class2[] schedule(Class2[] processes) {
        PriorityQueue<Class2> readyQueue = new PriorityQueue<>();
        Queue<Class2> arrivalQueue = new LinkedList<>();
        Class2[] scheduled = new Class2[processes.length];

        Collections.addAll(arrivalQueue, processes);

        int currentTime = 0;
        int scheduledCount = 0;

        while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()) {
            moveArrivedProcessesToReadyQueue(arrivalQueue, readyQueue, currentTime);

            if (!readyQueue.isEmpty()) {
                Class2 currentProcess = readyQueue.poll();
                currentProcess.startTime = currentTime;
                scheduled[scheduledCount++] = currentProcess;
                currentTime += currentProcess.burstTime;
            } else if (!arrivalQueue.isEmpty()) {
                currentTime = arrivalQueue.peek().arrivalTime;
            }
        }

        return scheduled;
    }

    private static void moveArrivedProcessesToReadyQueue(
            Queue<Class2> arrivalQueue,
            PriorityQueue<Class2> readyQueue,
            int currentTime
    ) {
        while (!arrivalQueue.isEmpty() && arrivalQueue.peek().arrivalTime <= currentTime) {
            readyQueue.add(arrivalQueue.poll());
        }
    }

    public static double averageWaitingTime(Class2[] original, Class2[] scheduled) {
        int totalWaitingTime = 0;

        for (Class2 process : scheduled) {
            int waitingTime = process.startTime - process.arrivalTime;
            totalWaitingTime += waitingTime;
        }

        return (double) totalWaitingTime / original.length;
    }

    public static double averageTurnaroundTime(Class2[] original, Class2[] scheduled) {
        int totalTurnaroundTime = 0;

        for (Class2 process : scheduled) {
            int turnaroundTime = process.startTime + process.burstTime - process.arrivalTime;
            totalTurnaroundTime += turnaroundTime;
        }

        return (double) totalTurnaroundTime / original.length;
    }
}