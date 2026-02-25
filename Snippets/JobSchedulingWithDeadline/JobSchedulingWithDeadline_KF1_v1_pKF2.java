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
     * Represents a job with an ID, deadline range, maximum deadline, and profit.
     */
    static class Class2 {
        int jobId;          // Unique identifier for the job
        int earliestSlot;   // Earliest time slot the job can be scheduled
        int deadline;       // Latest time slot (deadline) for the job
        int profit;         // Profit if the job is completed

        Class2(int jobId, int earliestSlot, int deadline, int profit) {
            this.jobId = jobId;
            this.earliestSlot = earliestSlot;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    /**
     * Schedules jobs to maximize total profit subject to their time constraints.
     *
     * Each job can be scheduled in any single time slot between its earliestSlot
     * and deadline (inclusive). At most one job can be scheduled in each time slot.
     *
     * @param jobs array of jobs to schedule
     * @return an array of two integers:
     *         [0] = number of jobs scheduled
     *         [1] = total profit from scheduled jobs
     */
    public static int[] method1(Class2[] jobs) {
        // Sort jobs in descending order of profit
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        // Find the maximum deadline among all jobs
        int maxDeadline = Arrays.stream(jobs).mapToInt(job -> job.deadline).max().orElse(0);

        // Time slots, initialized to -1 (indicating free)
        int[] slots = new int[maxDeadline];
        Arrays.fill(slots, -1);

        int jobCount = 0;
        int totalProfit = 0;

        // Greedy scheduling: pick the most profitable jobs first
        for (Class2 job : jobs) {
            if (job.earliestSlot <= job.deadline) {
                // Try to schedule the job in the latest possible free slot
                for (int slot = Math.min(job.deadline - 1, maxDeadline - 1);
                     slot >= job.earliestSlot - 1;
                     slot--) {

                    if (slots[slot] == -1) {
                        slots[slot] = job.jobId;
                        jobCount++;
                        totalProfit += job.profit;
                        break;
                    }
                }
            }
        }

        return new int[] {jobCount, totalProfit};
    }
}