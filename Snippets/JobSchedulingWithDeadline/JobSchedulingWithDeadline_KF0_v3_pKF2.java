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
     * Schedules jobs to maximize total profit subject to arrival times and deadlines.
     *
     * @param jobs array of jobs to schedule
     * @return int[0] = number of scheduled jobs, int[1] = total profit
     */
    public static int[] jobSequencingWithDeadlines(Job[] jobs) {
        if (jobs == null || jobs.length == 0) {
            return new int[] {0, 0};
        }

        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        int maxDeadline =
            Arrays.stream(jobs).mapToInt(job -> job.deadline).max().orElse(0);

        int[] timeSlots = new int[maxDeadline];
        Arrays.fill(timeSlots, -1);

        int scheduledJobs = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (job.arrivalTime > job.deadline) {
                continue;
            }

            int latestPossibleSlot = Math.min(job.deadline - 1, maxDeadline - 1);
            int earliestPossibleSlot = Math.max(0, job.arrivalTime - 1);

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