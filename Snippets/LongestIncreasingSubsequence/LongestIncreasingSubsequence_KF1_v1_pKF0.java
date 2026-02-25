package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the length of the Longest Increasing Subsequence (LIS).
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Binary search helper for method2.
     * Finds the first index in tails[left+1..right] such that tails[index] >= target.
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
     * Returns the length of the Longest Increasing Subsequence (LIS) in the array.
     * O(n log n) implementation using a tails array and binary search.
     */
    public static int method2(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        int length = 1;

        tails[0] = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] < tails[0]) {
                // New smallest value
                tails[0] = nums[i];
            } else if (nums[i] > tails[length - 1]) {
                // Extend the largest subsequence
                tails[length++] = nums[i];
            } else {
                // nums[i] will become the end candidate of an existing subsequence
                int pos = lowerBound(tails, -1, length - 1, nums[i]);
                tails[pos] = nums[i];
            }
        }

        return length;
    }

    /**
     * Alternative implementation of LIS length using a slightly different binary search.
     */
    public static int method3(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        tails[0] = nums[0];
        int length = 1;

        for (int i = 1; i < n; i++) {
            int pos = upperBoundLike(tails, length - 1, nums[i]);
            tails[pos] = nums[i];
            if (pos == length) {
                length++;
            }
        }
        return length;
    }

    /**
     * Binary search helper for method3.
     * Returns the index where target should be placed:
     * - 0 if target is smaller than all elements
     * - length if target is greater than all elements
     * - otherwise the first index with value >= target.
     */
    private static int upperBoundLike(int[] tails, int lastIndex, int target) {
        int left = 0;
        int right = lastIndex;

        if (target < tails[0]) {
            return 0;
        }
        if (target > tails[lastIndex]) {
            return lastIndex + 1;
        }

        while (left < right - 1) {
            int mid = (left + right) >>> 1;
            if (tails[mid] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }
}