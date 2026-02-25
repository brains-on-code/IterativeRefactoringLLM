package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public final class ActivitySelection {

    private ActivitySelection() {
        // Utility class; prevent instantiation
    }

    /**
     * Selects a maximum-size subset of non-overlapping activities using a greedy
     * strategy that always picks the activity with the earliest finishing time next.
     *
     * @param startTimes start time of each activity
     * @param endTimes   end time of each activity
     * @return list of indices of the selected activities (indices refer to the original arrays)
     * @throws IllegalArgumentException if input arrays are null, of different lengths, or empty
     */
    public static ArrayList<Integer> activitySelection(int[] startTimes, int[] endTimes) {
        validateInput(startTimes, endTimes);

        int n = startTimes.length;

        int[][] activities = buildActivitiesArray(startTimes, endTimes, n);

        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));

        return selectNonOverlappingActivities(activities);
    }

    private static void validateInput(int[] startTimes, int[] endTimes) {
        if (startTimes == null || endTimes == null) {
            throw new IllegalArgumentException("Start and end time arrays must not be null.");
        }
        if (startTimes.length != endTimes.length) {
            throw new IllegalArgumentException("Start and end time arrays must have the same length.");
        }
        if (startTimes.length == 0) {
            throw new IllegalArgumentException("Start and end time arrays must not be empty.");
        }
    }

    /**
     * Builds a 2D array where each row represents an activity:
     * [0] = original index, [1] = start time, [2] = end time.
     */
    private static int[][] buildActivitiesArray(int[] startTimes, int[] endTimes, int n) {
        int[][] activities = new int[n][3];
        for (int i = 0; i < n; i++) {
            activities[i][0] = i;
            activities[i][1] = startTimes[i];
            activities[i][2] = endTimes[i];
        }
        return activities;
    }

    /**
     * Greedily selects a maximum-size subset of non-overlapping activities
     * from a list sorted by end time.
     */
    private static ArrayList<Integer> selectNonOverlappingActivities(int[][] activities) {
        ArrayList<Integer> selectedActivities = new ArrayList<>();

        int lastEndTime = activities[0][2];
        selectedActivities.add(activities[0][0]);

        for (int i = 1; i < activities.length; i++) {
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