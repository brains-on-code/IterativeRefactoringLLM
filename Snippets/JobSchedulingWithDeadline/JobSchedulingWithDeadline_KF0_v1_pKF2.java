package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class JobSchedulingWithDeadline {

    private JobSchedulingWithDeadline() {
        // Utility class; prevent instantiation
    }

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

    public static int[] jobSequencingWithDeadlines(Job[] jobs) {
        // Sort jobs by profit in descending order
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        int maxDeadline = Arrays.stream(jobs)
            .mapToInt(job -> job.deadline)
            .max()
            .orElse(0);

        // timeSlots[i] holds the jobId scheduled at time i (0-based); -1 means free
        int[] timeSlots = new int[maxDeadline];
        Arrays.fill(timeSlots, -1);

        int scheduledJobs = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (job.arrivalTime > job.deadline) {
                continue;
            }

            int latestPossibleSlot = Math.min(job.deadline - 1, maxDeadline - 1);
            int earliestPossibleSlot = job.arrivalTime - 1;

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