package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public final class Class1 {

    private Class1() {
    }

    static class Class2 implements Comparable<Class2> {
        int id;
        int arrivalTime;
        int startTime;
        int burstTime;
        int priority;

        Class2(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.startTime = -1;
            this.burstTime = burstTime;
            this.priority = priority;
        }

        @Override
        public int compareTo(Class2 other) {
            if (this.priority == other.priority) {
                return Integer.compare(this.arrivalTime, other.arrivalTime);
            }
            return Integer.compare(this.priority, other.priority);
        }
    }

    public static Class2[] schedule(Class2[] processes) {
        PriorityQueue<Class2> readyQueue = new PriorityQueue<>();
        Queue<Class2> arrivalQueue = new LinkedList<>();
        int currentTime = 0;
        int scheduledCount = 0;
        Class2[] scheduled = new Class2[processes.length];

        Collections.addAll(arrivalQueue, processes);

        while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()) {
            while (!arrivalQueue.isEmpty() && arrivalQueue.peek().arrivalTime <= currentTime) {
                readyQueue.add(arrivalQueue.poll());
            }

            if (!readyQueue.isEmpty()) {
                Class2 currentProcess = readyQueue.poll();
                currentProcess.startTime = currentTime;
                scheduled[scheduledCount++] = currentProcess;
                currentTime += currentProcess.burstTime;
            } else {
                currentTime = arrivalQueue.peek().arrivalTime;
            }
        }

        return scheduled;
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