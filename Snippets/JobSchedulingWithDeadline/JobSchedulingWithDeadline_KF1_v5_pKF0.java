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

        sortJobsByProfitDescending(jobs);

        int maxEndTime = findMaxEndTime(jobs);
        if (maxEndTime <= 0) {
            return new int[] {0, 0};
        }

        int[] schedule = initializeSchedule(maxEndTime);

        int jobsScheduled = 0;
        int totalProfit = 0;

        for (Class2 job : jobs) {
            if (!isValidJob(job)) {
                continue;
            }

            int latestSlot = Math.min(job.endTime - 1, maxEndTime - 1);
            int earliestSlot = Math.max(job.startTime - 1, 0);

            int scheduledSlot = findAvailableSlot(schedule, earliestSlot, latestSlot);
            if (scheduledSlot != -1) {
                schedule[scheduledSlot] = job.id;
                jobsScheduled++;
                totalProfit += job.profit;
            }
        }

        return new int[] {jobsScheduled, totalProfit};
    }

    private static void sortJobsByProfitDescending(Class2[] jobs) {
        Arrays.sort(jobs, Comparator.comparingInt(job -> job.profit).reversed());
    }

    private static int findMaxEndTime(Class2[] jobs) {
        return Arrays.stream(jobs)
            .mapToInt(job -> job.endTime)
            .max()
            .orElse(0);
    }

    private static int[] initializeSchedule(int maxEndTime) {
        int[] schedule = new int[maxEndTime];
        Arrays.fill(schedule, -1);
        return schedule;
    }

    private static boolean isValidJob(Class2 job) {
        return job != null && job.endTime > 0 && job.startTime <= job.endTime;
    }

    private static int findAvailableSlot(int[] schedule, int earliestSlot, int latestSlot) {
        for (int slot = latestSlot; slot >= earliestSlot; slot--) {
            if (schedule[slot] == -1) {
                return slot;
            }
        }
        return -1;
    }
}