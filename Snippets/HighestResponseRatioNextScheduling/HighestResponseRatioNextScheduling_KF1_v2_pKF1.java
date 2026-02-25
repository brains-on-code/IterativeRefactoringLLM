package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class HighestResponseRatioNextScheduler {

    private static final int INVALID_INDEX = -1;
    private static final double INVALID_RATIO = -1.0;

    private HighestResponseRatioNextScheduler() {
    }

    private static class Job {
        String name;
        int arrivalTime;
        int burstTime;
        int completionTime;
        boolean isCompleted;

        Job(String name, int arrivalTime, int burstTime) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.completionTime = 0;
            this.isCompleted = false;
        }

        double calculateResponseRatio(int currentTime) {
            return (double) (burstTime + currentTime - arrivalTime) / burstTime;
        }
    }

    public static int[] calculateCompletionTimes(final String[] jobNames,
                                                 final int[] arrivalTimes,
                                                 final int[] burstTimes,
                                                 final int jobCount) {
        int currentTime = 0;
        int[] completionTimes = new int[jobCount];
        Job[] jobs = new Job[jobCount];

        for (int i = 0; i < jobCount; i++) {
            jobs[i] = new Job(jobNames[i], arrivalTimes[i], burstTimes[i]);
        }

        Arrays.sort(jobs, Comparator.comparingInt(job -> job.arrivalTime));

        int completedJobs = 0;
        while (completedJobs < jobCount) {
            int nextJobIndex = selectNextJobIndex(jobs, currentTime);
            if (nextJobIndex == INVALID_INDEX) {
                currentTime++;
                continue;
            }

            Job job = jobs[nextJobIndex];
            currentTime = Math.max(currentTime, job.arrivalTime);
            job.completionTime = currentTime + job.burstTime - job.arrivalTime;
            currentTime += job.burstTime;
            job.isCompleted = true;
            completedJobs++;
        }

        for (int i = 0; i < jobCount; i++) {
            completionTimes[i] = jobs[i].completionTime;
        }

        return completionTimes;
    }

    public static int[] calculateTurnaroundTimes(int[] completionTimes, int[] arrivalTimes) {
        int[] turnaroundTimes = new int[completionTimes.length];
        for (int i = 0; i < completionTimes.length; i++) {
            turnaroundTimes[i] = completionTimes[i] - arrivalTimes[i];
        }
        return turnaroundTimes;
    }

    private static int selectNextJobIndex(Job[] jobs, int currentTime) {
        return findJobWithHighestResponseRatio(jobs, currentTime);
    }

    private static int findJobWithHighestResponseRatio(Job[] jobs, int currentTime) {
        double bestResponseRatio = INVALID_RATIO;
        int bestJobIndex = INVALID_INDEX;

        for (int i = 0; i < jobs.length; i++) {
            Job job = jobs[i];
            if (!job.isCompleted && job.arrivalTime <= currentTime) {
                double responseRatio = job.calculateResponseRatio(currentTime);
                if (responseRatio > bestResponseRatio) {
                    bestResponseRatio = responseRatio;
                    bestJobIndex = i;
                }
            }
        }
        return bestJobIndex;
    }
}