package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class implementing a line sweep algorithm for range operations.
 *
 * <p>Overlapping ranges satisfy:
 * (startA <= endB) AND (endA >= startB)
 *
 * <p>References:
 * - https://en.wikipedia.org/wiki/Sweep_line_algorithm
 * - https://en.wikipedia.org/wiki/De_Morgan%27s_laws
 */
public final class LineSweep {

    private LineSweep() {
        // Prevent instantiation
    }

    /**
     * Returns the maximum end value among all ranges.
     *
     * @param ranges array of ranges, each represented as [start, end]
     * @return maximum end value
     * @throws IllegalArgumentException if ranges is null or empty
     */
    public static int findMaximumEndPoint(int[][] ranges) {
        if (ranges == null || ranges.length == 0) {
            throw new IllegalArgumentException("ranges must not be null or empty");
        }

        Arrays.sort(ranges, Comparator.comparingInt(range -> range[1]));
        return ranges[ranges.length - 1][1];
    }

    /**
     * Checks whether any of the given ranges overlap.
     *
     * <p>Uses a difference-array based line sweep:
     * - Increment at range start
     * - Decrement just after range end
     * - Prefix sum to find active range count at each point
     *
     * @param ranges array of ranges, each represented as [start, end]
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

        int activeRanges = 0;
        int maxActiveRanges = 0;

        for (int delta : numberLine) {
            activeRanges += delta;
            maxActiveRanges = Math.max(maxActiveRanges, activeRanges);
        }

        return maxActiveRanges > 1;
    }
}