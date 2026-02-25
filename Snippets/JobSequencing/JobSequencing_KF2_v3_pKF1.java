package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;

public final class JobSequencing {

    private JobSequencing() {}

    static class Job implements Comparable<Job> {
        char jobId;
        int deadline;
        int profit;

        Job(char jobId, int deadline, int profit) {
            this.jobId = jobId;
            this.deadline = deadline;
            this.profit = profit;
        }

        @Override
        public int compareTo(Job otherJob) {
            return Integer.compare(otherJob.profit, this.profit);
        }
    }

    public static String findJobSequence(ArrayList<Job> jobs, int totalSlots) {
        Boolean[] isSlotOccupied = new Boolean[totalSlots];
        Arrays.fill(isSlotOccupied, Boolean.FALSE);

        int[] jobIndexBySlot = new int[totalSlots];

        for (int jobIndex = 0; jobIndex < totalSlots; jobIndex++) {
            int lastPossibleSlotIndex = jobs.get(jobIndex).deadline - 1;
            for (int slotIndex = lastPossibleSlotIndex; slotIndex >= 0; slotIndex--) {
                if (!isSlotOccupied[slotIndex]) {
                    jobIndexBySlot[slotIndex] = jobIndex;
                    isSlotOccupied[slotIndex] = Boolean.TRUE;
                    break;
                }
            }
        }

        StringBuilder jobSequenceBuilder = new StringBuilder();
        jobSequenceBuilder.append("Job Sequence: ");
        for (int slotIndex = 0; slotIndex < jobs.size(); slotIndex++) {
            if (isSlotOccupied[slotIndex]) {
                jobSequenceBuilder
                    .append(jobs.get(jobIndexBySlot[slotIndex]).jobId)
                    .append(" -> ");
            }
        }

        if (jobSequenceBuilder.length() >= 4) {
            jobSequenceBuilder.setLength(jobSequenceBuilder.length() - 4);
        }

        return jobSequenceBuilder.toString();
    }
}