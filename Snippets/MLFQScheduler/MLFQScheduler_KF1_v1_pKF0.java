package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Multilevel feedback queue scheduler.
 */
public class Class1 {

    /** Queues for each priority level. */
    private final List<Queue<Class2>> priorityQueues;

    /** Time quantum for each priority level. */
    private final int[] timeQuanta;

    /** Total elapsed time. */
    private int currentTime;

    /**
     * @param numberOfLevels number of priority levels
     * @param timeQuanta     time quantum for each level
     */
    public Class1(int numberOfLevels, int[] timeQuanta) {
        this.priorityQueues = new ArrayList<>(numberOfLevels);
        for (int i = 0; i < numberOfLevels; i++) {
            this.priorityQueues.add(new LinkedList<>());
        }
        this.timeQuanta = timeQuanta;
        this.currentTime = 0;
    }

    /** Add a process to the highest-priority queue. */
    public void method1(Class2 process) {
        priorityQueues.get(0).add(process);
    }

    /** Run the scheduler until all queues are empty. */
    public void method2() {
        while (!allQueuesEmpty()) {
            for (int level = 0; level < priorityQueues.size(); level++) {
                Queue<Class2> queue = priorityQueues.get(level);
                if (queue.isEmpty()) {
                    continue;
                }

                Class2 process = queue.poll();
                int quantum = timeQuanta[level];

                int timeSlice = Math.min(quantum, process.remainingTime);
                process.method5(timeSlice);
                currentTime += timeSlice;

                if (process.method6()) {
                    System.out.println("Process " + process.id + " finished at time " + currentTime);
                } else {
                    if (level < priorityQueues.size() - 1) {
                        process.currentQueueLevel++;
                        priorityQueues.get(level + 1).add(process);
                    } else {
                        queue.add(process);
                    }
                }
            }
        }
    }

    /** Check if all queues are empty. */
    private boolean allQueuesEmpty() {
        for (Queue<Class2> queue : priorityQueues) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /** Get total elapsed time. */
    public int method4() {
        return currentTime;
    }
}

/** Represents a process in the scheduler. */
class Class2 {

    int id;
    int burstTime;
    int remainingTime;
    int arrivalTime;
    int currentQueueLevel;

    Class2(int id, int burstTime, int arrivalTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.currentQueueLevel = 0;
    }

    /** Execute the process for a given time slice. */
    public void method5(int timeSlice) {
        remainingTime -= timeSlice;
        if (remainingTime < 0) {
            remainingTime = 0;
        }
    }

    /** Check if the process has finished execution. */
    public boolean method6() {
        return remainingTime == 0;
    }
}