package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;

public final class LineSweep {

    private LineSweep() {
        // Utility class; prevent instantiation
    }

    public static int findMaximumEndPoint(int[][] ranges) {
        validateRangesNotEmpty(ranges);

        Arrays.sort(ranges, Comparator.comparingInt(range -> range[1]));
        int lastIndex = ranges.length - 1;
        return ranges[lastIndex][1];
    }

    public static boolean isOverlap(int[][] ranges) {
        if (ranges == null || ranges.length == 0) {
            return false;
        }

        int maximumEndPoint = findMaximumEndPoint(ranges);
        int[] numberLine = new int[maximumEndPoint + 2];

        for (int[] range : ranges) {
            validateRange(range);

            int start = range[0];
            int end = range[1];

            numberLine[start]++;
            numberLine[end + 1]--;
        }

        int currentOverlapCount = 0;
        int maxOverlapCount = 0;

        for (int delta : numberLine) {
            currentOverlapCount += delta;
            maxOverlapCount = Math.max(maxOverlapCount, currentOverlapCount);
        }

        return maxOverlapCount > 1;
    }

    private static void validateRangesNotEmpty(int[][] ranges) {
        if (ranges == null || ranges.length == 0) {
            throw new IllegalArgumentException("Ranges must not be null or empty");
        }
    }

    private static void validateRange(int[] range) {
        if (range == null || range.length < 2) {
            throw new IllegalArgumentException("Each range must have at least two elements");
        }
    }
}