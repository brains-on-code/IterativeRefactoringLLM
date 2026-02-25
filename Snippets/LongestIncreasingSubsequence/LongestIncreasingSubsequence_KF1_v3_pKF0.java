package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the length of the Longest Increasing Subsequence (LIS).
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
        // Prevent instantiation
    }

    /**
     * Binary search helper for {@link #lengthOfLISBinarySearch(int[])}.
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
    public static int lengthOfLISBinarySearch(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] tails = new int[nums.length];
        int length = 1;

        tails[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];

            if (current < tails[0]) {
                // New smallest value
                tails[0] = current;
            } else if (current > tails[length - 1]) {
                // Extend the largest subsequence
                tails[length++] = current;
            } else {
                // current will become the end candidate of an existing subsequence
                int pos = lowerBound(tails, -1, length - 1, current);
                tails[pos] = current;
            }
        }

        return length;
    }

    /**
     * Alternative implementation of LIS length using a slightly different binary search.
     */
    public static int lengthOfLISAlternative(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] tails = new int[nums.length];
        tails[0] = nums[0];
        int length = 1;

        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];
            int pos = upperBoundLike(tails, length - 1, current);
            tails[pos] = current;
            if (pos == length) {
                length++;
            }
        }
        return length;
    }

    /**
     * Binary search helper for {@link #lengthOfLISAlternative(int[])}.
     * Returns the index where target should be placed:
     * - 0 if target is smaller than all elements
     * - length if target is greater than all elements
     * - otherwise the first index with value >= target.
     */
    private static int upperBoundLike(int[] tails, int lastIndex, int target) {
        if (target < tails[0]) {
            return 0;
        }
        if (target > tails[lastIndex]) {
            return lastIndex + 1;
        }

        int left = 0;
        int right = lastIndex;

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