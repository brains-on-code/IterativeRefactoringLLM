package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class Class1 {
    private Class1() {
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

        int maxEndTime = Arrays.stream(jobs).mapToInt(job -> job.endTime).max().orElse(0);

        int[] timeSlots = new int[maxEndTime];
        Arrays.fill(timeSlots, -1);

        int totalJobsScheduled = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            if (job.startTime <= job.endTime) {
                for (int slot = Math.min(job.endTime - 1, maxEndTime - 1); slot >= job.startTime - 1; slot--) {
                    if (timeSlots[slot] == -1) {
                        timeSlots[slot] = job.id;
                        totalJobsScheduled++;
                        totalProfit += job.profit;
                        break;
                    }
                }
            }
        }

        return new int[] {totalJobsScheduled, totalProfit};
    }
}