package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Greedy algorithm for Job Sequencing with Deadlines.
 */
public final class JobSequencer {

    private JobSequencer() {
        // Utility class; prevent instantiation
    }

    /**
     * Represents a job with an ID, a deadline, and a profit.
     */
    static class Job implements Comparable<Job> {
        char id;       // Job identifier
        int deadline;  // Deadline by which the job should be completed (1-based)
        int profit;    // Profit if the job is completed before or on its deadline

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
            return other.profit - this.profit;
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
        // Track whether a time slot is occupied
        Boolean[] slotOccupied = new Boolean[maxSlots];
        Arrays.fill(slotOccupied, Boolean.FALSE);

        // Store index of job scheduled at each time slot
        int[] jobIndexAtSlot = new int[maxSlots];

        // Schedule jobs
        for (int i = 0; i < maxSlots; i++) {
            // Try to place job in the latest possible free slot before its deadline
            for (int slot = jobs.get(i).deadline - 1; slot >= 0; slot--) {
                if (!slotOccupied[slot]) {
                    jobIndexAtSlot[slot] = i;
                    slotOccupied[slot] = Boolean.TRUE;
                    break;
                }
            }
        }

        // Build result string
        StringBuilder result = new StringBuilder();
        result.append("Job Sequence: ");
        for (int i = 0; i < maxSlots; i++) {
            if (slotOccupied[i]) {
                result.append(jobs.get(jobIndexAtSlot[i]).id).append(" -> ");
            }
        }

        // Remove trailing " -> " if present
        if (result.length() >= 4) {
            result.setLength(result.length() - 4);
        }

        return result.toString();
    }
}