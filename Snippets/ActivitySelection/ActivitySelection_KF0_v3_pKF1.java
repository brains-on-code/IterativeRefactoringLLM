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
        int activityCount = startTimes.length;

        // Each activity: [originalIndex, startTime, endTime]
        int[][] activities = new int[activityCount][3];

        for (int i = 0; i < activityCount; i++) {
            activities[i][0] = i;
            activities[i][1] = startTimes[i];
            activities[i][2] = endTimes[i];
        }

        // Sort by end time (ascending)
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedActivityIndices = new ArrayList<>();
        int lastSelectedEndTime = activities[0][2];

        selectedActivityIndices.add(activities[0][0]);

        for (int i = 1; i < activityCount; i++) {
            int currentStartTime = activities[i][1];
            int currentEndTime = activities[i][2];

            if (currentStartTime >= lastSelectedEndTime) {
                selectedActivityIndices.add(activities[i][0]);
                lastSelectedEndTime = currentEndTime;
            }
        }

        return selectedActivityIndices;
    }
}