package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Selects a maximum-size set of non-overlapping intervals using a greedy strategy.
     *
     * @param startTimes array of start times
     * @param endTimes   array of end times
     * @return list of indices of the selected intervals
     */
    public static ArrayList<Integer> method1(int[] startTimes, int[] endTimes) {
        int n = startTimes.length;

        // Each activity: [originalIndex, startTime, endTime]
        int[][] activities = new int[n][3];

        for (int i = 0; i < n; i++) {
            activities[i][0] = i;
            activities[i][1] = startTimes[i];
            activities[i][2] = endTimes[i];
        }

        // Sort by end time (ascending)
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedIndices = new ArrayList<>();
        int lastEndTime = activities[0][2];

        selectedIndices.add(activities[0][0]);

        for (int i = 1; i < n; i++) {
            int startTime = activities[i][1];
            int endTime = activities[i][2];

            if (startTime >= lastEndTime) {
                selectedIndices.add(activities[i][0]);
                lastEndTime = endTime;
            }
        }

        return selectedIndices;
    }
}