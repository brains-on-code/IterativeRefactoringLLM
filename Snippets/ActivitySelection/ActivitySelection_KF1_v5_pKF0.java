package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Selects a maximum-size set of non-overlapping intervals using a greedy strategy.
     *
     * @param startTimes array of start times
     * @param endTimes   array of end times
     * @return list of indices of the selected intervals
     * @throws IllegalArgumentException if input arrays are null, have different lengths, or are empty
     */
    public static List<Integer> method1(int[] startTimes, int[] endTimes) {
        validateInput(startTimes, endTimes);

        int[][] activities = buildActivities(startTimes, endTimes);
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
        if (startTimes.length == 0) {
            throw new IllegalArgumentException("Start times and end times must not be empty.");
        }
    }

    /**
     * Builds a 2D array of activities where each activity is represented as:
     * [originalIndex, startTime, endTime].
     */
    private static int[][] buildActivities(int[] startTimes, int[] endTimes) {
        int activityCount = startTimes.length;
        int[][] activities = new int[activityCount][3];

        for (int i = 0; i < activityCount; i++) {
            activities[i][0] = i;
            activities[i][1] = startTimes[i];
            activities[i][2] = endTimes[i];
        }

        return activities;
    }

    private static void sortActivitiesByEndTime(int[][] activities) {
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));
    }

    private static List<Integer> selectNonOverlappingActivities(int[][] activities) {
        List<Integer> selectedIndices = new ArrayList<>();
        int lastEndTime = activities[0][2];
        selectedIndices.add(activities[0][0]);

        for (int i = 1; i < activities.length; i++) {
            int startTime = activities[i][1];
            int endTime = activities[i][2];

            if (startTime >= lastEndTime) {
                selectedIndices.add(activities[i][0]);
                lastEndTime = endTime;
            }
        }

        return selectedIndices;
    }
}