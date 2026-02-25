package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class Class1 {

    private static final int NOT_FOUND = -1;
    private static final double NO_RATIO = -1.0;

    private Class1() {
        // Utility class; prevent instantiation
    }

    private static class Job {
        final String name;
        final int arrivalTime;
        final int burstTime;
        int completionTime;
        boolean completed;

        Job(String name, int arrivalTime, int burstTime) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.completionTime = 0;
            this.completed = false;
        }

        double responseRatioAt(int currentTime) {
            int waitingTime = currentTime - arrivalTime;
            return (double) (waitingTime + burstTime) / burstTime;
        }
    }

    public static int[] method2(
        final String[] names,
        final int[] arrivalTimes,
        final int[] burstTimes,
        final int jobCount
    ) {
        int currentTime = 0;
        int[] completionTimes = new int[jobCount];
        Job[] jobs = createAndSortJobs(names, arrivalTimes, burstTimes, jobCount);

        int completedJobs = 0;
        while (completedJobs < jobCount) {
            int nextJobIndex = selectNextJob(jobs, currentTime);

            if (nextJobIndex == NOT_FOUND) {
                currentTime++;
                continue;
            }

            Job job = jobs[nextJobIndex];
            currentTime = Math.max(currentTime, job.arrivalTime);
            currentTime += job.burstTime;
            job.completionTime = currentTime;
            job.completed = true;
            completedJobs++;
        }

        for (int i = 0; i < jobCount; i++) {
            completionTimes[i] = jobs[i].completionTime;
        }

        return completionTimes;
    }

    private static Job[] createAndSortJobs(
        String[] names,
        int[] arrivalTimes,
        int[] burstTimes,
        int jobCount
    ) {
        Job[] jobs = new Job[jobCount];
        for (int i = 0; i < jobCount; i++) {
            jobs[i] = new Job(names[i], arrivalTimes[i], burstTimes[i]);
        }
        Arrays.sort(jobs, Comparator.comparingInt(job -> job.arrivalTime));
        return jobs;
    }

    public static int[] method3(int[] completionTimes, int[] arrivalTimes) {
        int[] turnaroundTimes = new int[completionTimes.length];
        for (int i = 0; i < completionTimes.length; i++) {
            turnaroundTimes[i] = completionTimes[i] - arrivalTimes[i];
        }
        return turnaroundTimes;
    }

    private static int selectNextJob(Job[] jobs, int currentTime) {
        double bestRatio = NO_RATIO;
        int bestIndex = NOT_FOUND;

        for (int i = 0; i < jobs.length; i++) {
            Job job = jobs[i];
            if (!job.completed && job.arrivalTime <= currentTime) {
                double ratio = job.responseRatioAt(currentTime);
                if (ratio > bestRatio) {
                    bestRatio = ratio;
                    bestIndex = i;
                }
            }
        }

        return bestIndex;
    }
}