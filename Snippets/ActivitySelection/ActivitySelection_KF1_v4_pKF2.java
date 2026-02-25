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
public final class IntervalScheduler {

    private IntervalScheduler() {
        // Prevent instantiation
    }

    /**
     * Selects a maximum-size subset of non-overlapping intervals using a greedy strategy.
     *
     * Assumptions:
     * - {@code starts} and {@code ends} have the same length.
     * - {@code starts[i]} and {@code ends[i]} represent the start and end of the i-th interval.
     *
     * Algorithm:
     * 1. Build an array of intervals [index, start, end].
     * 2. Sort intervals by their end time.
     * 3. Iteratively pick the earliest finishing interval that does not overlap
     *    with the last selected one.
     *
     * @param starts array of start times
     * @param ends   array of end times
     * @return a list of indices of the selected non-overlapping intervals
     * @throws IllegalArgumentException if input arrays are null or have different lengths
     */
    public static ArrayList<Integer> selectNonOverlappingIntervals(int[] starts, int[] ends) {
        validateInput(starts, ends);

        int n = starts.length;
        ArrayList<Integer> selectedIndices = new ArrayList<>();

        if (n == 0) {
            return selectedIndices;
        }

        int[][] intervals = buildIntervals(starts, ends);
        sortByEndTime(intervals);

        int firstIntervalIndex = intervals[0][0];
        int firstIntervalEnd = intervals[0][2];

        selectedIndices.add(firstIntervalIndex);
        int lastEndTime = firstIntervalEnd;

        for (int i = 1; i < n; i++) {
            int currentStart = intervals[i][1];
            int currentEnd = intervals[i][2];

            if (currentStart >= lastEndTime) {
                selectedIndices.add(intervals[i][0]);
                lastEndTime = currentEnd;
            }
        }

        return selectedIndices;
    }

    private static void validateInput(int[] starts, int[] ends) {
        if (starts == null || ends == null) {
            throw new IllegalArgumentException("Start and end arrays must not be null.");
        }
        if (starts.length != ends.length) {
            throw new IllegalArgumentException("Start and end arrays must have the same length.");
        }
    }

    /**
     * Builds an array of intervals where each interval is represented as:
     * [originalIndex, startTime, endTime].
     */
    private static int[][] buildIntervals(int[] starts, int[] ends) {
        int n = starts.length;
        int[][] intervals = new int[n][3];

        for (int i = 0; i < n; i++) {
            intervals[i][0] = i;
            intervals[i][1] = starts[i];
            intervals[i][2] = ends[i];
        }

        return intervals;
    }

    /**
     * Sorts intervals in ascending order of their end time.
     * Each interval is represented as [index, start, end].
     */
    private static void sortByEndTime(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[2]));
    }
}