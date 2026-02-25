package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;

// Problem Link: https://en.wikipedia.org/wiki/Job-shop_scheduling

public final class JobSequencing {
    private JobSequencing() {}

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
        public int compareTo(Job other) {
            return Integer.compare(other.profit, this.profit);
        }
    }

    public static String findJobSequence(ArrayList<Job> jobs, int maxSlots) {
        Boolean[] occupiedSlots = new Boolean[maxSlots];
        Arrays.fill(occupiedSlots, Boolean.FALSE);

        int[] jobIndexBySlot = new int[maxSlots];

        for (int jobIndex = 0; jobIndex < maxSlots; jobIndex++) {
            Job job = jobs.get(jobIndex);
            for (int slot = job.deadline - 1; slot >= 0; slot--) {
                if (!occupiedSlots[slot]) {
                    jobIndexBySlot[slot] = jobIndex;
                    occupiedSlots[slot] = Boolean.TRUE;
                    break;
                }
            }
        }

        StringBuilder sequenceBuilder = new StringBuilder();
        sequenceBuilder.append("Job Sequence: ");
        for (int slot = 0; slot < jobs.size(); slot++) {
            if (occupiedSlots[slot]) {
                sequenceBuilder
                    .append(jobs.get(jobIndexBySlot[slot]).id)
                    .append(" -> ");
            }
        }

        if (sequenceBuilder.length() >= 4) {
            sequenceBuilder.setLength(sequenceBuilder.length() - 4);
        }

        return sequenceBuilder.toString();
    }
}