package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    static class Class2 {
        final int id;
        final int startTime;
        final int endTime;
        final int profit;

        Class2(int id, int startTime, int endTime, int profit) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
            this.profit = profit;
        }
    }

    public static int[] method1(Class2[] jobs) {
        if (jobs == null || jobs.length == 0) {
            return new int[] {0, 0};
        }

        // Sort jobs by descending profit
        Arrays.sort(jobs, Comparator.comparingInt((Class2 job) -> job.profit).reversed());

        int maxEndTime =
            Arrays.stream(jobs).mapToInt(job -> job.endTime).max().orElse(0);

        if (maxEndTime <= 0) {
            return new int[] {0, 0};
        }

        int[] schedule = new int[maxEndTime];
        Arrays.fill(schedule, -1);

        int jobsScheduled = 0;
        int totalProfit = 0;

        for (Class2 job : jobs) {
            if (job.startTime > job.endTime || job.endTime <= 0) {
                continue;
            }

            int latestPossibleSlot = Math.min(job.endTime - 1, maxEndTime - 1);
            int earliestPossibleSlot = Math.max(job.startTime - 1, 0);

            for (int slot = latestPossibleSlot; slot >= earliestPossibleSlot; slot--) {
                if (schedule[slot] != -1) {
                    continue;
                }

                schedule[slot] = job.id;
                jobsScheduled++;
                totalProfit += job.profit;
                break;
            }
        }

        return new int[] {jobsScheduled, totalProfit};
    }
}