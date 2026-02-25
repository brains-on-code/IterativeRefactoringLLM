package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class JobSequencing {

    private JobSequencing() {
    }

    static class Job {
        int jobId;
        int startTime;
        int endTime;
        int profit;

        Job(int jobId, int startTime, int endTime, int profit) {
            this.jobId = jobId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.profit = profit;
        }
    }

    public static int[] scheduleJobs(Job[] jobs) {
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        int latestEndTime = Arrays.stream(jobs)
            .mapToInt(job -> job.endTime)
            .max()
            .orElse(0);

        int[] scheduledJobIdsByTimeSlot = new int[latestEndTime];
        Arrays.fill(scheduledJobIdsByTimeSlot, -1);

        int scheduledJobCount = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (job.startTime <= job.endTime) {
                int latestPossibleSlot = Math.min(job.endTime - 1, latestEndTime - 1);
                int earliestPossibleSlot = job.startTime - 1;

                for (int slotIndex = latestPossibleSlot; slotIndex >= earliestPossibleSlot; slotIndex--) {
                    if (scheduledJobIdsByTimeSlot[slotIndex] == -1) {
                        scheduledJobIdsByTimeSlot[slotIndex] = job.jobId;
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