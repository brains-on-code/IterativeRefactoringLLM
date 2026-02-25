package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;

// Problem Link: https://en.wikipedia.org/wiki/Job-shop_scheduling

public final class JobSequencing {
    private JobSequencing() {
    }

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

    public static String findJobSequence(ArrayList<Job> jobs, int maxSlots) {
        Boolean[] isSlotOccupied = new Boolean[maxSlots];
        Arrays.fill(isSlotOccupied, Boolean.FALSE);

        int[] jobIndexAtSlot = new int[maxSlots];

        for (int jobIndex = 0; jobIndex < maxSlots; jobIndex++) {
            Job currentJob = jobs.get(jobIndex);
            for (int slotIndex = currentJob.deadline - 1; slotIndex >= 0; slotIndex--) {
                if (!isSlotOccupied[slotIndex]) {
                    jobIndexAtSlot[slotIndex] = jobIndex;
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
                    .append(jobs.get(jobIndexAtSlot[slotIndex]).jobId)
                    .append(" -> ");
            }
        }

        if (jobSequenceBuilder.length() >= 4) {
            jobSequenceBuilder.setLength(jobSequenceBuilder.length() - 4);
        }

        return jobSequenceBuilder.toString();
    }
}