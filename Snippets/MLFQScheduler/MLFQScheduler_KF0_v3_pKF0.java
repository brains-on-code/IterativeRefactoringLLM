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

    private final List<Queue<Process>> queues; // Multi-level feedback queues
    private final int[] timeQuantums; // Time quantum for each queue level
    private int currentTime; // Current time in the system

    /**
     * Constructor to initialize the MLFQ scheduler with the specified number of
     * levels and their corresponding time quantums.
     *
     * @param levels       Number of queues (priority levels)
     * @param timeQuantums Time quantum for each queue level
     */
    public MLFQScheduler(int levels, int[] timeQuantums) {
        this.queues = new ArrayList<>(levels);
        for (int i = 0; i < levels; i++) {
            this.queues.add(new LinkedList<>());
        }
        this.timeQuantums = timeQuantums.clone();
        this.currentTime = 0;
    }

    /**
     * Adds a new process to the highest priority queue (queue 0).
     *
     * @param process The process to be added to the scheduler
     */
    public void addProcess(Process process) {
        if (process == null) {
            return;
        }
        queues.get(0).offer(process);
    }

    /**
     * Executes the scheduling process by running the processes in all queues,
     * promoting or demoting them based on their completion status and behavior.
     * The process continues until all queues are empty.
     */
    public void run() {
        while (!areAllQueuesEmpty()) {
            for (int level = 0; level < queues.size(); level++) {
                executeQueueLevel(level);
            }
        }
    }

    private void executeQueueLevel(int level) {
        Queue<Process> currentQueue = queues.get(level);
        if (currentQueue.isEmpty()) {
            return;
        }

        Process process = currentQueue.poll();
        if (process == null) {
            return;
        }

        int quantum = timeQuantums[level];
        int timeSlice = Math.min(quantum, process.getRemainingTime());

        process.execute(timeSlice);
        currentTime += timeSlice;

        if (process.isFinished()) {
            System.out.println(
                "Process " + process.getPid() + " finished at time " + currentTime
            );
        } else {
            moveProcessToNextQueue(level, process);
        }
    }

    private void moveProcessToNextQueue(int currentLevel, Process process) {
        boolean isLastLevel = currentLevel == queues.size() - 1;

        if (!isLastLevel) {
            process.incrementPriority();
            queues.get(currentLevel + 1).offer(process);
        } else {
            queues.get(currentLevel).offer(process);
        }
    }

    /**
     * Helper function to check if all the queues are empty (i.e., no process is
     * left to execute).
     *
     * @return true if all queues are empty, otherwise false
     */
    private boolean areAllQueuesEmpty() {
        for (Queue<Process> queue : queues) {
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
class Process {

    private final int pid;
    private final int burstTime;
    private final int arrivalTime;
    private int remainingTime;
    private int priority;

    /**
     * Constructor to initialize a new process.
     *
     * @param pid         Process ID
     * @param burstTime   CPU Burst Time (time required for the process)
     * @param arrivalTime Arrival time of the process
     */
    Process(int pid, int burstTime, int arrivalTime) {
        this.pid = pid;
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
        remainingTime = Math.max(0, remainingTime - timeSlice);
    }

    /**
     * Checks if the process has finished execution.
     *
     * @return true if the process is finished, otherwise false
     */
    public boolean isFinished() {
        return remainingTime == 0;
    }

    public int getPid() {
        return pid;
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

    public int getPriority() {
        return priority;
    }

    public void incrementPriority() {
        priority++;
    }
}