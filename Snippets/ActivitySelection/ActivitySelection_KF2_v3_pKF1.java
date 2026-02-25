package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public final class ActivitySelection {

    private ActivitySelection() {
    }

    public static ArrayList<Integer> selectActivities(int[] startTimes, int[] endTimes) {
        int activityCount = startTimes.length;
        int[][] activities = new int[activityCount][3];

        for (int i = 0; i < activityCount; i++) {
            activities[i][0] = i;               // original index
            activities[i][1] = startTimes[i];   // start time
            activities[i][2] = endTimes[i];     // end time
        }

        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedActivityIndices = new ArrayList<>();
        selectedActivityIndices.add(activities[0][0]);
        int lastSelectedEndTime = activities[0][2];

        for (int i = 1; i < activityCount; i++) {
            int currentStartTime = activities[i][1];
            int currentEndTime = activities[i][2];

            if (currentStartTime >= lastSelectedEndTime) {
                selectedActivityIndices.add(activities[i][0]);
                lastSelectedEndTime = currentEndTime;
            }
        }

        return selectedActivityIndices;
    }
}