package com.thealgorithms.scheduling;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Utility class for non-preemptive scheduling algorithms.
 */
public final class NonPreemptivePriorityScheduler {

    private NonPreemptivePriorityScheduler() {
        // Prevent instantiation
    }

    /**
     * Represents a task/job in the scheduling algorithm.
     */
    static class Job implements Comparable<Job> {
        /** Job ID. */
        int id;

        /** Arrival time of the job. */
        int arrivalTime;

        /** Start time of the job (set by the scheduler). */
        int startTime;

        /** Burst time (execution time) of the job. */
        int burstTime;

        /** Priority of the job (lower value means higher priority). */
        int priority;

        /**
         * Creates a new job.
         *
         * @param id          job ID
         * @param arrivalTime arrival time
         * @param burstTime   burst time
         * @param priority    priority
         */
        Job(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.startTime = -1;
            this.burstTime = burstTime;
            this.priority = priority;
        }

        /**
         * Compares jobs first by priority, then by arrival time.
         *
         * @param other other job
         * @return comparison result
         */
        @Override
        public int compareTo(Job other) {
            if (this.priority == other.priority) {
                return Integer.compare(this.arrivalTime, other.arrivalTime);
            }
            return Integer.compare(this.priority, other.priority);
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
        int currentTime = 0;
        int index = 0;
        Job[] scheduledJobs = new Job[jobs.length];

        Collections.addAll(arrivalQueue, jobs);

        while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()) {
            // Move all jobs that have arrived by currentTime into the ready queue
            while (!arrivalQueue.isEmpty() && arrivalQueue.peek().arrivalTime <= currentTime) {
                readyQueue.add(arrivalQueue.poll());
            }

            if (!readyQueue.isEmpty()) {
                Job job = readyQueue.poll();
                job.startTime = currentTime;
                scheduledJobs[index++] = job;
                currentTime += job.burstTime;
            } else {
                // If no job is ready, jump to the next job's arrival time
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