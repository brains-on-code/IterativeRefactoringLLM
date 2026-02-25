package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the length of the Longest Increasing Subsequence (LIS).
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
        // Prevent instantiation
    }

    /**
     * Finds the index of the smallest element in {@code tails} that is greater than
     * or equal to {@code target}, searching in the range (leftIndex, rightIndex].
     */
    private static int findCeilingIndex(int[] tails, int leftIndex, int rightIndex, int target) {
        while (leftIndex < rightIndex - 1) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            if (tails[middleIndex] >= target) {
                rightIndex = middleIndex;
            } else {
                leftIndex = middleIndex;
            }
        }
        return rightIndex;
    }

    /**
     * Computes the length of the Longest Increasing Subsequence using a binary search
     * based tail array approach (variant 1).
     */
    public static int lengthOfLISVariant1(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        int lisLength = 1;

        tails[0] = nums[0];
        for (int i = 1; i < n; i++) {
            int current = nums[i];

            if (current < tails[0]) {
                // New smallest value
                tails[0] = current;
            } else if (current > tails[lisLength - 1]) {
                // Extend the largest subsequence
                tails[lisLength++] = current;
            } else {
                // current will replace the ceiling value in tails
                int ceilingIndex = findCeilingIndex(tails, -1, lisLength - 1, current);
                tails[ceilingIndex] = current;
            }
        }

        return lisLength;
    }

    /**
     * Computes the length of the Longest Increasing Subsequence using a binary search
     * based tail array approach (variant 2).
     */
    public static int lengthOfLISVariant2(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        tails[0] = nums[0];
        int lisLength = 1;

        for (int i = 1; i < n; i++) {
            int current = nums[i];
            int insertIndex = findInsertPosition(tails, lisLength - 1, current);
            tails[insertIndex] = current;
            if (insertIndex == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    /**
     * Finds the index at which {@code target} should be inserted in {@code tails}
     * (in the range [0, rightIndex]) to maintain sorted order.
     */
    private static int findInsertPosition(int[] tails, int rightIndex, int target) {
        int leftIndex = 0;
        int highIndex = rightIndex;

        if (target < tails[0]) {
            return 0;
        }
        if (target > tails[rightIndex]) {
            return rightIndex + 1;
        }

        while (leftIndex < highIndex - 1) {
            int middleIndex = (leftIndex + highIndex) >>> 1;
            if (tails[middleIndex] < target) {
                leftIndex = middleIndex;
            } else {
                highIndex = middleIndex;
            }
        }
        return highIndex;
    }
}