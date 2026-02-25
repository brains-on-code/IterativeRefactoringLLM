package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class JobSchedulingWithDeadline {

    private JobSchedulingWithDeadline() {
        // Utility class; prevent instantiation
    }

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

    public static int[] jobSequencingWithDeadlines(Job[] jobs) {
        if (jobs == null || jobs.length == 0) {
            return new int[] {0, 0};
        }

        Arrays.sort(jobs, Comparator.comparingInt(job -> job.profit).reversed());

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

            int latestPossibleSlot = Math.min(job.deadline, maxDeadline) - 1;
            int earliestPossibleSlot = Math.max(job.arrivalTime, 1) - 1;

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