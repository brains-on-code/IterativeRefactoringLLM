package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the length of the Longest Increasing Subsequence (LIS).
 *
 * <p>Contains two O(n log n) implementations:
 * <ul>
 *     <li>{@link #lis(int[])} using an internal upperBound method</li>
 *     <li>{@link #findLISLen(int[])} using a clearer binary search helper</li>
 * </ul>
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the index of the first element in {@code array} within the range
     * (left, right] that is greater than or equal to {@code key}.
     *
     * <p>Precondition: {@code left < right} and the search is performed on a
     * non-decreasing segment.
     */
    private static int upperBound(int[] array, int left, int right, int key) {
        while (left < right - 1) {
            int mid = (left + right) >>> 1;
            if (array[mid] >= key) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    /**
     * Computes the length of the Longest Increasing Subsequence (LIS) in the given array
     * using an O(n log n) algorithm.
     *
     * <p>The method maintains an array {@code tails} where:
     * <ul>
     *     <li>{@code tails[i]} is the minimum possible tail value of an increasing subsequence
     *     of length {@code i + 1} found so far.</li>
     * </ul>
     *
     * @param array the input array
     * @return the length of the LIS
     */
    public static int lis(int[] array) {
        int size = array.length;
        if (size == 0) {
            return 0;
        }

        int[] tails = new int[size];
        int lisLength = 1;

        tails[0] = array[0];
        for (int i = 1; i < size; i++) {
            int value = array[i];

            if (value < tails[0]) {
                // New smallest value: start a new potential subsequence of length 1
                tails[0] = value;
            } else if (value > tails[lisLength - 1]) {
                // Value extends the longest subsequence found so far
                tails[lisLength++] = value;
            } else {
                // Value can improve the tail of an existing subsequence length
                int index = upperBound(tails, -1, lisLength - 1, value);
                tails[index] = value;
            }
        }

        return lisLength;
    }

    /**
     * Computes the length of the Longest Increasing Subsequence (LIS) in the given array
     * using an O(n log n) algorithm.
     *
     * @author Alon Firestein (https://github.com/alonfirestein)
     *
     * @param array the input array
     * @return the length of the LIS
     */
    public static int findLISLen(int[] array) {
        final int size = array.length;
        if (size == 0) {
            return 0;
        }

        int[] tails = new int[size];
        tails[0] = array[0];
        int lisLength = 1;

        for (int i = 1; i < size; i++) {
            int index = binarySearchBetween(tails, lisLength - 1, array[i]);
            tails[index] = array[i];
            if (index == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    /**
     * Binary search helper for {@link #findLISLen(int[])}.
     *
     * <p>Given a sorted array segment {@code tails[0..end]}, returns the index where {@code key}
     * should be placed to keep the segment sorted:
     * <ul>
     *     <li>If {@code key} is smaller than all elements, returns 0.</li>
     *     <li>If {@code key} is greater than all elements, returns {@code end + 1}.</li>
     *     <li>Otherwise, returns the first index {@code i} such that {@code tails[i] >= key}.</li>
     * </ul>
     *
     * @param tails the array containing the sorted segment
     * @param end   the last index of the sorted segment (inclusive)
     * @param key   the value to insert
     * @return the insertion index for {@code key}
     */
    private static int binarySearchBetween(int[] tails, int end, int key) {
        int left = 0;
        int right = end;

        if (key < tails[0]) {
            return 0;
        }
        if (key > tails[end]) {
            return end + 1;
        }

        while (left < right - 1) {
            final int middle = (left + right) >>> 1;
            if (tails[middle] < key) {
                left = middle;
            } else {
                right = middle;
            }
        }
        return right;
    }
}