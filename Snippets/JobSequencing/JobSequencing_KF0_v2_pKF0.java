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
            return Integer.compare(other.profit, this.profit); // descending by profit
        }
    }

    public static String findJobSequence(List<Job> jobs, int maxSlots) {
        if (jobs == null || jobs.isEmpty() || maxSlots <= 0) {
            return "Job Sequence: ";
        }

        // Ensure jobs are processed in descending order of profit
        List<Job> sortedJobs = new ArrayList<>(jobs);
        Collections.sort(sortedJobs);

        boolean[] occupiedSlots = new boolean[maxSlots];
        Job[] jobAtSlot = new Job[maxSlots];

        for (Job job : sortedJobs) {
            int lastPossibleSlot = Math.min(maxSlots, job.deadline) - 1;

            for (int slot = lastPossibleSlot; slot >= 0; slot--) {
                if (!occupiedSlots[slot]) {
                    occupiedSlots[slot] = true;
                    jobAtSlot[slot] = job;
                    break;
                }
            }
        }

        StringBuilder sequenceBuilder = new StringBuilder("Job Sequence: ");
        boolean first = true;

        for (int slot = 0; slot < maxSlots; slot++) {
            if (occupiedSlots[slot] && jobAtSlot[slot] != null) {
                if (!first) {
                    sequenceBuilder.append(" -> ");
                }
                sequenceBuilder.append(jobAtSlot[slot].id);
                first = false;
            }
        }

        return sequenceBuilder.toString();
    }
}