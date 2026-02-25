package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
    public static List<Integer> activitySelection(int[] startTimes, int[] endTimes) {
        validateInput(startTimes, endTimes);

        if (startTimes.length == 0) {
            return new ArrayList<>();
        }

        Activity[] activities = buildActivities(startTimes, endTimes);
        sortByEndTime(activities);

        return pickNonOverlappingActivities(activities);
    }

    private static void validateInput(int[] startTimes, int[] endTimes) {
        if (startTimes == null || endTimes == null) {
            throw new IllegalArgumentException("Start times and end times must not be null.");
        }
        if (startTimes.length != endTimes.length) {
            throw new IllegalArgumentException("Start times and end times must have the same length.");
        }
    }

    private static Activity[] buildActivities(int[] startTimes, int[] endTimes) {
        int n = startTimes.length;
        Activity[] activities = new Activity[n];

        for (int i = 0; i < n; i++) {
            activities[i] = new Activity(i, startTimes[i], endTimes[i]);
        }

        return activities;
    }

    private static void sortByEndTime(Activity[] activities) {
        Arrays.sort(activities, Comparator.comparingInt(Activity::endTime));
    }

    private static List<Integer> pickNonOverlappingActivities(Activity[] activities) {
        List<Integer> selectedIndices = new ArrayList<>();
        int lastEndTime = activities[0].endTime();
        selectedIndices.add(activities[0].index());

        for (int i = 1; i < activities.length; i++) {
            Activity current = activities[i];
            if (current.startTime() >= lastEndTime) {
                selectedIndices.add(current.index());
                lastEndTime = current.endTime();
            }
        }

        return selectedIndices;
    }

    private record Activity(int index, int startTime, int endTime) {}
}