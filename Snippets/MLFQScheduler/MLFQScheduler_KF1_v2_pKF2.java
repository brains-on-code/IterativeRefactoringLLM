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
public class MultilevelFeedbackQueueScheduler {

    /** Ready queues, one per priority level (0 = highest priority). */
    private final List<Queue<ScheduledProcess>> readyQueues;

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
    public MultilevelFeedbackQueueScheduler(int numberOfLevels, int[] timeQuanta) {
        this.readyQueues = new ArrayList<>(numberOfLevels);
        for (int i = 0; i < numberOfLevels; i++) {
            readyQueues.add(new LinkedList<>());
        }
        this.timeQuanta = timeQuanta;
        this.currentTime = 0;
    }

    /**
     * Adds a process to the highest-priority queue.
     *
     * @param process process to be scheduled
     */
    public void addProcess(ScheduledProcess process) {
        readyQueues.get(0).add(process);
    }

    /**
     * Runs the scheduling simulation until all queues are empty.
     * Uses round-robin within each level and demotes unfinished processes
     * to the next lower-priority queue.
     */
    public void run() {
        while (!allQueuesEmpty()) {
            for (int level = 0; level < readyQueues.size(); level++) {
                Queue<ScheduledProcess> queue = readyQueues.get(level);
                if (queue.isEmpty()) {
                    continue;
                }

                ScheduledProcess process = queue.poll();
                int quantum = timeQuanta[level];

                int timeSlice = Math.min(quantum, process.remainingBurstTime);
                process.execute(timeSlice);
                currentTime += timeSlice;

                if (process.isFinished()) {
                    System.out.println("Process " + process.id + " finished at time " + currentTime);
                } else {
                    if (level < readyQueues.size() - 1) {
                        process.demotions++;
                        readyQueues.get(level + 1).add(process);
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
        for (Queue<ScheduledProcess> queue : readyQueues) {
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
    public int getCurrentTime() {
        return currentTime;
    }
}

/**
 * Represents a process in the scheduler.
 */
class ScheduledProcess {

    /** Process identifier. */
    int id;

    /** Original burst time. */
    int originalBurstTime;

    /** Remaining burst time. */
    int remainingBurstTime;

    /** Arrival time (unused in current implementation). */
    int arrivalTime;

    /** Number of times the process has been demoted. */
    int demotions;

    /**
     * Creates a process.
     *
     * @param id           process identifier
     * @param burstTime    total CPU burst time required
     * @param arrivalTime  arrival time of the process
     */
    ScheduledProcess(int id, int burstTime, int arrivalTime) {
        this.id = id;
        this.originalBurstTime = burstTime;
        this.remainingBurstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.demotions = 0;
    }

    /**
     * Executes the process for the given time slice.
     * Remaining time will not go below zero.
     *
     * @param timeSlice time to execute
     */
    public void execute(int timeSlice) {
        remainingBurstTime -= timeSlice;
        if (remainingBurstTime < 0) {
            remainingBurstTime = 0;
        }
    }

    /**
     * Checks whether the process has finished execution.
     *
     * @return true if remaining time is zero; false otherwise
     */
    public boolean isFinished() {
        return remainingBurstTime == 0;
    }
}