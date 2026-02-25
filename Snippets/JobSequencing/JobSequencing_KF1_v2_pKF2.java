package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;

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
        char id;
        int deadline;  // 1-based deadline
        int profit;

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
     * Returns a string representing the sequence of jobs that maximizes profit
     * given their deadlines and profits.
     *
     * @param jobs      list of jobs
     * @param maxSlots  maximum number of available time slots
     * @return          job sequence as a string, e.g. "Job Sequence: A -> C -> B"
     */
    public static String getJobSequence(ArrayList<Job> jobs, int maxSlots) {
        if (jobs == null || jobs.isEmpty() || maxSlots <= 0) {
            return "Job Sequence: ";
        }

        // Sort jobs by profit in descending order
        jobs.sort(null);

        boolean[] slotOccupied = new boolean[maxSlots];
        int[] jobIndexAtSlot = new int[maxSlots];

        for (int i = 0; i < jobs.size() && i < maxSlots; i++) {
            Job job = jobs.get(i);
            int latestPossibleSlot = Math.min(job.deadline, maxSlots) - 1;

            for (int slot = latestPossibleSlot; slot >= 0; slot--) {
                if (!slotOccupied[slot]) {
                    slotOccupied[slot] = true;
                    jobIndexAtSlot[slot] = i;
                    break;
                }
            }
        }

        StringBuilder result = new StringBuilder("Job Sequence: ");
        boolean first = true;

        for (int i = 0; i < maxSlots; i++) {
            if (slotOccupied[i]) {
                if (!first) {
                    result.append(" -> ");
                }
                result.append(jobs.get(jobIndexAtSlot[i]).id);
                first = false;
            }
        }

        return result.toString();
    }
}