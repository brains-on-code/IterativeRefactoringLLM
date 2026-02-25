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
        if (startTimes == null || endTimes == null) {
            throw new IllegalArgumentException("Start and end time arrays must not be null.");
        }
        if (startTimes.length != endTimes.length) {
            throw new IllegalArgumentException("Start and end time arrays must have the same length.");
        }

        int n = startTimes.length;
        if (n == 0) {
            return new ArrayList<>();
        }

        // activities[i] = {originalIndex, startTime, endTime}
        int[][] activities = new int[n][3];
        for (int i = 0; i < n; i++) {
            activities[i][0] = i;
            activities[i][1] = startTimes[i];
            activities[i][2] = endTimes[i];
        }

        // Sort by end time (ascending)
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedActivities = new ArrayList<>();
        selectedActivities.add(activities[0][0]);
        int lastEndTime = activities[0][2];

        // Greedily pick the next activity with start time >= last selected end time
        for (int i = 1; i < n; i++) {
            if (activities[i][1] >= lastEndTime) {
                selectedActivities.add(activities[i][0]);
                lastEndTime = activities[i][2];
            }
        }

        return selectedActivities;
    }
}