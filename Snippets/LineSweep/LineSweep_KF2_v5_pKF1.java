package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;

public final class LineSweep {

    private LineSweep() {
    }

    public static int findMaxEndPoint(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[1]));
        return intervals[intervals.length - 1][1];
    }

    public static boolean hasOverlap(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return false;
        }

        int maxEndPoint = findMaxEndPoint(intervals);
        int[] coverageDifferences = new int[maxEndPoint + 2];

        for (int[] interval : intervals) {
            int startIndex = interval[0];
            int endIndex = interval[1];

            coverageDifferences[startIndex] += 1;
            coverageDifferences[endIndex + 1] -= 1;
        }

        int currentCoverageCount = 0;
        int maxCoverageCount = 0;

        for (int coverageDelta : coverageDifferences) {
            currentCoverageCount += coverageDelta;
            maxCoverageCount = Math.max(maxCoverageCount, currentCoverageCount);
        }

        return maxCoverageCount > 1;
    }
}