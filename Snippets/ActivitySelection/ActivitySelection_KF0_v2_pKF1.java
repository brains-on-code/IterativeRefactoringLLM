package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

// Problem Link: https://en.wikipedia.org/wiki/Activity_selection_problem

public final class ActivitySelection {

    private ActivitySelection() {
    }

    /**
     * Selects the maximum number of non-overlapping activities using a greedy approach.
     *
     * @param startTimes array containing the start times of the activities
     * @param endTimes   array containing the end times of the activities
     * @return a list of indices representing the selected activities
     */
    public static ArrayList<Integer> selectActivities(int[] startTimes, int[] endTimes) {
        int numberOfActivities = startTimes.length;

        // Each activity: [originalIndex, startTime, endTime]
        int[][] activities = new int[numberOfActivities][3];

        for (int activityIndex = 0; activityIndex < numberOfActivities; activityIndex++) {
            activities[activityIndex][0] = activityIndex;
            activities[activityIndex][1] = startTimes[activityIndex];
            activities[activityIndex][2] = endTimes[activityIndex];
        }

        // Sort by end time (ascending)
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedActivities = new ArrayList<>();
        int lastSelectedActivityEndTime = activities[0][2];

        selectedActivities.add(activities[0][0]);

        for (int activityIndex = 1; activityIndex < numberOfActivities; activityIndex++) {
            int currentActivityStartTime = activities[activityIndex][1];
            int currentActivityEndTime = activities[activityIndex][2];

            if (currentActivityStartTime >= lastSelectedActivityEndTime) {
                selectedActivities.add(activities[activityIndex][0]);
                lastSelectedActivityEndTime = currentActivityEndTime;
            }
        }

        return selectedActivities;
    }
}