package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class JobSchedulingWithDeadline {

    private JobSchedulingWithDeadline() {
    }

    static class Job {
        int jobId;
        int startTime;
        int deadline;
        int profit;

        Job(int jobId, int startTime, int deadline, int profit) {
            this.jobId = jobId;
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

        int[] scheduledJobIdsBySlot = new int[maxDeadline];
        Arrays.fill(scheduledJobIdsBySlot, -1);

        int numberOfScheduledJobs = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (job.startTime <= job.deadline) {
                int latestAvailableSlot = Math.min(job.deadline - 1, maxDeadline - 1);
                for (int slotIndex = latestAvailableSlot; slotIndex >= job.startTime - 1; slotIndex--) {
                    if (scheduledJobIdsBySlot[slotIndex] == -1) {
                        scheduledJobIdsBySlot[slotIndex] = job.jobId;
                        numberOfScheduledJobs++;
                        totalProfit += job.profit;
                        break;
                    }
                }
            }
        }

        return new int[] {numberOfScheduledJobs, totalProfit};
    }
}