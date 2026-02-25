package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class JobSequencing {

    private JobSequencing() {}

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

    public static String findJobSequence(List<Job> jobs, int size) {
        if (jobs == null || jobs.isEmpty() || size <= 0) {
            return "Job Sequence: ";
        }

        List<Job> sortedJobs = new ArrayList<>(jobs);
        Collections.sort(sortedJobs);

        boolean[] occupiedSlots = new boolean[size];
        int[] jobIndexAtSlot = new int[size];

        int jobsToConsider = Math.min(size, sortedJobs.size());
        for (int i = 0; i < jobsToConsider; i++) {
            Job job = sortedJobs.get(i);
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
        boolean firstJobAdded = false;

        for (int slot = 0; slot < size; slot++) {
            if (!occupiedSlots[slot]) {
                continue;
            }
            if (firstJobAdded) {
                sequenceBuilder.append(" -> ");
            }
            sequenceBuilder.append(sortedJobs.get(jobIndexAtSlot[slot]).id);
            firstJobAdded = true;
        }

        return sequenceBuilder.toString();
    }
}