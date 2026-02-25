package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            return Integer.compare(other.profit, this.profit);
        }
    }

    public static String findJobSequence(List<Job> jobs, int maxSlots) {
        boolean[] occupiedSlots = new boolean[maxSlots];
        int[] jobIndexAtSlot = new int[maxSlots];

        for (int i = 0; i < Math.min(jobs.size(), maxSlots); i++) {
            Job job = jobs.get(i);
            int lastPossibleSlot = Math.min(job.deadline, maxSlots) - 1;

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

        for (int slot = 0; slot < maxSlots; slot++) {
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