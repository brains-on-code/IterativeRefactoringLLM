package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class for job scheduling algorithms.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Represents a job with an ID, a valid scheduling window, and a profit.
     */
    static class Class2 {
        /** Unique identifier for the job. */
        int jobId;

        /** Earliest time slot (1-based) in which the job can be scheduled. */
        int earliestSlot;

        /** Latest time slot (1-based, inclusive) by which the job must be scheduled. */
        int deadline;

        /** Profit earned if the job is scheduled. */
        int profit;

        Class2(int jobId, int earliestSlot, int deadline, int profit) {
            this.jobId = jobId;
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
    public static int[] method1(Class2[] jobs) {
        // Sort jobs by profit in descending order (greedy choice).
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        // Determine the latest deadline to know how many time slots are needed.
        int maxDeadline = Arrays.stream(jobs)
            .mapToInt(job -> job.deadline)
            .max()
            .orElse(0);

        // Time slots are 0-based internally; -1 indicates a free slot.
        int[] slots = new int[maxDeadline];
        Arrays.fill(slots, -1);

        int jobCount = 0;
        int totalProfit = 0;

        // Try to schedule each job in the latest possible free slot within its window.
        for (Class2 job : jobs) {
            if (job.earliestSlot > job.deadline) {
                continue; // Invalid window; cannot schedule this job.
            }

            int latestPossibleSlot = Math.min(job.deadline - 1, maxDeadline - 1);
            int earliestPossibleSlot = job.earliestSlot - 1;

            for (int slot = latestPossibleSlot; slot >= earliestPossibleSlot; slot--) {
                if (slots[slot] == -1) {
                    slots[slot] = job.jobId;
                    jobCount++;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        return new int[] {jobCount, totalProfit};
    }
}