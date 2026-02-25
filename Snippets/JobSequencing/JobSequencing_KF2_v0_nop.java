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
        public int compareTo(Job otherJob) {
            return otherJob.profit - this.profit;
        }

        Job(char id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    public static String findJobSequence(ArrayList<Job> jobs, int size) {
        Boolean[] slots = new Boolean[size];
        Arrays.fill(slots, Boolean.FALSE);

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            for (int j = jobs.get(i).deadline - 1; j >= 0; j--) {
                if (!slots[j]) {
                    result[j] = i;
                    slots[j] = Boolean.TRUE;
                    break;
                }
            }
        }

        StringBuilder jobSequenceBuilder = new StringBuilder();
        jobSequenceBuilder.append("Job Sequence: ");
        for (int i = 0; i < jobs.size(); i++) {
            if (slots[i]) {
                jobSequenceBuilder.append(jobs.get(result[i]).id).append(" -> ");
            }
        }

        if (jobSequenceBuilder.length() >= 4) {
            jobSequenceBuilder.setLength(jobSequenceBuilder.length() - 4);
        }

        return jobSequenceBuilder.toString();
    }
}
