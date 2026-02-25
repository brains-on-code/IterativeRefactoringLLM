package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public final class ActivitySelection {

    private ActivitySelection() {
    }

    public static ArrayList<Integer> selectActivities(int[] startTimes, int[] endTimes) {
        int numberOfActivities = startTimes.length;
        int[][] activityData = new int[numberOfActivities][3];

        for (int activityIndex = 0; activityIndex < numberOfActivities; activityIndex++) {
            activityData[activityIndex][0] = activityIndex;          // original index
            activityData[activityIndex][1] = startTimes[activityIndex]; // start time
            activityData[activityIndex][2] = endTimes[activityIndex];   // end time
        }

        Arrays.sort(activityData, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedActivityIndices = new ArrayList<>();
        selectedActivityIndices.add(activityData[0][0]);
        int lastSelectedActivityEndTime = activityData[0][2];

        for (int activityIndex = 1; activityIndex < numberOfActivities; activityIndex++) {
            int currentActivityStartTime = activityData[activityIndex][1];
            int currentActivityEndTime = activityData[activityIndex][2];

            if (currentActivityStartTime >= lastSelectedActivityEndTime) {
                selectedActivityIndices.add(activityData[activityIndex][0]);
                lastSelectedActivityEndTime = currentActivityEndTime;
            }
        }

        return selectedActivityIndices;
    }
}