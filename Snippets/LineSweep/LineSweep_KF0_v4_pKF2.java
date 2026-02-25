package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class implementing a line sweep algorithm for range operations.
 *
 * <p>Two ranges [startA, endA] and [startB, endB] overlap if:
 * (startA <= endB) AND (endA >= startB)
 *
 * <p>References:
 * <ul>
 *   <li>https://en.wikipedia.org/wiki/Sweep_line_algorithm</li>
 *   <li>https://en.wikipedia.org/wiki/De_Morgan%27s_laws</li>
 * </ul>
 */
public final class LineSweep {

    private LineSweep() {
        // Utility class; prevent instantiation
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
     * Determines whether any of the given ranges overlap.
     *
     * <p>Algorithm (difference-array based line sweep):
     * <ol>
     *   <li>For each range [start, end], increment at index {@code start}.</li>
     *   <li>Decrement at index {@code end + 1}.</li>
     *   <li>Compute the prefix sum over this array to get the number of active ranges at each point.</li>
     * </ol>
     * If at any point the number of active ranges exceeds 1, an overlap exists.
     *
     * @param ranges array of ranges, each represented as [start, end]
     * @return {@code true} if at least two ranges overlap; {@code false} otherwise
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

            numberLine[start]++;
            numberLine[end + 1]--;
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