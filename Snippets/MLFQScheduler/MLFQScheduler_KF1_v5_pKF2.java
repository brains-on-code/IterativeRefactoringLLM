package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Multilevel Feedback Queue (MLFQ) scheduler.
 *
 * <p>Each priority level has its own ready queue and time quantum. Processes
 * start in the highest-priority queue (level 0). If a process does not finish
 * within its time quantum, it is moved to the next lower-priority queue.
 */
public class MultilevelFeedbackQueueScheduler {

    /** Ready queues indexed by priority level (0 = highest priority). */
    private final List<Queue<ScheduledProcess>> readyQueues;

    /** Time quantum for each priority level; index corresponds to level. */
    private final int[] timeQuanta;

    /** Total elapsed time in the scheduler. */
    private int currentTime;

    /**
     * Constructs a multilevel feedback queue scheduler.
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
     * Adds a process to the highest-priority queue (level 0).
     *
     * @param process process to be scheduled
     */
    public void addProcess(ScheduledProcess process) {
        readyQueues.get(0).add(process);
    }

    /**
     * Runs the scheduling simulation until all queues are empty.
     *
     * <p>Within each priority level, processes are scheduled in round-robin
     * fashion. Unfinished processes are demoted to the next lower-priority
     * queue after using their time quantum.
     */
    public void run() {
        while (!allQueuesEmpty()) {
            for (int level = 0; level < readyQueues.size(); level++) {
                processLevel(level);
            }
        }
    }

    /**
     * Executes one scheduling step for a single priority level.
     *
     * @param level the priority level to process
     */
    private void processLevel(int level) {
        Queue<ScheduledProcess> queue = readyQueues.get(level);
        if (queue.isEmpty()) {
            return;
        }

        ScheduledProcess process = queue.poll();
        int quantum = timeQuanta[level];

        int timeSlice = Math.min(quantum, process.getRemainingBurstTime());
        process.execute(timeSlice);
        currentTime += timeSlice;

        if (process.isFinished()) {
            System.out.println("Process " + process.getId() + " finished at time " + currentTime);
        } else {
            handleUnfinishedProcess(process, level, queue);
        }
    }

    /**
     * Handles a process that has not finished after its time slice.
     *
     * @param process the unfinished process
     * @param level   the current priority level
     * @param queue   the current queue
     */
    private void handleUnfinishedProcess(ScheduledProcess process, int level, Queue<ScheduledProcess> queue) {
        boolean hasLowerPriorityLevel = level < readyQueues.size() - 1;

        if (hasLowerPriorityLevel) {
            process.incrementDemotions();
            readyQueues.get(level + 1).add(process);
        } else {
            queue.add(process);
        }
    }

    /**
     * Returns {@code true} if all ready queues are empty.
     *
     * @return {@code true} if all queues are empty; {@code false} otherwise
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
 * Represents a process managed by the scheduler.
 */
class ScheduledProcess {

    /** Unique process identifier. */
    private final int id;

    /** Initial CPU burst time requested by the process. */
    private final int originalBurstTime;

    /** Remaining CPU burst time. */
    private int remainingBurstTime;

    /** Arrival time of the process (not used in current scheduling logic). */
    private final int arrivalTime;

    /** Number of times the process has been demoted to a lower-priority queue. */
    private int demotions;

    /**
     * Constructs a scheduled process.
     *
     * @param id          process identifier
     * @param burstTime   total CPU burst time required
     * @param arrivalTime arrival time of the process
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
     * Remaining time is clamped at zero.
     *
     * @param timeSlice time to execute
     */
    public void execute(int timeSlice) {
        remainingBurstTime = Math.max(0, remainingBurstTime - timeSlice);
    }

    /**
     * Returns {@code true} if the process has finished execution.
     *
     * @return {@code true} if remaining time is zero; {@code false} otherwise
     */
    public boolean isFinished() {
        return remainingBurstTime == 0;
    }

    public int getId() {
        return id;
    }

    public int getOriginalBurstTime() {
        return originalBurstTime;
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getDemotions() {
        return demotions;
    }

    /** Increments the demotion counter for this process. */
    public void incrementDemotions() {
        demotions++;
    }
}