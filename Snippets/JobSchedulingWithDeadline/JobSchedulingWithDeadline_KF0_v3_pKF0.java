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

        sortJobsByProfitDescending(jobs);

        int maxDeadline = findMaxDeadline(jobs);
        if (maxDeadline <= 0) {
            return new int[] {0, 0};
        }

        int[] timeSlots = initializeTimeSlots(maxDeadline);

        int scheduledJobsCount = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (!isJobFeasible(job)) {
                continue;
            }

            int latestPossibleSlot = getLatestPossibleSlot(job, maxDeadline);
            int earliestPossibleSlot = getEarliestPossibleSlot(job);

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

    private static void sortJobsByProfitDescending(Job[] jobs) {
        Arrays.sort(jobs, Comparator.comparingInt(job -> job.profit).reversed());
    }

    private static int findMaxDeadline(Job[] jobs) {
        return Arrays.stream(jobs)
            .mapToInt(job -> job.deadline)
            .max()
            .orElse(0);
    }

    private static int[] initializeTimeSlots(int maxDeadline) {
        int[] timeSlots = new int[maxDeadline];
        Arrays.fill(timeSlots, -1);
        return timeSlots;
    }

    private static boolean isJobFeasible(Job job) {
        return job.arrivalTime <= job.deadline;
    }

    private static int getLatestPossibleSlot(Job job, int maxDeadline) {
        return Math.min(job.deadline, maxDeadline) - 1;
    }

    private static int getEarliestPossibleSlot(Job job) {
        return Math.max(job.arrivalTime, 1) - 1;
    }
}