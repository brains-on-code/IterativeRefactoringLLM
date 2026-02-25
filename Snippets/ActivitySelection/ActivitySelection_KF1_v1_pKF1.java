package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public final class Class1 {

    private Class1() {
    }

    /**
     * Selects a maximum set of non-overlapping intervals using a greedy strategy.
     *
     * @param startTimes array of start times
     * @param endTimes   array of end times
     * @return list of indices of the selected intervals
     */
    public static ArrayList<Integer> method1(int[] startTimes, int[] endTimes) {
        int activityCount = startTimes.length;

        int[][] activities = new int[activityCount][3];

        for (int i = 0; i < activityCount; i++) {
            activities[i][0] = i;              // original index
            activities[i][1] = startTimes[i];  // start time
            activities[i][2] = endTimes[i];    // end time
        }

        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedActivityIndices = new ArrayList<>();
        selectedActivityIndices.add(activities[0][0]);
        int lastSelectedEndTime = activities[0][2];

        for (int i = 1; i < activityCount; i++) {
            if (activities[i][1] >= lastSelectedEndTime) {
                selectedActivityIndices.add(activities[i][0]);
                lastSelectedEndTime = activities[i][2];
            }
        }

        return selectedActivityIndices;
    }
}