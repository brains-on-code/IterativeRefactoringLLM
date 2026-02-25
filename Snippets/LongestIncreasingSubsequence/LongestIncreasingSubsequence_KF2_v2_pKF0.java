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
        if (array == null || array.length == 0) {
            return 0;
        }

        int n = array.length;
        int[] tails = new int[n];
        int length = 1;

        tails[0] = array[0];

        for (int i = 1; i < n; i++) {
            int value = array[i];

            if (value < tails[0]) {
                // New smallest value
                tails[0] = value;
            } else if (value > tails[length - 1]) {
                // Extend the largest subsequence
                tails[length] = value;
                length++;
            } else {
                // Replace the first element in tails >= value
                int position = firstGreaterOrEqual(tails, 0, length - 1, value);
                tails[position] = value;
            }
        }

        return length;
    }

    /**
     * Alternative implementation of LIS length using a similar tails array approach.
     */
    public static int findLISLen(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int n = array.length;
        int[] tails = new int[n];
        int length = 1;

        tails[0] = array[0];

        for (int i = 1; i < n; i++) {
            int value = array[i];
            int position = findInsertPosition(tails, length - 1, value);
            tails[position] = value;
            if (position == length) {
                length++;
            }
        }

        return length;
    }

    /**
     * Finds the first index in [left, right] such that array[index] >= key.
     * Assumes array is sorted in non-decreasing order in that range.
     */
    private static int firstGreaterOrEqual(int[] array, int left, int right, int key) {
        while (left < right) {
            int middle = (left + right) >>> 1;
            if (array[middle] >= key) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }
        return right;
    }

    /**
     * Binary search for the position to place key in tails[0..end] to keep it sorted.
     * Returns the index of the first element >= key, or end + 1 if all elements < key.
     */
    private static int findInsertPosition(int[] tails, int end, int key) {
        if (key <= tails[0]) {
            return 0;
        }
        if (key > tails[end]) {
            return end + 1;
        }

        int left = 0;
        int right = end;

        while (left < right) {
            int middle = (left + right) >>> 1;
            if (tails[middle] < key) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }

        return right;
    }
}