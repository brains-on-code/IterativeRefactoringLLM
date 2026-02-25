package com.thealgorithms.dynamicprogramming;

public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the length of the Longest Increasing Subsequence (LIS) in the array.
     * O(n log n) time, O(n) space.
     */
    public static int lis(int[] array) {
        if (isNullOrEmpty(array)) {
            return 0;
        }

        int[] tails = new int[array.length];
        int length = 0;

        for (int value : array) {
            int position = lowerBound(tails, 0, length, value);
            tails[position] = value;
            if (position == length) {
                length++;
            }
        }

        return length;
    }

    /**
     * Alternative implementation of LIS length using the same tails array approach.
     */
    public static int findLISLen(int[] array) {
        if (isNullOrEmpty(array)) {
            return 0;
        }

        int[] tails = new int[array.length];
        int length = 0;

        for (int value : array) {
            int position = lowerBound(tails, 0, length, value);
            tails[position] = value;
            if (position == length) {
                length++;
            }
        }

        return length;
    }

    private static boolean isNullOrEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Returns the first index in [from, to) such that array[index] >= key.
     * Assumes array is sorted in non-decreasing order in that range.
     */
    private static int lowerBound(int[] array, int from, int to, int key) {
        int left = from;
        int right = to;

        while (left < right) {
            int middle = (left + right) >>> 1;
            if (array[middle] >= key) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }

        return left;
    }
}