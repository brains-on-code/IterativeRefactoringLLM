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

        for (int index = 0; index < activityCount; index++) {
            activities[index][0] = index;
            activities[index][1] = startTimes[index];
            activities[index][2] = endTimes[index];
        }

        // Sort by end time (ascending)
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[2]));

        ArrayList<Integer> selectedActivities = new ArrayList<>();
        int lastEndTime = activities[0][2];

        selectedActivities.add(activities[0][0]);

        for (int i = 1; i < activityCount; i++) {
            int currentStartTime = activities[i][1];
            int currentEndTime = activities[i][2];

            if (currentStartTime >= lastEndTime) {
                selectedActivities.add(activities[i][0]);
                lastEndTime = currentEndTime;
            }
        }

        return selectedActivities;
    }
}