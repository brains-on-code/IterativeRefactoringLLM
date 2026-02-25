package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class for working with integer intervals.
 *
 * <p>Each interval is represented as an {@code int[]} of length 2:
 * {@code [start, end]} where {@code start <= end} and both are non‑negative.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns the maximum end value among the given intervals.
     *
     * @param intervals array of intervals, each as {@code [start, end]}
     * @return the largest {@code end} value in {@code intervals}
     * @throws IllegalArgumentException if {@code intervals} is {@code null} or empty
     */
    public static int method1(int[][] intervals) {
        validateIntervalsNotEmpty(intervals);

        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[1]));
        return intervals[intervals.length - 1][1];
    }

    /**
     * Determines whether any two intervals in the given array overlap.
     *
     * <p>An overlap exists if there is at least one point that belongs to
     * more than one interval.
     *
     * @param intervals array of intervals, each as {@code [start, end]}
     * @return {@code true} if at least two intervals overlap; {@code false} otherwise
     */
    public static boolean method2(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return false;
        }

        int maxEnd = method1(intervals);
        int[] diff = new int[maxEnd + 2];

        applyDifferenceArrayUpdates(intervals, diff);

        int maxActive = computeMaxActiveIntervals(diff);
        return maxActive > 1;
    }

    private static void validateIntervalsNotEmpty(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            throw new IllegalArgumentException("intervals must not be null or empty");
        }
    }

    /**
     * Applies the difference array technique:
     * increments at interval start and decrements after interval end.
     */
    private static void applyDifferenceArrayUpdates(int[][] intervals, int[] diff) {
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            diff[start] += 1;
            diff[end + 1] -= 1;
        }
    }

    /**
     * Computes the maximum number of active intervals at any point
     * using the prefix sum of the difference array.
     */
    private static int computeMaxActiveIntervals(int[] diff) {
        int active = 0;
        int maxActive = 0;

        for (int delta : diff) {
            active += delta;
            maxActive = Math.max(maxActive, active);
        }

        return maxActive;
    }
}