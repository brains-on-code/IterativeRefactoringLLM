package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Greedy solution to the Activity Selection problem.
 *
 * Problem reference: https://en.wikipedia.org/wiki/Activity_selection_problem
 */
public final class ActivitySelection {

    private static final int INDEX_COLUMN = 0;
    private static final int START_COLUMN = 1;
    private static final int END_COLUMN = 2;

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

        int[][] activities = buildActivities(startTimes, endTimes);
        sortByEndTime(activities);

        return selectActivities(activities);
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
     * Builds a 2D array where each row is:
     * [originalIndex, startTime, endTime].
     */
    private static int[][] buildActivities(int[] startTimes, int[] endTimes) {
        int n = startTimes.length;
        int[][] activities = new int[n][3];

        for (int i = 0; i < n; i++) {
            activities[i][INDEX_COLUMN] = i;
            activities[i][START_COLUMN] = startTimes[i];
            activities[i][END_COLUMN] = endTimes[i];
        }

        return activities;
    }

    private static void sortByEndTime(int[][] activities) {
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[END_COLUMN]));
    }

    private static ArrayList<Integer> selectActivities(int[][] activities) {
        ArrayList<Integer> selected = new ArrayList<>();

        selected.add(activities[0][INDEX_COLUMN]);
        int lastEndTime = activities[0][END_COLUMN];

        for (int i = 1; i < activities.length; i++) {
            int startTime = activities[i][START_COLUMN];
            int endTime = activities[i][END_COLUMN];

            if (startTime >= lastEndTime) {
                selected.add(activities[i][INDEX_COLUMN]);
                lastEndTime = endTime;
            }
        }

        return selected;
    }
}