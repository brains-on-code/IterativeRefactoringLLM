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

        int maximumEndPoint = findMaximumEndPoint(intervals);
        int[] coverageDifferences = new int[maximumEndPoint + 2];

        for (int[] interval : intervals) {
            int startIndex = interval[0];
            int endIndex = interval[1];

            coverageDifferences[startIndex] += 1;
            coverageDifferences[endIndex + 1] -= 1;
        }

        int currentCoverage = 0;
        int maximumCoverage = 0;

        for (int coverageDelta : coverageDifferences) {
            currentCoverage += coverageDelta;
            maximumCoverage = Math.max(maximumCoverage, currentCoverage);
        }

        return maximumCoverage > 1;
    }
}