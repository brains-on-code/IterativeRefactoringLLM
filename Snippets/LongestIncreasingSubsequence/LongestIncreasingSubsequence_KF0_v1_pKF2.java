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
        // Prevent instantiation
    }

    /**
     * Returns the index of the first element in {@code ar} within (l, r] that is
     * greater than or equal to {@code key}.
     *
     * <p>Precondition: l < r and the search is performed on a non-decreasing segment.
     */
    private static int upperBound(int[] ar, int l, int r, int key) {
        while (l < r - 1) {
            int m = (l + r) >>> 1;
            if (ar[m] >= key) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }

    /**
     * Computes the length of the Longest Increasing Subsequence (LIS) in the given array
     * using an O(n log n) algorithm.
     *
     * <p>The method maintains an array {@code tail} where:
     * <ul>
     *     <li>{@code tail[i]} is the minimum possible tail value of an increasing subsequence
     *     of length {@code i + 1} found so far.</li>
     * </ul>
     *
     * @param array the input array
     * @return the length of the LIS
     */
    public static int lis(int[] array) {
        int len = array.length;
        if (len == 0) {
            return 0;
        }

        int[] tail = new int[len];
        int length = 1; // number of valid elements in tail

        tail[0] = array[0];
        for (int i = 1; i < len; i++) {
            int value = array[i];

            // Case 1: new smallest value, start a new potential subsequence of length 1
            if (value < tail[0]) {
                tail[0] = value;
            }
            // Case 2: value extends the largest subsequence found so far
            else if (value > tail[length - 1]) {
                tail[length++] = value;
            }
            // Case 3: value can be a better tail for some existing subsequence length
            // Find the first index where tail[index] >= value and replace it
            else {
                int idx = upperBound(tail, -1, length - 1, value);
                tail[idx] = value;
            }
        }

        return length;
    }

    /**
     * Computes the length of the Longest Increasing Subsequence (LIS) in the given array
     * using an O(n log n) algorithm.
     *
     * @author Alon Firestein (https://github.com/alonfirestein)
     *
     * @param a the input array
     * @return the length of the LIS
     */
    public static int findLISLen(int[] a) {
        final int size = a.length;
        if (size == 0) {
            return 0;
        }

        int[] tails = new int[size];
        tails[0] = a[0];
        int lis = 1; // current length of LIS

        for (int i = 1; i < size; i++) {
            int index = binarySearchBetween(tails, lis - 1, a[i]);
            tails[index] = a[i];
            if (index == lis) {
                lis++;
            }
        }
        return lis;
    }

    /**
     * Binary search helper for {@link #findLISLen(int[])}.
     *
     * <p>Given a sorted array segment {@code t[0..end]}, returns the index where {@code key}
     * should be placed to keep the segment sorted:
     * <ul>
     *     <li>If {@code key} is smaller than all elements, returns 0.</li>
     *     <li>If {@code key} is greater than all elements, returns {@code end + 1}.</li>
     *     <li>Otherwise, returns the first index {@code i} such that {@code t[i] >= key}.</li>
     * </ul>
     *
     * @param t    the array containing the sorted segment
     * @param end  the last index of the sorted segment (inclusive)
     * @param key  the value to insert
     * @return the insertion index for {@code key}
     */
    private static int binarySearchBetween(int[] t, int end, int key) {
        int left = 0;
        int right = end;

        if (key < t[0]) {
            return 0;
        }
        if (key > t[end]) {
            return end + 1;
        }

        while (left < right - 1) {
            final int middle = (left + right) >>> 1;
            if (t[middle] < key) {
                left = middle;
            } else {
                right = middle;
            }
        }
        return right;
    }
}