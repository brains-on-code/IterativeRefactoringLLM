package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class for job scheduling algorithms.
 */
public final class JobScheduler {

    private JobScheduler() {
        // Prevent instantiation
    }

    /**
     * Represents a job with an ID, a valid scheduling window, and a profit.
     */
    static class Job {
        /** Unique identifier for the job. */
        int id;

        /** Earliest time slot (1-based) in which the job can be scheduled. */
        int earliestSlot;

        /** Latest time slot (1-based, inclusive) by which the job must be scheduled. */
        int deadline;

        /** Profit earned if the job is scheduled. */
        int profit;

        Job(int id, int earliestSlot, int deadline, int profit) {
            this.id = id;
            this.earliestSlot = earliestSlot;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    /**
     * Schedules jobs to maximize total profit subject to their time windows.
     *
     * Each job can be scheduled in exactly one time slot between earliestSlot and
     * deadline (both inclusive). At most one job can occupy a given time slot.
     *
     * @param jobs array of jobs to schedule
     * @return an array of two integers:
     *         index 0 = number of jobs scheduled
     *         index 1 = total profit from scheduled jobs
     */
    public static int[] scheduleJobs(Job[] jobs) {
        // Sort jobs by profit in descending order.
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        // Find the maximum deadline to determine the number of time slots.
        int maxDeadline = Arrays.stream(jobs)
            .mapToInt(job -> job.deadline)
            .max()
            .orElse(0);

        // Time slots are 0-based internally; -1 indicates a free slot.
        int[] slots = new int[maxDeadline];
        Arrays.fill(slots, -1);

        int scheduledJobs = 0;
        int totalProfit = 0;

        // For each job, try to place it in the latest available slot within its window.
        for (Job job : jobs) {
            if (job.earliestSlot > job.deadline) {
                // Invalid window; cannot schedule this job.
                continue;
            }

            int latestPossibleSlot = Math.min(job.deadline - 1, maxDeadline - 1);
            int earliestPossibleSlot = job.earliestSlot - 1;

            for (int slot = latestPossibleSlot; slot >= earliestPossibleSlot; slot--) {
                if (slots[slot] == -1) {
                    slots[slot] = job.id;
                    scheduledJobs++;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        return new int[] {scheduledJobs, totalProfit};
    }
}