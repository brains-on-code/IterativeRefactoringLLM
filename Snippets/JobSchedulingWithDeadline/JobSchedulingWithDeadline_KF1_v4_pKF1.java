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

        int latestDeadline = Arrays.stream(jobs)
            .mapToInt(job -> job.endTime)
            .max()
            .orElse(0);

        int[] scheduledJobIdsByTimeSlot = new int[latestDeadline];
        Arrays.fill(scheduledJobIdsByTimeSlot, -1);

        int totalScheduledJobs = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (job.startTime <= job.endTime) {
                int latestAvailableSlot = Math.min(job.endTime - 1, latestDeadline - 1);
                int earliestAvailableSlot = job.startTime - 1;

                for (int timeSlot = latestAvailableSlot; timeSlot >= earliestAvailableSlot; timeSlot--) {
                    if (scheduledJobIdsByTimeSlot[timeSlot] == -1) {
                        scheduledJobIdsByTimeSlot[timeSlot] = job.jobId;
                        totalScheduledJobs++;
                        totalProfit += job.profit;
                        break;
                    }
                }
            }
        }

        return new int[] {totalScheduledJobs, totalProfit};
    }
}