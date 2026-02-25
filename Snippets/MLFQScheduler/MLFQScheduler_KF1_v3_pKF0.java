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
        if (process == null) {
            return;
        }
        priorityQueues.get(0).offer(process);
    }

    /** Run the scheduler until all queues are empty. */
    public void run() {
        while (!areAllQueuesEmpty()) {
            for (int level = 0; level < priorityQueues.size(); level++) {
                Queue<ScheduledProcess> queue = priorityQueues.get(level);
                if (queue.isEmpty()) {
                    continue;
                }

                ScheduledProcess process = queue.poll();
                if (process == null) {
                    continue;
                }

                int quantum = timeQuanta[level];
                executeProcessAtLevel(process, level, quantum);
            }
        }
    }

    private void executeProcessAtLevel(ScheduledProcess process, int level, int quantum) {
        int timeSlice = Math.min(quantum, process.getRemainingTime());
        process.execute(timeSlice);
        currentTime += timeSlice;

        if (process.isFinished()) {
            System.out.println("Process " + process.getId() + " finished at time " + currentTime);
            return;
        }

        if (level < priorityQueues.size() - 1) {
            process.incrementQueueLevel();
            priorityQueues.get(level + 1).offer(process);
        } else {
            priorityQueues.get(level).offer(process);
        }
    }

    /** Check if all queues are empty. */
    private boolean areAllQueuesEmpty() {
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

    private final int id;
    private final int burstTime;
    private final int arrivalTime;
    private int remainingTime;
    private int currentQueueLevel;

    ScheduledProcess(int id, int burstTime, int arrivalTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.currentQueueLevel = 0;
    }

    /** Execute the process for a given time slice. */
    public void execute(int timeSlice) {
        remainingTime = Math.max(0, remainingTime - timeSlice);
    }

    /** Check if the process has finished execution. */
    public boolean isFinished() {
        return remainingTime == 0;
    }

    public int getId() {
        return id;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getCurrentQueueLevel() {
        return currentQueueLevel;
    }

    public void incrementQueueLevel() {
        currentQueueLevel++;
    }
}