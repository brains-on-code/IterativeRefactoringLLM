package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;

public final class JobSequencing {

    private JobSequencing() {
    }

    static class Job implements Comparable<Job> {
        char id;
        int deadline;
        int profit;

        @Override
        public int compareTo(Job other) {
            return other.profit - this.profit;
        }

        Job(char id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    public static String getJobSequence(ArrayList<Job> jobs, int numberOfSlots) {
        Boolean[] slotOccupied = new Boolean[numberOfSlots];
        Arrays.fill(slotOccupied, Boolean.FALSE);

        int[] jobIndexAtSlot = new int[numberOfSlots];

        for (int i = 0; i < numberOfSlots; i++) {
            for (int slot = jobs.get(i).deadline - 1; slot >= 0; slot--) {
                if (!slotOccupied[slot]) {
                    jobIndexAtSlot[slot] = i;
                    slotOccupied[slot] = Boolean.TRUE;
                    break;
                }
            }
        }

        StringBuilder sequenceBuilder = new StringBuilder();
        sequenceBuilder.append("Job Sequence: ");
        for (int i = 0; i < jobs.size(); i++) {
            if (slotOccupied[i]) {
                sequenceBuilder.append(jobs.get(jobIndexAtSlot[i]).id).append(" -> ");
            }
        }

        if (sequenceBuilder.length() >= 4) {
            sequenceBuilder.setLength(sequenceBuilder.length() - 4);
        }

        return sequenceBuilder.toString();
    }
}