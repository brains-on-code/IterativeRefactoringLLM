package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class JobSchedulingWithDeadline {

    private JobSchedulingWithDeadline() {}

    static class Job {
        final int jobId;
        final int arrivalTime;
        final int deadline;
        final int profit;

        Job(int jobId, int arrivalTime, int deadline, int profit) {
            this.jobId = jobId;
            this.arrivalTime = arrivalTime;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    /**
     * Schedules jobs to maximize total profit, respecting each job's arrival time and deadline.
     *
     * @param jobs array of jobs to schedule
     * @return array where index 0 is the number of scheduled jobs and index 1 is the total profit
     */
    public static int[] jobSequencingWithDeadlines(Job[] jobs) {
        if (jobs == null || jobs.length == 0) {
            return new int[] {0, 0};
        }

        // Sort jobs in descending order of profit so that more profitable jobs are scheduled first.
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        // Determine the maximum deadline to know how many time slots are needed.
        int maxDeadline =
            Arrays.stream(jobs).mapToInt(job -> job.deadline).max().orElse(0);

        // timeSlots[i] holds the jobId scheduled at time (i + 1); -1 means the slot is free.
        int[] timeSlots = new int[maxDeadline];
        Arrays.fill(timeSlots, -1);

        int scheduledJobs = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            // Ignore jobs whose arrival time is after their deadline; they can never be scheduled.
            if (job.arrivalTime > job.deadline) {
                continue;
            }

            // Convert 1-based arrival/deadline to 0-based indices for the timeSlots array.
            int latestPossibleSlot = Math.min(job.deadline - 1, maxDeadline - 1);
            int earliestPossibleSlot = Math.max(0, job.arrivalTime - 1);

            // Try to place the job in the latest available slot within its feasible window.
            for (int slot = latestPossibleSlot; slot >= earliestPossibleSlot; slot--) {
                if (timeSlots[slot] == -1) {
                    timeSlots[slot] = job.jobId;
                    scheduledJobs++;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        return new int[] {scheduledJobs, totalProfit};
    }
}