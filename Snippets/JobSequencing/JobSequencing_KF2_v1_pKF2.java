package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;

public final class JobSequencing {

    private JobSequencing() {
        // Utility class; prevent instantiation
    }

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

    public static String findJobSequence(ArrayList<Job> jobs, int size) {
        boolean[] occupiedSlots = new boolean[size];
        int[] jobIndexAtSlot = new int[size];

        for (int i = 0; i < size; i++) {
            int lastPossibleSlot = Math.min(size, jobs.get(i).deadline) - 1;
            for (int slot = lastPossibleSlot; slot >= 0; slot--) {
                if (!occupiedSlots[slot]) {
                    occupiedSlots[slot] = true;
                    jobIndexAtSlot[slot] = i;
                    break;
                }
            }
        }

        StringBuilder sequenceBuilder = new StringBuilder("Job Sequence: ");
        boolean firstJobAdded = false;

        for (int slot = 0; slot < size; slot++) {
            if (occupiedSlots[slot]) {
                if (firstJobAdded) {
                    sequenceBuilder.append(" -> ");
                }
                sequenceBuilder.append(jobs.get(jobIndexAtSlot[slot]).id);
                firstJobAdded = true;
            }
        }

        return sequenceBuilder.toString();
    }
}