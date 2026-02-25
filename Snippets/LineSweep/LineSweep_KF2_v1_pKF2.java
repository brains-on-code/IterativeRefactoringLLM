package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;

public final class LineSweep {

    private LineSweep() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the maximum end point among all given ranges.
     *
     * @param ranges an array of [start, end] intervals
     * @return the largest end value
     */
    public static int findMaximumEndPoint(int[][] ranges) {
        Arrays.sort(ranges, Comparator.comparingInt(range -> range[1]));
        return ranges[ranges.length - 1][1];
    }

    /**
     * Determines whether any of the given ranges overlap.
     *
     * Uses a line sweep (difference array) approach:
     * - Increment at each range start
     * - Decrement just after each range end
     * - Accumulate to find how many ranges cover each point
     *
     * @param ranges an array of [start, end] intervals
     * @return true if at least two ranges overlap, false otherwise
     */
    public static boolean isOverlap(int[][] ranges) {
        if (ranges == null || ranges.length == 0) {
            return false;
        }

        int maximumEndPoint = findMaximumEndPoint(ranges);
        int[] numberLine = new int[maximumEndPoint + 2];

        for (int[] range : ranges) {
            int start = range[0];
            int end = range[1];

            numberLine[start] += 1;
            numberLine[end + 1] -= 1;
        }

        int currentCount = 0;
        int maxOverlaps = 0;

        for (int delta : numberLine) {
            currentCount += delta;
            maxOverlaps = Math.max(maxOverlaps, currentCount);
        }

        return maxOverlaps > 1;
    }
}