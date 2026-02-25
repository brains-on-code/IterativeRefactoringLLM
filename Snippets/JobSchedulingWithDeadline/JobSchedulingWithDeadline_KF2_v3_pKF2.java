package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class JobSchedulingWithDeadline {

    private JobSchedulingWithDeadline() {
        // Utility class; prevent instantiation
    }

    /**
     * Represents a job with an ID, arrival time, deadline, and profit.
     */
    static class Job {
        int jobId;
        int arrivalTime;
        int deadline;
        int profit;

        Job(int jobId, int arrivalTime, int deadline, int profit) {
            this.jobId = jobId;
            this.arrivalTime = arrivalTime;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    /**
     * Schedules jobs to maximize profit given arrival times and deadlines.
     *
     * @param jobs array of jobs to schedule
     * @return an array where:
     *         index 0 = number of jobs scheduled,
     *         index 1 = total profit from scheduled jobs
     */
    public static int[] jobSequencingWithDeadlines(Job[] jobs) {
        if (jobs == null || jobs.length == 0) {
            return new int[] {0, 0};
        }

        // Sort jobs by profit in descending order
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        // Maximum deadline determines the number of available time slots
        int maxDeadline =
            Arrays.stream(jobs).mapToInt(job -> job.deadline).max().orElse(0);

        // timeSlots[i] stores the jobId scheduled at time (i + 1); -1 means the slot is free
        int[] timeSlots = new int[maxDeadline];
        Arrays.fill(timeSlots, -1);

        int scheduledJobsCount = 0;
        int totalProfit = 0;

        // Try to schedule each job in the latest possible free slot within its time window
        for (Job job : jobs) {
            if (job.arrivalTime > job.deadline) {
                continue;
            }

            int latestPossibleSlot = Math.min(job.deadline - 1, maxDeadline - 1);
            int earliestPossibleSlot = Math.max(0, job.arrivalTime - 1);

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