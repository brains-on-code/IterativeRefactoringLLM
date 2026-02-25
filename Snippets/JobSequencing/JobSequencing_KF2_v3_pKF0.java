package com.thealgorithms.greedyalgorithms;

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
        if (jobs == null || jobs.isEmpty() || maxSlots <= 0) {
            return "Job Sequence: ";
        }

        boolean[] occupiedSlots = new boolean[maxSlots];
        int[] jobIndexAtSlot = new int[maxSlots];

        int jobsToSchedule = Math.min(jobs.size(), maxSlots);
        for (int jobIndex = 0; jobIndex < jobsToSchedule; jobIndex++) {
            Job job = jobs.get(jobIndex);
            int lastPossibleSlot = Math.min(job.deadline, maxSlots) - 1;

            scheduleJobInLatestFreeSlot(jobIndex, lastPossibleSlot, occupiedSlots, jobIndexAtSlot);
        }

        return buildJobSequenceString(jobs, maxSlots, occupiedSlots, jobIndexAtSlot);
    }

    private static void scheduleJobInLatestFreeSlot(
            int jobIndex,
            int lastPossibleSlot,
            boolean[] occupiedSlots,
            int[] jobIndexAtSlot
    ) {
        for (int slot = lastPossibleSlot; slot >= 0; slot--) {
            if (!occupiedSlots[slot]) {
                occupiedSlots[slot] = true;
                jobIndexAtSlot[slot] = jobIndex;
                return;
            }
        }
    }

    private static String buildJobSequenceString(
            List<Job> jobs,
            int maxSlots,
            boolean[] occupiedSlots,
            int[] jobIndexAtSlot
    ) {
        StringBuilder sequenceBuilder = new StringBuilder("Job Sequence: ");
        boolean isFirstJob = true;

        for (int slot = 0; slot < maxSlots; slot++) {
            if (!occupiedSlots[slot]) {
                continue;
            }

            if (!isFirstJob) {
                sequenceBuilder.append(" -> ");
            }

            sequenceBuilder.append(jobs.get(jobIndexAtSlot[slot]).id);
            isFirstJob = false;
        }

        return sequenceBuilder.toString();
    }
}