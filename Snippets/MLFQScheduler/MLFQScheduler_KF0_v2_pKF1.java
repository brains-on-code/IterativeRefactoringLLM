package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The Multi-Level Feedback Queue (MLFQ) Scheduler class.
 * This class simulates scheduling using multiple queues, where processes move
 * between queues depending on their CPU burst behavior.
 */
public class MLFQScheduler {
    private final List<Queue<ScheduledProcess>> priorityQueues; // Multi-level feedback queues
    private final int[] timeQuantaPerLevel; // Time quantum for each queue level
    private int currentTime; // Current time in the system

    /**
     * Constructor to initialize the MLFQ scheduler with the specified number of
     * levels and their corresponding time quantums.
     *
     * @param numberOfLevels Number of queues (priority levels)
     * @param timeQuanta     Time quantum for each queue level
     */
    public MLFQScheduler(int numberOfLevels, int[] timeQuanta) {
        this.priorityQueues = new ArrayList<>(numberOfLevels);
        for (int levelIndex = 0; levelIndex < numberOfLevels; levelIndex++) {
            priorityQueues.add(new LinkedList<>());
        }
        this.timeQuantaPerLevel = timeQuanta;
        this.currentTime = 0;
    }

    /**
     * Adds a new process to the highest priority queue (queue 0).
     *
     * @param process The process to be added to the scheduler
     */
    public void addProcess(ScheduledProcess process) {
        priorityQueues.get(0).add(process);
    }

    /**
     * Executes the scheduling process by running the processes in all queues,
     * promoting or demoting them based on their completion status and behavior.
     * The process continues until all queues are empty.
     */
    public void run() {
        while (!areAllQueuesEmpty()) {
            for (int queueLevel = 0; queueLevel < priorityQueues.size(); queueLevel++) {
                Queue<ScheduledProcess> currentQueue = priorityQueues.get(queueLevel);
                if (currentQueue.isEmpty()) {
                    continue;
                }

                ScheduledProcess currentProcess = currentQueue.poll();
                int timeQuantumForLevel = timeQuantaPerLevel[queueLevel];

                // Execute the process for the minimum of the time quantum or the remaining time
                int timeSlice = Math.min(timeQuantumForLevel, currentProcess.remainingTime);
                currentProcess.execute(timeSlice);
                currentTime += timeSlice; // Update the system's current time

                if (currentProcess.isFinished()) {
                    System.out.println(
                        "Process " + currentProcess.processId + " finished at time " + currentTime
                    );
                } else {
                    if (queueLevel < priorityQueues.size() - 1) {
                        currentProcess.priority++; // Demote the process to the next lower priority queue
                        priorityQueues.get(queueLevel + 1).add(currentProcess); // Add to the next queue level
                    } else {
                        currentQueue.add(currentProcess); // Stay in the same queue if it's the last level
                    }
                }
            }
        }
    }

    /**
     * Helper function to check if all the queues are empty (i.e., no process is
     * left to execute).
     *
     * @return true if all queues are empty, otherwise false
     */
    private boolean areAllQueuesEmpty() {
        for (Queue<ScheduledProcess> queue : priorityQueues) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the current time of the scheduler, which reflects the total time
     * elapsed during the execution of all processes.
     *
     * @return The current time in the system
     */
    public int getCurrentTime() {
        return currentTime;
    }
}

/**
 * Represents a process in the Multi-Level Feedback Queue (MLFQ) scheduling
 * algorithm.
 */
class ScheduledProcess {
    int processId;
    int burstTime;
    int remainingTime;
    int arrivalTime;
    int priority;

    /**
     * Constructor to initialize a new process.
     *
     * @param processId   Process ID
     * @param burstTime   CPU Burst Time (time required for the process)
     * @param arrivalTime Arrival time of the process
     */
    ScheduledProcess(int processId, int burstTime, int arrivalTime) {
        this.processId = processId;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = 0;
    }

    /**
     * Executes the process for a given time slice.
     *
     * @param timeSlice The amount of time the process is executed
     */
    public void execute(int timeSlice) {
        remainingTime -= timeSlice;
        if (remainingTime < 0) {
            remainingTime = 0;
        }
    }

    /**
     * Checks if the process has finished execution.
     *
     * @return true if the process is finished, otherwise false
     */
    public boolean isFinished() {
        return remainingTime == 0;
    }
}