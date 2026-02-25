package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    static class Class2 {
        int id;
        int startTime;
        int endTime;
        int profit;

        Class2(int id, int startTime, int endTime, int profit) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
            this.profit = profit;
        }
    }

    public static int[] method1(Class2[] jobs) {
        // Sort jobs by descending profit
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        // Find the maximum end time to determine the schedule length
        int maxEndTime = Arrays.stream(jobs)
            .mapToInt(job -> job.endTime)
            .max()
            .orElse(0);

        // Initialize schedule with -1 indicating free slots
        int[] schedule = new int[maxEndTime];
        Arrays.fill(schedule, -1);

        int jobsScheduled = 0;
        int totalProfit = 0;

        for (Class2 job : jobs) {
            if (job.startTime <= job.endTime) {
                int latestPossibleSlot = Math.min(job.endTime - 1, maxEndTime - 1);

                for (int slot = latestPossibleSlot; slot >= job.startTime - 1; slot--) {
                    if (schedule[slot] == -1) {
                        schedule[slot] = job.id;
                        jobsScheduled++;
                        totalProfit += job.profit;
                        break;
                    }
                }
            }
        }

        return new int[] {jobsScheduled, totalProfit};
    }
}