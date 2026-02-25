package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Multilevel feedback queue scheduler.
 *
 * Each queue has its own time quantum. Processes start in the highest-priority
 * queue and are demoted to lower-priority queues if they are not finished
 * within the allotted quantum.
 */
public class Class1 {

    /** List of ready queues, one per priority level (0 = highest priority). */
    private final List<Queue<Class2>> queues;

    /** Time quantum for each priority level. */
    private final int[] timeQuanta;

    /** Total elapsed time in the scheduler. */
    private int currentTime;

    /**
     * Creates a multilevel feedback queue scheduler.
     *
     * @param numberOfLevels number of priority levels (queues)
     * @param timeQuanta     time quantum for each level; length must equal numberOfLevels
     */
    public Class1(int numberOfLevels, int[] timeQuanta) {
        this.queues = new ArrayList<>(numberOfLevels);
        for (int i = 0; i < numberOfLevels; i++) {
            queues.add(new LinkedList<>());
        }
        this.timeQuanta = timeQuanta;
        this.currentTime = 0;
    }

    /**
     * Adds a process to the highest-priority queue.
     *
     * @param process process to be scheduled
     */
    public void method1(Class2 process) {
        queues.get(0).add(process);
    }

    /**
     * Runs the scheduling simulation until all queues are empty.
     * Uses round-robin within each level and demotes unfinished processes
     * to the next lower-priority queue.
     */
    public void method2() {
        while (!allQueuesEmpty()) {
            for (int level = 0; level < queues.size(); level++) {
                Queue<Class2> queue = queues.get(level);
                if (queue.isEmpty()) {
                    continue;
                }

                Class2 process = queue.poll();
                int quantum = timeQuanta[level];

                // Run the process for at most its remaining time or the queue's quantum.
                int timeSlice = Math.min(quantum, process.var14);
                process.method5(timeSlice);
                currentTime += timeSlice;

                if (process.method6()) {
                    System.out.println("Process " + process.var4 + " finished at time " + currentTime);
                } else {
                    // Demote to next lower-priority queue if possible; otherwise, stay in the same queue.
                    if (level < queues.size() - 1) {
                        process.var15++;
                        queues.get(level + 1).add(process);
                    } else {
                        queue.add(process);
                    }
                }
            }
        }
    }

    /**
     * Checks whether all queues are empty.
     *
     * @return true if all queues are empty; false otherwise
     */
    private boolean allQueuesEmpty() {
        for (Queue<Class2> queue : queues) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the total elapsed time in the scheduler.
     *
     * @return total elapsed time
     */
    public int method4() {
        return currentTime;
    }
}

/**
 * Represents a process in the scheduler.
 */
class Class2 {

    /** Process identifier. */
    int var4;

    /** Original burst time. */
    int var5;

    /** Remaining burst time. */
    int var14;

    /** Arrival time (unused in current implementation). */
    int var6;

    /** Number of times the process has been demoted. */
    int var15;

    /**
     * Creates a process.
     *
     * @param id         process identifier
     * @param burstTime  total CPU burst time required
     * @param arrivalTime arrival time of the process
     */
    Class2(int id, int burstTime, int arrivalTime) {
        this.var4 = id;
        this.var5 = burstTime;
        this.var14 = burstTime;
        this.var6 = arrivalTime;
        this.var15 = 0;
    }

    /**
     * Executes the process for the given time slice.
     * Remaining time will not go below zero.
     *
     * @param timeSlice time to execute
     */
    public void method5(int timeSlice) {
        var14 -= timeSlice;
        if (var14 < 0) {
            var14 = 0;
        }
    }

    /**
     * Checks whether the process has finished execution.
     *
     * @return true if remaining time is zero; false otherwise
     */
    public boolean method6() {
        return var14 == 0;
    }
}