package com.thealgorithms.dynamicprogramming;

public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the index of the first element in {@code array[l+1..r]} that is
     * greater than or equal to {@code key}. Assumes {@code array} is sorted in
     * non-decreasing order in the searched range and that {@code l < r}.
     *
     * @param array the array to search
     * @param l     left boundary (exclusive)
     * @param r     right boundary (inclusive)
     * @param key   value to search for
     * @return index of the first element >= key within (l, r]
     */
    private static int upperBound(int[] array, int l, int r, int key) {
        while (l < r - 1) {
            int m = (l + r) >>> 1;
            if (array[m] >= key) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }

    /**
     * Computes the length of the Longest Increasing Subsequence (LIS) using a
     * patience sorting style algorithm with O(n log n) time complexity.
     *
     * @param array input array
     * @return length of the LIS
     */
    public static int lis(int[] array) {
        int len = array.length;
        if (len == 0) {
            return 0;
        }

        int[] tail = new int[len];
        int length = 1;

        tail[0] = array[0];

        for (int i = 1; i < len; i++) {
            int value = array[i];

            // New smallest value
            if (value < tail[0]) {
                tail[0] = value;
            }
            // Extend the largest subsequence
            else if (value > tail[length - 1]) {
                tail[length++] = value;
            }
            // Replace the first element >= value
            else {
                int index = upperBound(tail, -1, length - 1, value);
                tail[index] = value;
            }
        }

        return length;
    }

    /**
     * Alternative implementation of LIS length using a similar O(n log n)
     * approach with a slightly different binary search helper.
     *
     * @param array input array
     * @return length of the LIS
     */
    public static int findLISLen(int[] array) {
        final int size = array.length;
        if (size == 0) {
            return 0;
        }

        int[] tail = new int[size];
        tail[0] = array[0];
        int lisLength = 1;

        for (int i = 1; i < size; i++) {
            int value = array[i];
            int index = binarySearchBetween(tail, lisLength - 1, value);
            tail[index] = value;
            if (index == lisLength) {
                lisLength++;
            }
        }

        return lisLength;
    }

    /**
     * Binary search helper for {@link #findLISLen(int[])}.
     * Returns the index where {@code key} should be placed in {@code tail[0..end]}
     * to maintain sorted order.
     *
     * @param tail array containing current tails of increasing subsequences
     * @param end  last valid index in {@code tail} to consider
     * @param key  value to insert
     * @return index at which {@code key} should be placed
     */
    private static int binarySearchBetween(int[] tail, int end, int key) {
        if (key < tail[0]) {
            return 0;
        }
        if (key > tail[end]) {
            return end + 1;
        }

        int left = 0;
        int right = end;

        while (left < right - 1) {
            int middle = (left + right) >>> 1;
            if (tail[middle] < key) {
                left = middle;
            } else {
                right = middle;
            }
        }

        return right;
    }
}