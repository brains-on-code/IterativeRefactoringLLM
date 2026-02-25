package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Greedy solution for the Job Sequencing with Deadlines problem.
 * Reference: https://en.wikipedia.org/wiki/Job-shop_scheduling
 */
public final class JobSequencing {

    private static final String SEQUENCE_PREFIX = "Job Sequence: ";
    private static final String SEPARATOR = " -> ";

    private JobSequencing() {
        // Prevent instantiation
    }

    /**
     * Represents a job with an identifier, deadline, and profit.
     * Jobs are ordered by profit in descending order.
     */
    static class Job implements Comparable<Job> {
        char id;
        int deadline;
        int profit;

        Job(char id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }

        @Override
        public int compareTo(Job otherJob) {
            return Integer.compare(otherJob.profit, this.profit);
        }
    }

    /**
     * Returns a string representation of the selected job sequence.
     *
     * @param jobs list of jobs (assumed to be sorted by profit descending)
     * @param size number of available time slots
     * @return job sequence in execution order
     */
    public static String findJobSequence(ArrayList<Job> jobs, int size) {
        boolean[] occupiedSlots = new boolean[size];
        int[] jobIndexAtSlot = new int[size];

        for (int i = 0; i < size && i < jobs.size(); i++) {
            Job currentJob = jobs.get(i);
            int lastPossibleSlot = Math.min(size, currentJob.deadline) - 1;

            for (int slot = lastPossibleSlot; slot >= 0; slot--) {
                if (!occupiedSlots[slot]) {
                    occupiedSlots[slot] = true;
                    jobIndexAtSlot[slot] = i;
                    break;
                }
            }
        }

        StringBuilder sequence = new StringBuilder(SEQUENCE_PREFIX);
        for (int slot = 0; slot < size; slot++) {
            if (occupiedSlots[slot]) {
                sequence.append(jobs.get(jobIndexAtSlot[slot]).id).append(SEPARATOR);
            }
        }

        int prefixLength = SEQUENCE_PREFIX.length();
        int minLengthWithOneJob = prefixLength + SEPARATOR.length() + 1;
        if (sequence.length() >= minLengthWithOneJob) {
            sequence.setLength(sequence.length() - SEPARATOR.length());
        }

        return sequence.toString();
    }
}