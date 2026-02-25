package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Solves the Activity Selection problem using a greedy algorithm.
 *
 * Problem reference: https://en.wikipedia.org/wiki/Activity_selection_problem
 */
public final class ActivitySelection {

    private static final int INDEX_ORIGINAL = 0;
    private static final int INDEX_START = 1;
    private static final int INDEX_END = 2;

    private ActivitySelection() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the indices of a maximum-size subset of non-overlapping activities.
     *
     * Each activity is defined by a start time and an end time at the same index
     * in the input arrays.
     *
     * @param startTimes start times of the activities
     * @param endTimes   end times of the activities
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
            throw new IllegalArgumentException("Start and end time arrays must not be null.");
        }
        if (startTimes.length != endTimes.length) {
            throw new IllegalArgumentException("Start and end time arrays must have the same length.");
        }
    }

    /**
     * Builds a 2D array where each row represents an activity:
     * [originalIndex, startTime, endTime].
     */
    private static int[][] buildActivitiesArray(int[] startTimes, int[] endTimes) {
        int n = startTimes.length;
        int[][] activities = new int[n][3];

        for (int i = 0; i < n; i++) {
            activities[i][INDEX_ORIGINAL] = i;
            activities[i][INDEX_START] = startTimes[i];
            activities[i][INDEX_END] = endTimes[i];
        }

        return activities;
    }

    /**
     * Sorts activities in-place by their end time in ascending order.
     */
    private static void sortActivitiesByEndTime(int[][] activities) {
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[INDEX_END]));
    }

    /**
     * Greedily selects a maximum-size subset of non-overlapping activities.
     */
    private static ArrayList<Integer> selectNonOverlappingActivities(int[][] activities) {
        ArrayList<Integer> selectedActivities = new ArrayList<>();

        selectedActivities.add(activities[0][INDEX_ORIGINAL]);
        int lastEndTime = activities[0][INDEX_END];

        for (int i = 1; i < activities.length; i++) {
            int startTime = activities[i][INDEX_START];
            int endTime = activities[i][INDEX_END];

            if (startTime >= lastEndTime) {
                selectedActivities.add(activities[i][INDEX_ORIGINAL]);
                lastEndTime = endTime;
            }
        }

        return selectedActivities;
    }
}