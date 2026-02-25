package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JobSequencing {

    private JobSequencing() {
        // Utility class
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
        public int compareTo(Job other) {
            return Integer.compare(other.profit, this.profit);
        }
    }

    public static String getJobSequence(List<Job> jobs, int maxSlots) {
        Boolean[] slotOccupied = new Boolean[maxSlots];
        Arrays.fill(slotOccupied, Boolean.FALSE);

        int[] jobIndexAtSlot = new int[maxSlots];

        for (int i = 0; i < maxSlots && i < jobs.size(); i++) {
            int lastPossibleSlot = Math.min(jobs.get(i).deadline, maxSlots) - 1;
            for (int slot = lastPossibleSlot; slot >= 0; slot--) {
                if (!slotOccupied[slot]) {
                    jobIndexAtSlot[slot] = i;
                    slotOccupied[slot] = Boolean.TRUE;
                    break;
                }
            }
        }

        StringBuilder sequenceBuilder = new StringBuilder("Job Sequence: ");
        for (int slot = 0; slot < maxSlots; slot++) {
            if (slotOccupied[slot]) {
                sequenceBuilder.append(jobs.get(jobIndexAtSlot[slot]).id).append(" -> ");
            }
        }

        if (sequenceBuilder.length() >= 4) {
            sequenceBuilder.setLength(sequenceBuilder.length() - 4);
        }

        return sequenceBuilder.toString();
    }

    public static String getJobSequence(ArrayList<Job> jobs, int maxSlots) {
        return getJobSequence((List<Job>) jobs, maxSlots);
    }
}