package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class JobSchedulingWithDeadline {

    private JobSchedulingWithDeadline() {
    }

    static class Job {
        int id;
        int startTime;
        int deadline;
        int profit;

        Job(int id, int startTime, int deadline, int profit) {
            this.id = id;
            this.startTime = startTime;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    public static int[] scheduleJobsWithDeadlines(Job[] jobs) {
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        int maxDeadline = Arrays.stream(jobs)
            .mapToInt(job -> job.deadline)
            .max()
            .orElse(0);

        int[] scheduledJobIds = new int[maxDeadline];
        Arrays.fill(scheduledJobIds, -1);

        int scheduledJobCount = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (job.startTime <= job.deadline) {
                int lastPossibleSlot = Math.min(job.deadline - 1, maxDeadline - 1);
                for (int slotIndex = lastPossibleSlot; slotIndex >= job.startTime - 1; slotIndex--) {
                    if (scheduledJobIds[slotIndex] == -1) {
                        scheduledJobIds[slotIndex] = job.id;
                        scheduledJobCount++;
                        totalProfit += job.profit;
                        break;
                    }
                }
            }
        }

        return new int[] {scheduledJobCount, totalProfit};
    }
}