package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The Line Sweep algorithm is used to solve range problems efficiently. It works by:
 * 1. Sorting a list of ranges by their start values in non-decreasing order.
 * 2. Sweeping through the number line (x-axis) while updating a count for each point based on the ranges.
 *
 * An overlapping range is defined as:
 * - (StartA <= EndB) AND (EndA >= StartB)
 *
 * References:
 * - https://en.wikipedia.org/wiki/Sweep_line_algorithm
 * - https://en.wikipedia.org/wiki/De_Morgan%27s_laws
 */
public final class LineSweep {

    private LineSweep() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the maximum endpoint from a list of ranges.
     *
     * @param ranges a 2D array where each element is a range represented by [start, end]
     * @return the maximum endpoint among all ranges
     * @throws IllegalArgumentException if ranges is null or empty
     */
    public static int findMaximumEndPoint(int[][] ranges) {
        validateRangesNotEmpty(ranges);

        Arrays.sort(ranges, Comparator.comparingInt(range -> range[1]));
        return ranges[ranges.length - 1][1];
    }

    /**
     * Determines if any of the given ranges overlap.
     *
     * @param ranges a 2D array where each element is a range represented by [start, end]
     * @return true if any ranges overlap, false otherwise
     */
    public static boolean isOverlap(int[][] ranges) {
        if (ranges == null || ranges.length == 0) {
            return false;
        }

        int maximumEndPoint = findMaximumEndPoint(ranges);
        int[] numberLine = new int[maximumEndPoint + 2];

        applyRangesToNumberLine(ranges, numberLine);

        return hasOverlap(numberLine);
    }

    private static void validateRangesNotEmpty(int[][] ranges) {
        if (ranges == null || ranges.length == 0) {
            throw new IllegalArgumentException("ranges must not be null or empty");
        }
    }

    private static void applyRangesToNumberLine(int[][] ranges, int[] numberLine) {
        for (int[] range : ranges) {
            if (range == null || range.length < 2) {
                throw new IllegalArgumentException("Each range must contain at least a start and end value");
            }

            int start = range[0];
            int end = range[1];

            if (start < 0 || end < start || end + 1 >= numberLine.length) {
                throw new IllegalArgumentException("Invalid range: [" + start + ", " + end + "]");
            }

            numberLine[start]++;
            numberLine[end + 1]--;
        }
    }

    private static boolean hasOverlap(int[] numberLine) {
        int activeRanges = 0;

        for (int delta : numberLine) {
            activeRanges += delta;
            if (activeRanges > 1) {
                return true;
            }
        }

        return false;
    }
}