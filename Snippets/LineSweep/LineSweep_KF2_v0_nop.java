package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;


public final class LineSweep {
    private LineSweep() {
    }


    public static int findMaximumEndPoint(int[][] ranges) {
        Arrays.sort(ranges, Comparator.comparingInt(range -> range[1]));
        return ranges[ranges.length - 1][1];
    }


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
        for (int count : numberLine) {
            currentCount += count;
            maxOverlaps = Math.max(maxOverlaps, currentCount);
        }
        return maxOverlaps > 1;
    }
}
