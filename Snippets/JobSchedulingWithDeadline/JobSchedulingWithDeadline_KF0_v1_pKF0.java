package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A class that implements a job scheduling algorithm to maximize profit
 * while adhering to job deadlines and arrival times.
 *
 * This class provides functionality to schedule jobs based on their profit,
 * arrival time, and deadlines to ensure that the maximum number of jobs is completed
 * within the given timeframe. It sorts the jobs in decreasing order of profit
 * and attempts to assign them to the latest possible time slots.
 */
public final class JobSchedulingWithDeadline {

    private JobSchedulingWithDeadline() {
        // Utility class; prevent instantiation
    }

    /**
     * Represents a job with an ID, arrival time, deadline, and profit.
     *
     * Each job has a unique identifier, an arrival time (when it becomes available for scheduling),
     * a deadline by which it must be completed, and a profit associated with completing the job.
     */
    static class Job {
        final int jobId;
        final int arrivalTime;
        final int deadline;
        final int profit;

        /**
         * Constructs a Job instance with the specified job ID, arrival time, deadline, and profit.
         *
         * @param jobId       Unique identifier for the job
         * @param arrivalTime Time when the job becomes available for scheduling
         * @param deadline    Deadline for completing the job
         * @param profit      Profit earned upon completing the job
         */
        Job(int jobId, int arrivalTime, int deadline, int profit) {
            this.jobId = jobId;
            this.arrivalTime = arrivalTime;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    /**
     * Schedules jobs to maximize profit while respecting their deadlines and arrival times.
     *
     * This method sorts the jobs in descending order of profit and attempts
     * to allocate them to time slots that are before or on their deadlines,
     * provided they have arrived. The function returns an array where the first element
     * is the total number of jobs scheduled and the second element is the total profit earned.
     *
     * @param jobs An array of Job objects, each representing a job with an ID, arrival time,
     *             deadline, and profit.
     * @return An array of two integers: the first element is the count of jobs
     *         that were successfully scheduled, and the second element is the
     *         total profit earned from those jobs.
     */
    public static int[] jobSequencingWithDeadlines(Job[] jobs) {
        if (jobs == null || jobs.length == 0) {
            return new int[] {0, 0};
        }

        // Sort jobs by profit in descending order
        Arrays.sort(jobs, Comparator.comparingInt((Job job) -> job.profit).reversed());

        int maxDeadline = Arrays.stream(jobs)
            .mapToInt(job -> job.deadline)
            .max()
            .orElse(0);

        if (maxDeadline <= 0) {
            return new int[] {0, 0};
        }

        int[] timeSlots = new int[maxDeadline];
        Arrays.fill(timeSlots, -1);

        int scheduledJobsCount = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (job.arrivalTime > job.deadline) {
                continue;
            }

            int latestPossibleSlot = Math.min(job.deadline - 1, maxDeadline - 1);
            int earliestPossibleSlot = Math.max(job.arrivalTime - 1, 0);

            for (int slot = latestPossibleSlot; slot >= earliestPossibleSlot; slot--) {
                if (timeSlots[slot] == -1) {
                    timeSlots[slot] = job.jobId;
                    scheduledJobsCount++;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        return new int[] {scheduledJobsCount, totalProfit};
    }
}