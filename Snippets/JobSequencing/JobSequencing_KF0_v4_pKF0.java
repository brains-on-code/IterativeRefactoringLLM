package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Problem Link: https://en.wikipedia.org/wiki/Job-shop_scheduling
public final class JobSequencing {

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
            return Integer.compare(other.profit, profit);
        }
    }

    public static String findJobSequence(List<Job> jobs, int maxSlots) {
        if (jobs == null || jobs.isEmpty() || maxSlots <= 0) {
            return "Job Sequence: ";
        }

        List<Job> sortedJobs = new ArrayList<>(jobs);
        Collections.sort(sortedJobs);

        boolean[] occupiedSlots = new boolean[maxSlots];
        Job[] scheduledJobs = new Job[maxSlots];

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

        StringBuilder sequenceBuilder = new StringBuilder("Job Sequence: ");
        boolean firstJobAdded = false;

        for (Job job : scheduledJobs) {
            if (job != null) {
                if (firstJobAdded) {
                    sequenceBuilder.append(" -> ");
                }
                sequenceBuilder.append(job.id);
                firstJobAdded = true;
            }
        }

        return sequenceBuilder.toString();
    }
}