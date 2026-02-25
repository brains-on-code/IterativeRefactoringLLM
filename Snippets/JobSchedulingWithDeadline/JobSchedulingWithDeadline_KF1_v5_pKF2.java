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
        int id;
        int earliestSlot; // 1-based inclusive
        int deadline;     // 1-based inclusive
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
        if (jobs == null || jobs.length == 0) {
            return new int[] {0, 0};
        }

        sortJobsByProfitDescending(jobs);

        int maxDeadline = findMaxDeadline(jobs);
        if (maxDeadline == 0) {
            return new int[] {0, 0};
        }

        int[] slots = createEmptySlots(maxDeadline);

        int scheduledJobs = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (!hasValidWindow(job)) {
                continue;
            }

            int assignedSlot = findAvailableSlot(job, slots, maxDeadline);
            if (assignedSlot != -1) {
                slots[assignedSlot] = job.id;
                scheduledJobs++;
                totalProfit += job.profit;
            }
        }

        return new int[] {scheduledJobs, totalProfit};
    }

    private static void sortJobsByProfitDescending(Job[] jobs) {
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));
    }

    private static int findMaxDeadline(Job[] jobs) {
        return Arrays.stream(jobs)
            .mapToInt(job -> job.deadline)
            .max()
            .orElse(0);
    }

    /**
     * Creates an array of time slots.
     *
     * Slots are 0-based internally; a value of -1 indicates a free slot.
     */
    private static int[] createEmptySlots(int maxDeadline) {
        int[] slots = new int[maxDeadline];
        Arrays.fill(slots, -1);
        return slots;
    }

    private static boolean hasValidWindow(Job job) {
        return job.earliestSlot <= job.deadline;
    }

    /**
     * Finds the latest available slot for the given job within its valid window.
     *
     * @param job         the job to schedule
     * @param slots       the time slots array
     * @param maxDeadline the maximum deadline across all jobs
     * @return the index of the assigned slot, or -1 if no slot is available
     */
    private static int findAvailableSlot(Job job, int[] slots, int maxDeadline) {
        int latestPossibleSlot = Math.min(job.deadline - 1, maxDeadline - 1);
        int earliestPossibleSlot = job.earliestSlot - 1;

        for (int slot = latestPossibleSlot; slot >= earliestPossibleSlot; slot--) {
            if (slots[slot] == -1) {
                return slot;
            }
        }
        return -1;
    }
}