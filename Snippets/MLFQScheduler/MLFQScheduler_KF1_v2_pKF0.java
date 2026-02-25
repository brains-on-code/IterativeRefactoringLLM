package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Multilevel feedback queue scheduler.
 */
public class MultilevelFeedbackQueueScheduler {

    /** Queues for each priority level. */
    private final List<Queue<ScheduledProcess>> priorityQueues;

    /** Time quantum for each priority level. */
    private final int[] timeQuanta;

    /** Total elapsed time. */
    private int currentTime;

    /**
     * @param numberOfLevels number of priority levels
     * @param timeQuanta     time quantum for each level
     */
    public MultilevelFeedbackQueueScheduler(int numberOfLevels, int[] timeQuanta) {
        this.priorityQueues = new ArrayList<>(numberOfLevels);
        for (int i = 0; i < numberOfLevels; i++) {
            this.priorityQueues.add(new LinkedList<>());
        }
        this.timeQuanta = timeQuanta;
        this.currentTime = 0;
    }

    /** Add a process to the highest-priority queue. */
    public void addProcess(ScheduledProcess process) {
        priorityQueues.get(0).add(process);
    }

    /** Run the scheduler until all queues are empty. */
    public void run() {
        while (!allQueuesEmpty()) {
            for (int level = 0; level < priorityQueues.size(); level++) {
                Queue<ScheduledProcess> queue = priorityQueues.get(level);
                if (queue.isEmpty()) {
                    continue;
                }

                ScheduledProcess process = queue.poll();
                int quantum = timeQuanta[level];

                int timeSlice = Math.min(quantum, process.remainingTime);
                process.execute(timeSlice);
                currentTime += timeSlice;

                if (process.isFinished()) {
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
        for (Queue<ScheduledProcess> queue : priorityQueues) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /** Get total elapsed time. */
    public int getCurrentTime() {
        return currentTime;
    }
}

/** Represents a process in the scheduler. */
class ScheduledProcess {

    int id;
    int burstTime;
    int remainingTime;
    int arrivalTime;
    int currentQueueLevel;

    ScheduledProcess(int id, int burstTime, int arrivalTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.currentQueueLevel = 0;
    }

    /** Execute the process for a given time slice. */
    public void execute(int timeSlice) {
        remainingTime -= timeSlice;
        if (remainingTime < 0) {
            remainingTime = 0;
        }
    }

    /** Check if the process has finished execution. */
    public boolean isFinished() {
        return remainingTime == 0;
    }
}