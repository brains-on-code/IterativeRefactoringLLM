package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class JobSequencing {

    private JobSequencing() {
    }

    static class Job {
        int id;
        int startTime;
        int endTime;
        int profit;

        Job(int id, int startTime, int endTime, int profit) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
            this.profit = profit;
        }
    }

    public static int[] scheduleJobs(Job[] jobs) {
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        int maxEndTime = Arrays.stream(jobs)
            .mapToInt(job -> job.endTime)
            .max()
            .orElse(0);

        int[] jobIdsByTimeSlot = new int[maxEndTime];
        Arrays.fill(jobIdsByTimeSlot, -1);

        int scheduledJobs = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (job.startTime <= job.endTime) {
                int latestAvailableSlot = Math.min(job.endTime - 1, maxEndTime - 1);
                int earliestAvailableSlot = job.startTime - 1;

                for (int slot = latestAvailableSlot; slot >= earliestAvailableSlot; slot--) {
                    if (jobIdsByTimeSlot[slot] == -1) {
                        jobIdsByTimeSlot[slot] = job.id;
                        scheduledJobs++;
                        totalProfit += job.profit;
                        break;
                    }
                }
            }
        }

        return new int[] {scheduledJobs, totalProfit};
    }
}