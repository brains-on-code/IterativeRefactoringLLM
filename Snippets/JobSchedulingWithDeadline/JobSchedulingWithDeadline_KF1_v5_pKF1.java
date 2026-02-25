package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class JobSequencing {

    private JobSequencing() {
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

    public static int[] scheduleJobs(Job[] jobs) {
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        int maxDeadline = Arrays.stream(jobs)
            .mapToInt(job -> job.deadline)
            .max()
            .orElse(0);

        int[] jobIdsByTimeSlot = new int[maxDeadline];
        Arrays.fill(jobIdsByTimeSlot, -1);

        int scheduledJobCount = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (job.startTime <= job.deadline) {
                int latestPossibleSlot = Math.min(job.deadline - 1, maxDeadline - 1);
                int earliestPossibleSlot = job.startTime - 1;

                for (int timeSlot = latestPossibleSlot; timeSlot >= earliestPossibleSlot; timeSlot--) {
                    if (jobIdsByTimeSlot[timeSlot] == -1) {
                        jobIdsByTimeSlot[timeSlot] = job.id;
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