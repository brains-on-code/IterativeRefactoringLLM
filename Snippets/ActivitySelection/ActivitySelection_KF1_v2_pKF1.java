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
        int numberOfActivities = startTimes.length;

        int[][] activities = new int[numberOfActivities][3];

        for (int activityIndex = 0; activityIndex < numberOfActivities; activityIndex++) {
            activities[activityIndex][0] = activityIndex;              // original index
            activities[activityIndex][1] = startTimes[activityIndex];  // start time
            activities[activityIndex][2] = endTimes[activityIndex];    // end time
        }

        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedActivityIndices = new ArrayList<>();
        selectedActivityIndices.add(activities[0][0]);
        int lastSelectedEndTime = activities[0][2];

        for (int activityIndex = 1; activityIndex < numberOfActivities; activityIndex++) {
            int currentStartTime = activities[activityIndex][1];
            int currentEndTime = activities[activityIndex][2];

            if (currentStartTime >= lastSelectedEndTime) {
                selectedActivityIndices.add(activities[activityIndex][0]);
                lastSelectedEndTime = currentEndTime;
            }
        }

        return selectedActivityIndices;
    }
}