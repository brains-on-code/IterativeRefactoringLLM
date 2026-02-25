package com.thealgorithms.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class for interval scheduling using a greedy algorithm.
 *
 * Given arrays of start times and end times, this class provides a method
 * to select a maximum-size subset of non-overlapping intervals.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Selects a maximum-size subset of non-overlapping intervals using a greedy strategy.
     *
     * The method assumes:
     * - {@code starts} and {@code ends} have the same length.
     * - {@code starts[i]} and {@code ends[i]} represent the start and end of the i-th interval.
     *
     * The algorithm:
     * 1. Builds an array of intervals [index, start, end].
     * 2. Sorts intervals by their end time.
     * 3. Iteratively picks the earliest finishing interval that does not overlap
     *    with the last selected one.
     *
     * @param starts array of start times
     * @param ends   array of end times
     * @return a list of indices of the selected non-overlapping intervals
     */
    public static ArrayList<Integer> method1(int[] starts, int[] ends) {
        int n = starts.length;

        // Each interval: [originalIndex, start, end]
        int[][] intervals = new int[n][3];

        for (int i = 0; i < n; i++) {
            intervals[i][0] = i;          // original index
            intervals[i][1] = starts[i];  // start time
            intervals[i][2] = ends[i];    // end time
        }

        // Sort intervals by end time (ascending)
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[2]));

        ArrayList<Integer> selectedIndices = new ArrayList<>();
        int lastEndTime = intervals[0][2];

        // Always select the first interval (earliest finishing)
        selectedIndices.add(intervals[0][0]);

        // Select subsequent non-overlapping intervals
        for (int i = 1; i < n; i++) {
            if (intervals[i][1] >= lastEndTime) {
                selectedIndices.add(intervals[i][0]);
                lastEndTime = intervals[i][2];
            }
        }

        return selectedIndices;
    }
}