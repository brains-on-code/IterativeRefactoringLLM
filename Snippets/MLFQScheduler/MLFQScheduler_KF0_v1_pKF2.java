package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Multi-Level Feedback Queue (MLFQ) scheduler.
 *
 * <p>Uses multiple ready queues with different time quanta. Processes start in
 * the highest-priority queue and may be demoted to lower-priority queues
 * depending on how much CPU time they consume.</p>
 */
public class MLFQScheduler {

    /** Ready queues ordered from highest (index 0) to lowest priority. */
    private final List<Queue<Process>> queues;

    /** Time quantum for each queue level; index corresponds to queue index. */
    private final int[] timeQuantum;

    /** Global time elapsed in the system. */
    private int currentTime;

    /**
     * Creates an MLFQ scheduler.
     *
     * @param levels       number of priority levels (queues)
     * @param timeQuantums time quantum for each level; length must be {@code levels}
     */
    public MLFQScheduler(int levels, int[] timeQuantums) {
        this.queues = new ArrayList<>(levels);
        for (int i = 0; i < levels; i++) {
            queues.add(new LinkedList<>());
        }
        this.timeQuantum = timeQuantums;
        this.currentTime = 0;
    }

    /**
     * Adds a process to the highest-priority queue.
     *
     * @param process process to schedule
     */
    public void addProcess(Process process) {
        queues.get(0).add(process);
    }

    /**
     * Runs the scheduler until all queues are empty.
     *
     * <p>At each step, the scheduler:
     * <ul>
     *   <li>selects the first non-empty queue (highest priority),</li>
     *   <li>executes the head process for up to that queue's time quantum,</li>
     *   <li>either completes the process or demotes it to a lower-priority queue.</li>
     * </ul>
     * </p>
     */
    public void run() {
        while (!allQueuesEmpty()) {
            for (int level = 0; level < queues.size(); level++) {
                Queue<Process> queue = queues.get(level);
                if (queue.isEmpty()) {
                    continue;
                }

                Process process = queue.poll();
                int quantum = timeQuantum[level];

                int timeSlice = Math.min(quantum, process.remainingTime);
                process.execute(timeSlice);
                currentTime += timeSlice;

                if (process.isFinished()) {
                    System.out.println("Process " + process.pid + " finished at time " + currentTime);
                } else {
                    boolean hasLowerLevel = level < queues.size() - 1;
                    if (hasLowerLevel) {
                        process.priority++;
                        queues.get(level + 1).add(process);
                    } else {
                        queue.add(process);
                    }
                }
            }
        }
    }

    /**
     * Returns {@code true} if all queues are empty.
     */
    private boolean allQueuesEmpty() {
        for (Queue<Process> queue : queues) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the total time elapsed in the system.
     */
    public int getCurrentTime() {
        return currentTime;
    }
}

/**
 * Process used by the MLFQ scheduler.
 */
class Process {

    /** Process identifier. */
    int pid;

    /** Total CPU burst time required. */
    int burstTime;

    /** Remaining CPU time. */
    int remainingTime;

    /** Time at which the process arrives in the system. */
    int arrivalTime;

    /** Current priority level (0 is highest). */
    int priority;

    /**
     * Creates a new process.
     *
     * @param pid         process ID
     * @param burstTime   total CPU burst time
     * @param arrivalTime arrival time of the process
     */
    Process(int pid, int burstTime, int arrivalTime) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = 0;
    }

    /**
     * Executes the process for the given time slice.
     *
     * @param timeSlice time to run
     */
    public void execute(int timeSlice) {
        remainingTime -= timeSlice;
        if (remainingTime < 0) {
            remainingTime = 0;
        }
    }

    /**
     * Returns {@code true} if the process has finished execution.
     */
    public boolean isFinished() {
        return remainingTime == 0;
    }
}