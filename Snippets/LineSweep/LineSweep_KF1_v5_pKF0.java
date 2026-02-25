package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class for interval-based operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns the maximum end value among the given intervals.
     *
     * @param intervals array of intervals, where each interval is [start, end]
     * @return the maximum end value
     * @throws IllegalArgumentException if intervals is null or empty
     */
    public static int getMaxEnd(int[][] intervals) {
        validateIntervalsNotEmpty(intervals);

        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[1]));
        int lastIndex = intervals.length - 1;
        return intervals[lastIndex][1];
    }

    /**
     * Determines whether any of the given intervals overlap.
     *
     * @param intervals array of intervals, where each interval is [start, end]
     * @return {@code true} if there is at least one overlap; {@code false} otherwise
     */
    public static boolean hasOverlap(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return false;
        }

        int maxEnd = getMaxEnd(intervals);
        int[] differenceArray = new int[maxEnd + 2];

        applyIntervalDifferences(intervals, differenceArray);

        return getMaxActiveIntervals(differenceArray) > 1;
    }

    private static void validateIntervalsNotEmpty(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            throw new IllegalArgumentException("Intervals must not be null or empty");
        }
    }

    private static void applyIntervalDifferences(int[][] intervals, int[] differenceArray) {
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            differenceArray[start]++;
            differenceArray[end + 1]--;
        }
    }

    private static int getMaxActiveIntervals(int[] differenceArray) {
        int currentActive = 0;
        int maxActive = 0;

        for (int delta : differenceArray) {
            currentActive += delta;
            maxActive = Math.max(maxActive, currentActive);
        }

        return maxActive;
    }
}