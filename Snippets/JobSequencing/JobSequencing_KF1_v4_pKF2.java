package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Greedy algorithm for Job Sequencing with Deadlines.
 */
public final class JobSequencer {

    private JobSequencer() {
        // Prevent instantiation
    }

    /**
     * Represents a job with an ID, a deadline, and a profit.
     */
    static class Job implements Comparable<Job> {
        final char id;
        final int deadline; // 1-based deadline
        final int profit;

        Job(char id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }

        /**
         * Sort jobs in descending order of profit.
         */
        @Override
        public int compareTo(Job other) {
            return Integer.compare(other.profit, this.profit);
        }
    }

    /**
     * Computes a sequence of jobs that maximizes profit subject to deadlines.
     *
     * @param jobs     list of jobs
     * @param maxSlots maximum number of available time slots
     * @return job sequence as a string, e.g. "Job Sequence: A -> C -> B"
     */
    public static String getJobSequence(List<Job> jobs, int maxSlots) {
        if (jobs == null || jobs.isEmpty() || maxSlots <= 0) {
            return "Job Sequence: ";
        }

        jobs.sort(null);

        boolean[] slotOccupied = new boolean[maxSlots];
        int[] jobIndexAtSlot = new int[maxSlots];

        for (int jobIndex = 0; jobIndex < jobs.size() && jobIndex < maxSlots; jobIndex++) {
            Job job = jobs.get(jobIndex);
            int latestPossibleSlot = Math.min(job.deadline, maxSlots) - 1;

            for (int slot = latestPossibleSlot; slot >= 0; slot--) {
                if (!slotOccupied[slot]) {
                    slotOccupied[slot] = true;
                    jobIndexAtSlot[slot] = jobIndex;
                    break;
                }
            }
        }

        StringBuilder result = new StringBuilder("Job Sequence: ");
        boolean firstJobAdded = false;

        for (int slot = 0; slot < maxSlots; slot++) {
            if (slotOccupied[slot]) {
                if (firstJobAdded) {
                    result.append(" -> ");
                }
                result.append(jobs.get(jobIndexAtSlot[slot]).id);
                firstJobAdded = true;
            }
        }

        return result.toString();
    }
}