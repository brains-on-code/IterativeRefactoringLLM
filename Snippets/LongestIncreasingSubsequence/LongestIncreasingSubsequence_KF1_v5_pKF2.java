package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the length of the Longest Increasing Subsequence (LIS).
 *
 * <p>Provides two O(n log n) implementations that differ only in how they locate
 * the position at which to place each element in the internal {@code tails} array.</p>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the length of the Longest Increasing Subsequence (LIS) using a custom
     * lower-bound implementation.
     *
     * <p>Time complexity: O(n log n)</p>
     *
     * @param nums the input array
     * @return the length of the LIS
     */
    public static int lengthOfLISWithCustomLowerBound(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        /*
         * tails[i] holds the minimum possible tail value of an increasing subsequence
         * of length i + 1 found so far.
         */
        int[] tails = new int[n];
        int length = 1;

        tails[0] = nums[0];
        for (int i = 1; i < n; i++) {
            int current = nums[i];

            if (current < tails[0]) {
                // Current value becomes the new smallest tail.
                tails[0] = current;
            } else if (current > tails[length - 1]) {
                // Current value extends the longest subsequence found so far.
                tails[length++] = current;
            } else {
                // Current value updates the first tail that is >= current.
                int pos = lowerBound(tails, -1, length - 1, current);
                tails[pos] = current;
            }
        }

        return length;
    }

    /**
     * Computes the length of the Longest Increasing Subsequence (LIS) using a
     * binary search helper to find the insertion position.
     *
     * <p>Time complexity: O(n log n)</p>
     *
     * @param nums the input array
     * @return the length of the LIS
     */
    public static int lengthOfLISWithBinarySearch(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        /*
         * tails[i] holds the minimum possible tail value of an increasing subsequence
         * of length i + 1 found so far.
         */
        int[] tails = new int[n];
        tails[0] = nums[0];
        int length = 1;

        for (int i = 1; i < n; i++) {
            int pos = binarySearchPosition(tails, length - 1, nums[i]);
            tails[pos] = nums[i];
            if (pos == length) {
                length++;
            }
        }
        return length;
    }

    /**
     * Returns the first index in {@code tails[left + 1..right]} such that
     * {@code tails[index] >= target}.
     *
     * <p>This is a lower-bound search on the half-open interval
     * {@code (left, right]}.</p>
     *
     * @param tails  the array of current subsequence tails
     * @param left   the left boundary (exclusive)
     * @param right  the right boundary (inclusive)
     * @param target the value to search for
     * @return the index of the first element {@code >= target}
     */
    private static int lowerBound(int[] tails, int left, int right, int target) {
        while (left < right - 1) {
            int mid = (left + right) >>> 1;
            if (tails[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    /**
     * Returns the index where {@code target} should be placed in
     * {@code tails[0..right]} to maintain non-decreasing order.
     *
     * <p>The returned index is the first position {@code i} such that
     * {@code tails[i] >= target}.</p>
     *
     * @param tails  the array of current subsequence tails
     * @param right  the right boundary (inclusive)
     * @param target the value to insert
     * @return the insertion index for {@code target}
     */
    private static int binarySearchPosition(int[] tails, int right, int target) {
        int left = 0;
        int high = right;

        if (target < tails[0]) {
            return 0;
        }
        if (target > tails[right]) {
            return right + 1;
        }

        while (left < high - 1) {
            int mid = (left + high) >>> 1;
            if (tails[mid] < target) {
                left = mid;
            } else {
                high = mid;
            }
        }
        return high;
    }
}