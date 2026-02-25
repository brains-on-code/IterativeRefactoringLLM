package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;

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

    public static String findJobSequence(ArrayList<Job> jobs, int size) {
        boolean[] occupiedSlots = new boolean[size];
        int[] jobIndexAtSlot = new int[size];

        for (int i = 0; i < size && i < jobs.size(); i++) {
            Job job = jobs.get(i);
            int lastPossibleSlot = Math.min(size, job.deadline) - 1;

            for (int slot = lastPossibleSlot; slot >= 0; slot--) {
                if (!occupiedSlots[slot]) {
                    occupiedSlots[slot] = true;
                    jobIndexAtSlot[slot] = i;
                    break;
                }
            }
        }

        StringBuilder sequenceBuilder = new StringBuilder("Job Sequence: ");
        boolean first = true;

        for (int slot = 0; slot < size; slot++) {
            if (occupiedSlots[slot]) {
                if (!first) {
                    sequenceBuilder.append(" -> ");
                }
                sequenceBuilder.append(jobs.get(jobIndexAtSlot[slot]).id);
                first = false;
            }
        }

        return sequenceBuilder.toString();
    }
}