package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;

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

    public static String findJobSequence(ArrayList<Job> jobs, int totalSlots) {
        Boolean[] slotOccupied = new Boolean[totalSlots];
        Arrays.fill(slotOccupied, Boolean.FALSE);

        int[] jobIndexForSlot = new int[totalSlots];

        for (int jobIndex = 0; jobIndex < totalSlots; jobIndex++) {
            int lastAvailableSlot = jobs.get(jobIndex).deadline - 1;
            for (int slotIndex = lastAvailableSlot; slotIndex >= 0; slotIndex--) {
                if (!slotOccupied[slotIndex]) {
                    jobIndexForSlot[slotIndex] = jobIndex;
                    slotOccupied[slotIndex] = Boolean.TRUE;
                    break;
                }
            }
        }

        StringBuilder sequenceBuilder = new StringBuilder();
        sequenceBuilder.append("Job Sequence: ");
        for (int slotIndex = 0; slotIndex < jobs.size(); slotIndex++) {
            if (slotOccupied[slotIndex]) {
                sequenceBuilder
                    .append(jobs.get(jobIndexForSlot[slotIndex]).id)
                    .append(" -> ");
            }
        }

        if (sequenceBuilder.length() >= 4) {
            sequenceBuilder.setLength(sequenceBuilder.length() - 4);
        }

        return sequenceBuilder.toString();
    }
}