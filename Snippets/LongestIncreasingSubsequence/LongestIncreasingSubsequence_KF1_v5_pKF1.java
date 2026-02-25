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
     * or equal to {@code target}, searching in the range (low, high].
     */
    private static int findCeilingIndex(int[] tails, int low, int high, int target) {
        while (low < high - 1) {
            int mid = (low + high) >>> 1;
            if (tails[mid] >= target) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return high;
    }

    /**
     * Computes the length of the Longest Increasing Subsequence using a binary search
     * based tail array approach (variant 1).
     */
    public static int lengthOfLISVariant1(int[] sequence) {
        int length = sequence.length;
        if (length == 0) {
            return 0;
        }

        int[] tails = new int[length];
        int lisLength = 1;

        tails[0] = sequence[0];
        for (int i = 1; i < length; i++) {
            int currentValue = sequence[i];

            if (currentValue < tails[0]) {
                // New smallest value
                tails[0] = currentValue;
            } else if (currentValue > tails[lisLength - 1]) {
                // Extend the largest subsequence
                tails[lisLength++] = currentValue;
            } else {
                // currentValue will replace the ceiling value in tails
                int ceilingIndex = findCeilingIndex(tails, -1, lisLength - 1, currentValue);
                tails[ceilingIndex] = currentValue;
            }
        }

        return lisLength;
    }

    /**
     * Computes the length of the Longest Increasing Subsequence using a binary search
     * based tail array approach (variant 2).
     */
    public static int lengthOfLISVariant2(int[] sequence) {
        int length = sequence.length;
        if (length == 0) {
            return 0;
        }

        int[] tails = new int[length];
        tails[0] = sequence[0];
        int lisLength = 1;

        for (int i = 1; i < length; i++) {
            int currentValue = sequence[i];
            int insertIndex = findInsertPosition(tails, lisLength - 1, currentValue);
            tails[insertIndex] = currentValue;
            if (insertIndex == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    /**
     * Finds the index at which {@code target} should be inserted in {@code tails}
     * (in the range [0, right]) to maintain sorted order.
     */
    private static int findInsertPosition(int[] tails, int right, int target) {
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