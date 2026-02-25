package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the length of the Longest Increasing Subsequence (LIS).
 *
 * <p>Contains two O(n log n) implementations:
 * <ul>
 *     <li>{@link #lis(int[])} using an internal upperBound method</li>
 *     <li>{@link #findLISLen(int[])} using an internal binarySearchBetween method</li>
 * </ul>
 *
 * @author Afrizal Fikri (https://github.com/icalF)
 * @author Alon Firestein (https://github.com/alonfirestein)
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the index of the first element in {@code array} within the range
     * {@code (left, right]} that is greater than or equal to {@code key}.
     *
     * <p>Assumes that {@code array} is sorted in non-decreasing order in the given range.
     *
     * @param array the array to search
     * @param left  the left boundary (exclusive)
     * @param right the right boundary (inclusive)
     * @param key   the value to search for
     * @return the index of the first element >= key within (left, right]
     */
    private static int upperBound(int[] array, int left, int right, int key) {
        while (left < right - 1) {
            int middle = (left + right) >>> 1;
            if (array[middle] >= key) {
                right = middle;
            } else {
                left = middle;
            }
        }
        return right;
    }

    /**
     * Computes the length of the Longest Increasing Subsequence (LIS) in the given array
     * using an O(n log n) algorithm.
     *
     * @param array the input array
     * @return the length of the LIS
     */
    public static int lis(int[] array) {
        int length = array.length;
        if (length == 0) {
            return 0;
        }

        int[] tails = new int[length];
        int lisLength = 1;

        tails[0] = array[0];

        for (int i = 1; i < length; i++) {
            int value = array[i];

            // New smallest value
            if (value < tails[0]) {
                tails[0] = value;
            }
            // Value extends the largest subsequence
            else if (value > tails[lisLength - 1]) {
                tails[lisLength] = value;
                lisLength++;
            }
            // Value will replace an existing tail to potentially start a better subsequence
            else {
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
            int value = array[i];
            int index = binarySearchBetween(tails, lisLength - 1, value);
            tails[index] = value;
            if (index == lisLength) {
                lisLength++;
            }
        }

        return lisLength;
    }

    /**
     * Binary search helper for {@link #findLISLen(int[])}.
     *
     * <p>Finds the index where {@code key} should be placed in the sorted prefix
     * {@code t[0..end]} to maintain sorted order.
     *
     * @param t    the array containing the sorted prefix
     * @param end  the last index of the sorted prefix
     * @param key  the value to insert
     * @return the index at which {@code key} should be placed
     */
    private static int binarySearchBetween(int[] t, int end, int key) {
        if (key < t[0]) {
            return 0;
        }
        if (key > t[end]) {
            return end + 1;
        }

        int left = 0;
        int right = end;

        while (left < right - 1) {
            int middle = (left + right) >>> 1;
            if (t[middle] < key) {
                left = middle;
            } else {
                right = middle;
            }
        }

        return right;
    }
}