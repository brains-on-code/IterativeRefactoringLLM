package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the length of the Longest Increasing Subsequence (LIS).
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the length of the Longest Increasing Subsequence (LIS) in the array.
     * <p>
     * Time complexity: O(n log n).
     * Uses a custom lower-bound implementation.
     */
    public static int lengthOfLISWithCustomLowerBound(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        int length = 1;

        tails[0] = nums[0];
        for (int i = 1; i < n; i++) {
            int current = nums[i];

            if (current < tails[0]) {
                // Start a new subsequence with a smaller first element
                tails[0] = current;
            } else if (current > tails[length - 1]) {
                // Extend the current longest subsequence
                tails[length++] = current;
            } else {
                // Update the first element in tails that is >= current
                int pos = lowerBound(tails, -1, length - 1, current);
                tails[pos] = current;
            }
        }

        return length;
    }

    /**
     * Returns the length of the Longest Increasing Subsequence (LIS) in the array.
     * <p>
     * Time complexity: O(n log n).
     * Uses a binary search helper to find the insertion position.
     */
    public static int lengthOfLISWithBinarySearch(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

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
     * Finds the first index in {@code tails[left+1..right]} such that
     * {@code tails[index] >= target}.
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