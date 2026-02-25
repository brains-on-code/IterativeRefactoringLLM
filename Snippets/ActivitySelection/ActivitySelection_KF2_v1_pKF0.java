package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public final class ActivitySelection {

    private ActivitySelection() {
        // Utility class; prevent instantiation
    }

    public static ArrayList<Integer> activitySelection(int[] startTimes, int[] endTimes) {
        int activityCount = startTimes.length;
        int[][] activities = buildActivitiesArray(startTimes, endTimes, activityCount);

        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedActivities = new ArrayList<>();
        int lastEndTime = selectFirstActivity(activities, selectedActivities);

        selectRemainingActivities(activities, selectedActivities, lastEndTime);

        return selectedActivities;
    }

    private static int[][] buildActivitiesArray(int[] startTimes, int[] endTimes, int activityCount) {
        int[][] activities = new int[activityCount][3];
        for (int i = 0; i < activityCount; i++) {
            activities[i][0] = i;              // original index
            activities[i][1] = startTimes[i];  // start time
            activities[i][2] = endTimes[i];    // end time
        }
        return activities;
    }

    private static int selectFirstActivity(int[][] activities, ArrayList<Integer> selectedActivities) {
        selectedActivities.add(activities[0][0]);
        return activities[0][2];
    }

    private static void selectRemainingActivities(
        int[][] activities,
        ArrayList<Integer> selectedActivities,
        int initialLastEndTime
    ) {
        int lastEndTime = initialLastEndTime;
        for (int i = 1; i < activities.length; i++) {
            int startTime = activities[i][1];
            int endTime = activities[i][2];

            if (startTime >= lastEndTime) {
                selectedActivities.add(activities[i][0]);
                lastEndTime = endTime;
            }
        }
    }
}