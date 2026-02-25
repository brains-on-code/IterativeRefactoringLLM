package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public final class ActivitySelector {

    private ActivitySelector() {
    }

    /**
     * Selects a maximum set of non-overlapping intervals using a greedy strategy.
     *
     * @param startTimes array of start times
     * @param endTimes   array of end times
     * @return list of indices of the selected intervals
     */
    public static ArrayList<Integer> selectMaxNonOverlappingActivities(int[] startTimes, int[] endTimes) {
        int activityCount = startTimes.length;

        int[][] activityData = new int[activityCount][3];

        for (int i = 0; i < activityCount; i++) {
            activityData[i][0] = i;               // original index
            activityData[i][1] = startTimes[i];   // start time
            activityData[i][2] = endTimes[i];     // end time
        }

        Arrays.sort(activityData, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedActivityIndices = new ArrayList<>();
        selectedActivityIndices.add(activityData[0][0]);
        int lastSelectedEndTime = activityData[0][2];

        for (int i = 1; i < activityCount; i++) {
            int currentStartTime = activityData[i][1];
            int currentEndTime = activityData[i][2];

            if (currentStartTime >= lastSelectedEndTime) {
                selectedActivityIndices.add(activityData[i][0]);
                lastSelectedEndTime = currentEndTime;
            }
        }

        return selectedActivityIndices;
    }
}