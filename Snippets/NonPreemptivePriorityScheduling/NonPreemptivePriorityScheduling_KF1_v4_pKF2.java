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
        // Prevent instantiation
    }

    /**
     * Represents a job in the scheduling algorithm.
     */
    static class Job implements Comparable<Job> {
        int id;
        int arrivalTime;
        int startTime;
        int burstTime;
        int priority; // lower value = higher priority

        Job(int id, int arrivalTime, int burstTime, int priority) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.startTime = -1;
            this.burstTime = burstTime;
            this.priority = priority;
        }

        /**
         * Order by priority, then by arrival time.
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
     * Non-preemptive priority scheduling.
     *
     * @param jobs jobs to schedule
     * @return jobs in execution order, with start times set
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
                // No job is ready; jump to the next arrival
                currentTime = arrivalQueue.peek().arrivalTime;
            }
        }

        return scheduledJobs;
    }

    /**
     * Average response time.
     * Response time = start time - arrival time.
     */
    public static double calculateAverageResponseTime(Job[] originalJobs, Job[] scheduledJobs) {
        int totalResponseTime = 0;

        for (Job job : scheduledJobs) {
            totalResponseTime += job.startTime - job.arrivalTime;
        }

        return (double) totalResponseTime / originalJobs.length;
    }

    /**
     * Average turnaround time.
     * Turnaround time = completion time - arrival time.
     */
    public static double calculateAverageTurnaroundTime(Job[] originalJobs, Job[] scheduledJobs) {
        int totalTurnaroundTime = 0;

        for (Job job : scheduledJobs) {
            int completionTime = job.startTime + job.burstTime;
            totalTurnaroundTime += completionTime - job.arrivalTime;
        }

        return (double) totalTurnaroundTime / originalJobs.length;
    }
}