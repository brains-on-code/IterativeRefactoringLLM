package com.thealgorithms.scheduling;

import java.util.Arrays;
import java.util.Comparator;

public final class JobSchedulingWithDeadline {

    private JobSchedulingWithDeadline() {}

    static class Job {
        final int jobId;
        final int arrivalTime;
        final int deadline;
        final int profit;

        Job(int jobId, int arrivalTime, int deadline, int profit) {
            this.jobId = jobId;
            this.arrivalTime = arrivalTime;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    /**
     * Schedule jobs to maximize total profit, subject to each job's arrival time and deadline.
     *
     * @param jobs array of jobs to schedule
     * @return int[2]: [0] = number of scheduled jobs, [1] = total profit
     */
    public static int[] jobSequencingWithDeadlines(Job[] jobs) {
        if (jobs == null || jobs.length == 0) {
            return new int[] {0, 0};
        }

        // Sort jobs by profit in descending order
        Arrays.sort(jobs, Comparator.comparingInt(job -> -job.profit));

        // Maximum deadline determines the number of available time slots
        int maxDeadline =
            Arrays.stream(jobs).mapToInt(job -> job.deadline).max().orElse(0);

        // timeSlots[i] = jobId scheduled at time (i + 1); -1 indicates a free slot
        int[] timeSlots = new int[maxDeadline];
        Arrays.fill(timeSlots, -1);

        int scheduledJobs = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
            // Job cannot be scheduled if it arrives after its own deadline
            if (job.arrivalTime > job.deadline) {
                continue;
            }

            // Convert 1-based arrival/deadline to 0-based indices
            int latestPossibleSlot = Math.min(job.deadline - 1, maxDeadline - 1);
            int earliestPossibleSlot = Math.max(0, job.arrivalTime - 1);

            // Assign job to the latest available slot within its feasible window
            for (int slot = latestPossibleSlot; slot >= earliestPossibleSlot; slot--) {
                if (timeSlots[slot] == -1) {
                    timeSlots[slot] = job.jobId;
                    scheduledJobs++;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        return new int[] {scheduledJobs, totalProfit};
    }
}