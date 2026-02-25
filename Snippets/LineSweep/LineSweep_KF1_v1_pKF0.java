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
     */
    public static int getMaxEnd(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[1]));
        return intervals[intervals.length - 1][1];
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
        int[] prefixDiff = new int[maxEnd + 2];

        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            prefixDiff[start] += 1;
            prefixDiff[end + 1] -= 1;
        }

        int currentActive = 0;
        int maxActive = 0;
        for (int delta : prefixDiff) {
            currentActive += delta;
            maxActive = Math.max(maxActive, currentActive);
        }

        return maxActive > 1;
    }
}