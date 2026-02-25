package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class ActivitySelection {

    private ActivitySelection() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> activitySelection(int[] startTimes, int[] endTimes) {
        validateInput(startTimes, endTimes);

        int activityCount = startTimes.length;
        Activity[] activities = buildActivities(startTimes, endTimes, activityCount);

        Arrays.sort(activities, Comparator.comparingInt(a -> a.endTime));

        List<Integer> selectedActivities = new ArrayList<>();
        int lastEndTime = selectFirstActivity(activities, selectedActivities);

        selectRemainingActivities(activities, selectedActivities, lastEndTime);

        return selectedActivities;
    }

    private static void validateInput(int[] startTimes, int[] endTimes) {
        if (startTimes == null || endTimes == null) {
            throw new IllegalArgumentException("Start and end time arrays must not be null.");
        }
        if (startTimes.length != endTimes.length) {
            throw new IllegalArgumentException("Start and end time arrays must have the same length.");
        }
        if (startTimes.length == 0) {
            throw new IllegalArgumentException("At least one activity is required.");
        }
    }

    private static Activity[] buildActivities(int[] startTimes, int[] endTimes, int activityCount) {
        Activity[] activities = new Activity[activityCount];
        for (int i = 0; i < activityCount; i++) {
            activities[i] = new Activity(i, startTimes[i], endTimes[i]);
        }
        return activities;
    }

    private static int selectFirstActivity(Activity[] activities, List<Integer> selectedActivities) {
        Activity first = activities[0];
        selectedActivities.add(first.index);
        return first.endTime;
    }

    private static void selectRemainingActivities(
        Activity[] activities,
        List<Integer> selectedActivities,
        int initialLastEndTime
    ) {
        int lastEndTime = initialLastEndTime;
        for (int i = 1; i < activities.length; i++) {
            Activity current = activities[i];
            if (current.startTime >= lastEndTime) {
                selectedActivities.add(current.index);
                lastEndTime = current.endTime;
            }
        }
    }

    private static final class Activity {
        final int index;
        final int startTime;
        final int endTime;

        Activity(int index, int startTime, int endTime) {
            this.index = index;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
}