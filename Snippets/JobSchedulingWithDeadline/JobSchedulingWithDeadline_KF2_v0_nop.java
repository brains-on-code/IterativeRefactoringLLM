package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;


public final class JobSchedulingWithDeadline {
    private JobSchedulingWithDeadline() {
    }


    static class Job {
        int jobId;
        int arrivalTime;
        int deadline;
        int profit;


        Job(int jobId, int arrivalTime, int deadline, int profit) {
            this.jobId = jobId;
            this.arrivalTime = arrivalTime;
            this.deadline = deadline;
            this.profit = profit;
        }
    }


    public static int[] jobSequencingWithDeadlines(Job[] jobs) {
        Arrays.sort(jobs, Comparator.comparingInt(job -> - job.profit));

        int maxDeadline = Arrays.stream(jobs).mapToInt(job -> job.deadline).max().orElse(0);

        int[] timeSlots = new int[maxDeadline];
        Arrays.fill(timeSlots, -1);

        int count = 0;
        int maxProfit = 0;

        for (Job job : jobs) {
            if (job.arrivalTime <= job.deadline) {
                for (int i = Math.min(job.deadline - 1, maxDeadline - 1); i >= job.arrivalTime - 1; i--) {
                    if (timeSlots[i] == -1) {
                        timeSlots[i] = job.jobId;
                        count++;
                        maxProfit += job.profit;
                        break;
                    }
                }
            }
        }

        return new int[] {count, maxProfit};
    }
}
