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
        int n = array.length;
        if (n == 0) {
            return 0;
        }

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
                tails[length++] = value;
            } else {
                // Replace the first element in tails >= value
                int pos = upperBound(tails, 0, length - 1, value);
                tails[pos] = value;
            }
        }

        return length;
    }

    /**
     * Alternative implementation of LIS length using a similar tails array approach.
     */
    public static int findLISLen(int[] array) {
        int n = array.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        tails[0] = array[0];
        int length = 1;

        for (int i = 1; i < n; i++) {
            int value = array[i];
            int pos = binarySearchBetween(tails, length - 1, value);
            tails[pos] = value;
            if (pos == length) {
                length++;
            }
        }

        return length;
    }

    /**
     * Finds the first index in [left, right] such that ar[index] >= key.
     * Assumes ar is sorted in non-decreasing order in that range.
     */
    private static int upperBound(int[] ar, int left, int right, int key) {
        // Invariant: answer is in (left, right]
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (ar[mid] >= key) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    /**
     * Binary search for the position to place key in t[0..end] to keep it sorted.
     * Returns the index of the first element >= key, or end + 1 if all elements < key.
     */
    private static int binarySearchBetween(int[] t, int end, int key) {
        if (key <= t[0]) {
            return 0;
        }
        if (key > t[end]) {
            return end + 1;
        }

        int left = 0;
        int right = end;

        while (left < right) {
            int middle = (left + right) >>> 1;
            if (t[middle] < key) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }

        return right;
    }
}