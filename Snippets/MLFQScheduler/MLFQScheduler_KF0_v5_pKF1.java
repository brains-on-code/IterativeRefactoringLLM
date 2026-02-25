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
    private final List<Queue<ScheduledProcess>> feedbackQueues; // Multi-level feedback queues
    private final int[] timeQuantaByLevel; // Time quantum for each queue level
    private int currentTime; // Current time in the system

    /**
     * Constructor to initialize the MLFQ scheduler with the specified number of
     * levels and their corresponding time quantums.
     *
     * @param numberOfLevels Number of queues (priority levels)
     * @param timeQuanta     Time quantum for each queue level
     */
    public MLFQScheduler(int numberOfLevels, int[] timeQuanta) {
        this.feedbackQueues = new ArrayList<>(numberOfLevels);
        for (int levelIndex = 0; levelIndex < numberOfLevels; levelIndex++) {
            feedbackQueues.add(new LinkedList<>());
        }
        this.timeQuantaByLevel = timeQuanta;
        this.currentTime = 0;
    }

    /**
     * Adds a new process to the highest priority queue (queue 0).
     *
     * @param process The process to be added to the scheduler
     */
    public void addProcess(ScheduledProcess process) {
        feedbackQueues.get(0).add(process);
    }

    /**
     * Executes the scheduling process by running the processes in all queues,
     * promoting or demoting them based on their completion status and behavior.
     * The process continues until all queues are empty.
     */
    public void run() {
        while (!areAllQueuesEmpty()) {
            for (int levelIndex = 0; levelIndex < feedbackQueues.size(); levelIndex++) {
                Queue<ScheduledProcess> queueAtCurrentLevel = feedbackQueues.get(levelIndex);
                if (queueAtCurrentLevel.isEmpty()) {
                    continue;
                }

                ScheduledProcess currentProcess = queueAtCurrentLevel.poll();
                int timeQuantumForLevel = timeQuantaByLevel[levelIndex];

                // Execute the process for the minimum of the time quantum or the remaining time
                int timeSlice = Math.min(timeQuantumForLevel, currentProcess.remainingTime);
                currentProcess.execute(timeSlice);
                currentTime += timeSlice; // Update the system's current time

                if (currentProcess.isFinished()) {
                    System.out.println(
                        "Process " + currentProcess.processId + " finished at time " + currentTime
                    );
                } else {
                    if (levelIndex < feedbackQueues.size() - 1) {
                        currentProcess.priority++; // Demote the process to the next lower priority queue
                        feedbackQueues.get(levelIndex + 1).add(currentProcess); // Add to the next queue level
                    } else {
                        queueAtCurrentLevel.add(currentProcess); // Stay in the same queue if it's the last level
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
        for (Queue<ScheduledProcess> processQueue : feedbackQueues) {
            if (!processQueue.isEmpty()) {
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