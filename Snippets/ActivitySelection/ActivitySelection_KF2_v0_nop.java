package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public final class ActivitySelection {

    private ActivitySelection() {
    }


    public static ArrayList<Integer> activitySelection(int[] startTimes, int[] endTimes) {
        int n = startTimes.length;

        int[][] activities = new int[n][3];

        for (int i = 0; i < n; i++) {
            activities[i][0] = i;
            activities[i][1] = startTimes[i];
            activities[i][2] = endTimes[i];
        }

        Arrays.sort(activities, Comparator.comparingDouble(activity -> activity[2]));
        int lastEndTime;
        ArrayList<Integer> selectedActivities = new ArrayList<>();

        selectedActivities.add(activities[0][0]);
        lastEndTime = activities[0][2];

        for (int i = 1; i < n; i++) {
            if (activities[i][1] >= lastEndTime) {
                selectedActivities.add(activities[i][0]);
                lastEndTime = activities[i][2];
            }
        }

        return selectedActivities;
    }
}
