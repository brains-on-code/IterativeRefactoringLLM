package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public final class ActivitySelection {

    private ActivitySelection() {
        // Utility class; prevent instantiation
    }

    /**
     * Selects the maximum number of non-overlapping activities using a greedy approach.
     *
     * @param startTimes array of activity start times
     * @param endTimes   array of activity end times
     * @return list of indices of selected activities
     * @throws IllegalArgumentException if input arrays are null or of different lengths
     */
    public static ArrayList<Integer> activitySelection(int[] startTimes, int[] endTimes) {
        validateInput(startTimes, endTimes);

        int n = startTimes.length;
        if (n == 0) {
            return new ArrayList<>();
        }

        int[][] activities = buildActivitiesArray(startTimes, endTimes);
        sortActivitiesByEndTime(activities);

        return selectNonOverlappingActivities(activities);
    }

    private static void validateInput(int[] startTimes, int[] endTimes) {
        if (startTimes == null || endTimes == null) {
            throw new IllegalArgumentException("Start times and end times must not be null.");
        }
        if (startTimes.length != endTimes.length) {
            throw new IllegalArgumentException("Start times and end times must have the same length.");
        }
    }

    private static int[][] buildActivitiesArray(int[] startTimes, int[] endTimes) {
        int n = startTimes.length;
        int[][] activities = new int[n][3];

        for (int i = 0; i < n; i++) {
            activities[i][0] = i;              // activity index
            activities[i][1] = startTimes[i];  // start time
            activities[i][2] = endTimes[i];    // end time
        }

        return activities;
    }

    private static void sortActivitiesByEndTime(int[][] activities) {
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));
    }

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