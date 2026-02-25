package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public final class ActivitySelection {

    private ActivitySelection() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the indices of a maximum-size subset of non-overlapping activities.
     * Activities are chosen using a greedy strategy based on earliest finishing time.
     *
     * @param startTimes start time of each activity
     * @param endTimes   end time of each activity
     * @return list of indices of the selected activities (indices refer to the original arrays)
     */
    public static ArrayList<Integer> activitySelection(int[] startTimes, int[] endTimes) {
        int n = startTimes.length;

        // Each activity: [originalIndex, startTime, endTime]
        int[][] activities = new int[n][3];
        for (int i = 0; i < n; i++) {
            activities[i][0] = i;
            activities[i][1] = startTimes[i];
            activities[i][2] = endTimes[i];
        }

        // Sort activities by end time (ascending)
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedActivities = new ArrayList<>();
        int lastEndTime = activities[0][2];
        selectedActivities.add(activities[0][0]);

        // Greedily pick the next activity that starts after or when the last one ends
        for (int i = 1; i < n; i++) {
            int startTime = activities[i][1];
            int endTime = activities[i][2];

            if (startTime >= lastEndTime) {
                selectedActivities.add(activities[i][0]);
                lastEndTime = endTime;
            }
        }

        return selectedActivities;
    }
}