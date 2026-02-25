package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Problem Link: https://en.wikipedia.org/wiki/Job-shop_scheduling
public final class JobSequencing {

    private static final String JOB_SEQUENCE_PREFIX = "Job Sequence: ";

    private JobSequencing() {
        // Utility class
    }

    static class Job implements Comparable<Job> {
        final char id;
        final int deadline;
        final int profit;

        Job(char id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }

        @Override
        public int compareTo(Job other) {
            // Sort in descending order of profit
            return Integer.compare(other.profit, this.profit);
        }
    }

    public static String findJobSequence(List<Job> jobs, int maxSlots) {
        if (jobs == null || jobs.isEmpty() || maxSlots <= 0) {
            return JOB_SEQUENCE_PREFIX;
        }

        List<Job> sortedJobs = new ArrayList<>(jobs);
        Collections.sort(sortedJobs);

        boolean[] occupiedSlots = new boolean[maxSlots];
        Job[] scheduledJobs = new Job[maxSlots];

        scheduleJobs(sortedJobs, maxSlots, occupiedSlots, scheduledJobs);

        return buildJobSequenceString(scheduledJobs);
    }

    private static void scheduleJobs(
            List<Job> sortedJobs,
            int maxSlots,
            boolean[] occupiedSlots,
            Job[] scheduledJobs
    ) {
        for (Job job : sortedJobs) {
            int lastPossibleSlot = Math.min(maxSlots, job.deadline) - 1;

            for (int slot = lastPossibleSlot; slot >= 0; slot--) {
                if (!occupiedSlots[slot]) {
                    occupiedSlots[slot] = true;
                    scheduledJobs[slot] = job;
                    break;
                }
            }
        }
    }

    private static String buildJobSequenceString(Job[] scheduledJobs) {
        StringBuilder sequenceBuilder = new StringBuilder(JOB_SEQUENCE_PREFIX);
        boolean isFirstJob = true;

        for (Job job : scheduledJobs) {
            if (job == null) {
                continue;
            }
            if (!isFirstJob) {
                sequenceBuilder.append(" -> ");
            }
            sequenceBuilder.append(job.id);
            isFirstJob = false;
        }

        return sequenceBuilder.toString();
    }
}