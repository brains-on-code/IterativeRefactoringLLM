package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Utility class for non-preemptive priority scheduling algorithms.
 */
public final class NonPreemptivePriorityScheduler {

    private NonPreemptivePriorityScheduler() {
        // Utility class; prevent instantiation
    }

    /**
     * Represents a job in the scheduling algorithm.
     */
    static class Job implements Comparable<Job> {
        /** Unique job identifier. */
        int id;

        /** Time at which the job arrives. */
        int arrivalTime;

        /** Time at which the job starts executing (set by the scheduler). */
        int startTime;

        /** Execution time required by the job. */
        int burstTime;

        /** Job priority (lower value indicates higher priority). */
        int priority;

        /**
         * Creates a new job.
         *
         * @param id          job identifier
         * @param arrivalTime arrival time
         * @param burstTime   burst (execution) time
         * @param priority    job priority (lower is higher priority)
         */
        Job(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.startTime = -1;
            this.burstTime = burstTime;
            this.priority = priority;
        }

        /**
         * Compares jobs by priority, then by arrival time.
         *
         * @param other the other job to compare to
         * @return negative if this job has higher priority or earlier arrival,
         *         positive if lower priority or later arrival, zero if equal
         */
        @Override
        public int compareTo(Job other) {
            int priorityComparison = Integer.compare(this.priority, other.priority);
            if (priorityComparison != 0) {
                return priorityComparison;
            }
            return Integer.compare(this.arrivalTime, other.arrivalTime);
        }
    }

    /**
     * Schedules the given jobs using a non-preemptive priority scheduling algorithm.
     *
     * @param jobs array of jobs to schedule
     * @return array of jobs in the order they are executed, with start times set
     */
    public static Job[] schedule(Job[] jobs) {
        PriorityQueue<Job> readyQueue = new PriorityQueue<>();
        Queue<Job> arrivalQueue = new LinkedList<>();
        Job[] scheduledJobs = new Job[jobs.length];

        int currentTime = 0;
        int scheduledIndex = 0;

        Collections.addAll(arrivalQueue, jobs);

        while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()) {
            // Move all jobs that have arrived by currentTime into the ready queue
            while (!arrivalQueue.isEmpty() && arrivalQueue.peek().arrivalTime <= currentTime) {
                readyQueue.add(arrivalQueue.poll());
            }

            if (!readyQueue.isEmpty()) {
                Job job = readyQueue.poll();
                job.startTime = currentTime;
                scheduledJobs[scheduledIndex++] = job;
                currentTime += job.burstTime;
            } else {
                // No job is ready; advance time to the next job's arrival
                currentTime = arrivalQueue.peek().arrivalTime;
            }
        }

        return scheduledJobs;
    }

    /**
     * Calculates the average response time for the scheduled jobs.
     * Response time = start time - arrival time.
     *
     * @param originalJobs  original array of jobs
     * @param scheduledJobs scheduled jobs with start times set
     * @return average response time
     */
    public static double calculateAverageResponseTime(Job[] originalJobs, Job[] scheduledJobs) {
        int totalResponseTime = 0;

        for (Job job : scheduledJobs) {
            int responseTime = job.startTime - job.arrivalTime;
            totalResponseTime += responseTime;
        }

        return (double) totalResponseTime / originalJobs.length;
    }

    /**
     * Calculates the average turnaround time for the scheduled jobs.
     * Turnaround time = completion time - arrival time.
     *
     * @param originalJobs  original array of jobs
     * @param scheduledJobs scheduled jobs with start times set
     * @return average turnaround time
     */
    public static double calculateAverageTurnaroundTime(Job[] originalJobs, Job[] scheduledJobs) {
        int totalTurnaroundTime = 0;

        for (Job job : scheduledJobs) {
            int completionTime = job.startTime + job.burstTime;
            int turnaroundTime = completionTime - job.arrivalTime;
            totalTurnaroundTime += turnaroundTime;
        }

        return (double) totalTurnaroundTime / originalJobs.length;
    }
}