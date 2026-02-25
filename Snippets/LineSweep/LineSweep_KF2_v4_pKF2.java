package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;

public final class LineSweep {

    private LineSweep() {
        // Prevent instantiation
    }

    /**
     * Returns the maximum end value among all given ranges.
     *
     * @param ranges array of intervals, where each interval is [start, end]
     * @return the largest end value
     */
    public static int findMaximumEndPoint(int[][] ranges) {
        Arrays.sort(ranges, Comparator.comparingInt(range -> range[1]));
        return ranges[ranges.length - 1][1];
    }

    /**
     * Determines whether any of the given ranges overlap using a line sweep
     * (difference array) technique.
     *
     * @param ranges array of intervals, where each interval is [start, end]
     * @return true if at least two ranges overlap; false otherwise
     */
    public static boolean isOverlap(int[][] ranges) {
        if (ranges == null || ranges.length == 0) {
            return false;
        }

        int maximumEndPoint = findMaximumEndPoint(ranges);
        int[] numberLine = new int[maximumEndPoint + 2];

        // Build difference array: +1 at start, -1 after end
        for (int[] range : ranges) {
            int start = range[0];
            int end = range[1];

            numberLine[start]++;
            numberLine[end + 1]--;
        }

        int currentCoverage = 0;
        int maxCoverage = 0;

        // Prefix sum to compute coverage at each point
        for (int delta : numberLine) {
            currentCoverage += delta;
            maxCoverage = Math.max(maxCoverage, currentCoverage);
        }

        return maxCoverage > 1;
    }
}