package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class for solving the Activity Selection problem using a greedy
 * algorithm.
 *
 * Problem reference: https://en.wikipedia.org/wiki/Activity_selection_problem
 */
public final class ActivitySelection {

    private ActivitySelection() {
        // Prevent instantiation
    }

    /**
     * Selects the maximum number of non-overlapping activities.
     *
     * Activities are identified by their indices in the input arrays. An activity
     * is defined by a start time and an end time. The method returns the indices
     * of a maximum-size subset of mutually compatible (non-overlapping) activities.
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
            activities[i][0] = i;
            activities[i][1] = startTimes[i];
            activities[i][2] = endTimes[i];
        }

        return activities;
    }

    /**
     * Sorts activities in-place by their end time in ascending order.
     */
    private static void sortActivitiesByEndTime(int[][] activities) {
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));
    }

    /**
     * Greedily selects a maximum-size subset of non-overlapping activities.
     */
    private static ArrayList<Integer> selectNonOverlappingActivities(int[][] activities) {
        ArrayList<Integer> selectedActivities = new ArrayList<>();

        selectedActivities.add(activities[0][0]);
        int lastEndTime = activities[0][2];

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