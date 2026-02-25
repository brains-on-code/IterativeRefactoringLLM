package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class HighestResponseRatioNextScheduler {

    private static final int NO_ELIGIBLE_JOB_INDEX = -1;
    private static final double INITIAL_RESPONSE_RATIO = -1.0;

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
            int waitingTime = currentTime - arrivalTime;
            return (double) (waitingTime + burstTime) / burstTime;
        }
    }

    public static int[] calculateCompletionTimes(final String[] jobNames,
                                                 final int[] arrivalTimes,
                                                 final int[] burstTimes,
                                                 final int jobCount) {
        int currentTime = 0;
        int[] completionTimes = new int[jobCount];
        Job[] jobs = new Job[jobCount];

        for (int jobIndex = 0; jobIndex < jobCount; jobIndex++) {
            jobs[jobIndex] = new Job(jobNames[jobIndex], arrivalTimes[jobIndex], burstTimes[jobIndex]);
        }

        Arrays.sort(jobs, Comparator.comparingInt(job -> job.arrivalTime));

        int completedJobs = 0;
        while (completedJobs < jobCount) {
            int nextJobIndex = selectNextJobIndex(jobs, currentTime);
            if (nextJobIndex == NO_ELIGIBLE_JOB_INDEX) {
                currentTime++;
                continue;
            }

            Job jobToRun = jobs[nextJobIndex];
            currentTime = Math.max(currentTime, jobToRun.arrivalTime);
            jobToRun.completionTime = currentTime + jobToRun.burstTime - jobToRun.arrivalTime;
            currentTime += jobToRun.burstTime;
            jobToRun.isCompleted = true;
            completedJobs++;
        }

        for (int jobIndex = 0; jobIndex < jobCount; jobIndex++) {
            completionTimes[jobIndex] = jobs[jobIndex].completionTime;
        }

        return completionTimes;
    }

    public static int[] calculateTurnaroundTimes(int[] completionTimes, int[] arrivalTimes) {
        int[] turnaroundTimes = new int[completionTimes.length];
        for (int index = 0; index < completionTimes.length; index++) {
            turnaroundTimes[index] = completionTimes[index] - arrivalTimes[index];
        }
        return turnaroundTimes;
    }

    private static int selectNextJobIndex(Job[] jobs, int currentTime) {
        return findJobWithHighestResponseRatio(jobs, currentTime);
    }

    private static int findJobWithHighestResponseRatio(Job[] jobs, int currentTime) {
        double highestResponseRatio = INITIAL_RESPONSE_RATIO;
        int highestResponseRatioJobIndex = NO_ELIGIBLE_JOB_INDEX;

        for (int jobIndex = 0; jobIndex < jobs.length; jobIndex++) {
            Job job = jobs[jobIndex];
            if (!job.isCompleted && job.arrivalTime <= currentTime) {
                double responseRatio = job.calculateResponseRatio(currentTime);
                if (responseRatio > highestResponseRatio) {
                    highestResponseRatio = responseRatio;
                    highestResponseRatioJobIndex = jobIndex;
                }
            }
        }
        return highestResponseRatioJobIndex;
    }
}