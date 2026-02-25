package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.List;

public final class JobSequencing {

    private JobSequencing() {
        // Utility class
    }

    static class Job implements Comparable<Job> {
        private final char id;
        private final int deadline;
        private final int profit;

        Job(char id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }

        public char getId() {
            return id;
        }

        public int getDeadline() {
            return deadline;
        }

        public int getProfit() {
            return profit;
        }

        @Override
        public int compareTo(Job other) {
            return Integer.compare(other.profit, this.profit);
        }
    }

    public static String getJobSequence(List<Job> jobs, int maxSlots) {
        if (jobs == null || jobs.isEmpty() || maxSlots <= 0) {
            return "Job Sequence: ";
        }

        boolean[] slotOccupied = new boolean[maxSlots];
        Job[] jobAtSlot = new Job[maxSlots];

        int jobsToSchedule = Math.min(maxSlots, jobs.size());
        for (int i = 0; i < jobsToSchedule; i++) {
            Job job = jobs.get(i);
            int lastPossibleSlot = Math.min(job.getDeadline(), maxSlots) - 1;

            for (int slot = lastPossibleSlot; slot >= 0; slot--) {
                if (!slotOccupied[slot]) {
                    jobAtSlot[slot] = job;
                    slotOccupied[slot] = true;
                    break;
                }
            }
        }

        StringBuilder sequenceBuilder = new StringBuilder("Job Sequence: ");
        boolean firstJobAdded = false;

        for (int slot = 0; slot < maxSlots; slot++) {
            if (slotOccupied[slot]) {
                if (firstJobAdded) {
                    sequenceBuilder.append(" -> ");
                }
                sequenceBuilder.append(jobAtSlot[slot].getId());
                firstJobAdded = true;
            }
        }

        return sequenceBuilder.toString();
    }

    public static String getJobSequence(ArrayList<Job> jobs, int maxSlots) {
        return getJobSequence((List<Job>) jobs, maxSlots);
    }
}