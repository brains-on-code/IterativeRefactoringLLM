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
     * Finds the first index in tails[low..high] such that tails[index] >= target.
     */
    private static int lowerBound(int[] tails, int low, int high, int target) {
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (tails[mid] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
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
        int lisLength = 0;

        for (int num : nums) {
            if (lisLength == 0 || num > tails[lisLength - 1]) {
                tails[lisLength++] = num;
            } else {
                int position = lowerBound(tails, 0, lisLength, num);
                tails[position] = num;
            }
        }

        return lisLength;
    }

    /**
     * Alternative implementation of LIS length using a slightly different binary search.
     */
    public static int lengthOfLISAlternative(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] tails = new int[nums.length];
        int lisLength = 0;

        for (int num : nums) {
            int position = upperBoundLike(tails, lisLength, num);
            tails[position] = num;
            if (position == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    /**
     * Binary search helper for {@link #lengthOfLISAlternative(int[])}.
     * Returns the index where target should be placed:
     * - 0 if target is smaller than all elements
     * - length if target is greater than all elements
     * - otherwise the first index with value >= target.
     */
    private static int upperBoundLike(int[] tails, int length, int target) {
        if (length == 0) {
            return 0;
        }

        if (target < tails[0]) {
            return 0;
        }
        if (target > tails[length - 1]) {
            return length;
        }

        int low = 0;
        int high = length - 1;

        while (low < high) {
            int mid = (low + high) >>> 1;
            if (tails[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}