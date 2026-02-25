package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class for working with time intervals.
 */
public final class IntervalUtils {

    private IntervalUtils() {
    }

    /**
     * Returns the maximum end time among the given intervals.
     *
     * @param intervals array of intervals, where each interval is [start, end]
     * @return the largest end time
     */
    public static int getMaxEndTime(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[1]));
        return intervals[intervals.length - 1][1];
    }

    /**
     * Determines whether any of the given intervals overlap.
     *
     * @param intervals array of intervals, where each interval is [start, end]
     * @return true if there is at least one overlap, false otherwise
     */
    public static boolean hasOverlappingIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return false;
        }

        int maxEndTime = getMaxEndTime(intervals);
        int[] intervalCountChanges = new int[maxEndTime + 2];

        for (int[] interval : intervals) {
            int startTime = interval[0];
            int endTime = interval[1];

            intervalCountChanges[startTime] += 1;
            intervalCountChanges[endTime + 1] -= 1;
        }

        int currentActiveIntervalCount = 0;
        int maxActiveIntervalCount = 0;
        for (int intervalCountChange : intervalCountChanges) {
            currentActiveIntervalCount += intervalCountChange;
            maxActiveIntervalCount = Math.max(maxActiveIntervalCount, currentActiveIntervalCount);
        }
        return maxActiveIntervalCount > 1;
    }
}