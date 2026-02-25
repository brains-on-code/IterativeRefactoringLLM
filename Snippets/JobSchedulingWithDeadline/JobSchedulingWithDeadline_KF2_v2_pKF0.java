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

        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

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

            int assignedSlot = findAvailableSlot(timeSlots, earliestPossibleSlot, latestPossibleSlot);
            if (assignedSlot != -1) {
                timeSlots[assignedSlot] = job.jobId;
                scheduledJobsCount++;
                totalProfit += job.profit;
            }
        }

        return new int[] {scheduledJobsCount, totalProfit};
    }

    private static int findAvailableSlot(int[] timeSlots, int earliestSlot, int latestSlot) {
        for (int slot = latestSlot; slot >= earliestSlot; slot--) {
            if (timeSlots[slot] == -1) {
                return slot;
            }
        }
        return -1;
    }
}