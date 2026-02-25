package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;

public final class LineSweep {

    private LineSweep() {
    }

    public static int findMaximumEndPoint(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[1]));
        return intervals[intervals.length - 1][1];
    }

    public static boolean hasOverlap(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return false;
        }

        int maxEndPoint = findMaximumEndPoint(intervals);
        int[] coverageDiff = new int[maxEndPoint + 2];

        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            coverageDiff[start] += 1;
            coverageDiff[end + 1] -= 1;
        }

        int currentCoverage = 0;
        int maxCoverage = 0;

        for (int delta : coverageDiff) {
            currentCoverage += delta;
            maxCoverage = Math.max(maxCoverage, currentCoverage);
        }

        return maxCoverage > 1;
    }
}