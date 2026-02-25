package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class HighestResponseRatioNextScheduler {

    private static final int NO_JOB_INDEX = -1;
    private static final double NO_RESPONSE_RATIO = -1.0;

    private HighestResponseRatioNextScheduler() {
    }

    private static class Job {
        String jobName;
        int arrivalTime;
        int burstTime;
        int completionTime;
        boolean isCompleted;

        Job(String jobName, int arrivalTime, int burstTime) {
            this.jobName = jobName;
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

        int completedJobCount = 0;
        while (completedJobCount < jobCount) {
            int nextJobIndex = selectNextJobIndex(jobs, currentTime);
            if (nextJobIndex == NO_JOB_INDEX) {
                currentTime++;
                continue;
            }

            Job currentJob = jobs[nextJobIndex];
            currentTime = Math.max(currentTime, currentJob.arrivalTime);
            currentJob.completionTime = currentTime + currentJob.burstTime - currentJob.arrivalTime;
            currentTime += currentJob.burstTime;
            currentJob.isCompleted = true;
            completedJobCount++;
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
        double highestResponseRatio = NO_RESPONSE_RATIO;
        int highestRatioJobIndex = NO_JOB_INDEX;

        for (int jobIndex = 0; jobIndex < jobs.length; jobIndex++) {
            Job job = jobs[jobIndex];
            if (!job.isCompleted && job.arrivalTime <= currentTime) {
                double responseRatio = job.calculateResponseRatio(currentTime);
                if (responseRatio > highestResponseRatio) {
                    highestResponseRatio = responseRatio;
                    highestRatioJobIndex = jobIndex;
                }
            }
        }
        return highestRatioJobIndex;
    }
}