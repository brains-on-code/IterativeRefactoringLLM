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
     * O(n log n) implementation using a custom lowerBound.
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
                // nums[i] will replace the first element in tails >= nums[i]
                int pos = lowerBound(tails, -1, length - 1, nums[i]);
                tails[pos] = nums[i];
            }
        }

        return length;
    }

    /**
     * Returns the length of the Longest Increasing Subsequence (LIS) in the array.
     * O(n log n) implementation using method4 as a binary search helper.
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
            int pos = method4(tails, length - 1, nums[i]);
            tails[pos] = nums[i];
            if (pos == length) {
                length++;
            }
        }
        return length;
    }

    /**
     * Binary search helper for method3.
     * Returns the index where target should be placed in tails[0..right]
     * to maintain a sorted (non-decreasing) order.
     */
    private static int method4(int[] tails, int right, int target) {
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