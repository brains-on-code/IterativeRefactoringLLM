package com.thealgorithms.dynamicprogramming;

/**
 * Utility class to compute the maximum sum of non-adjacent elements in an array.
 * Provides two approaches: one using O(n) extra space and another using O(1) space.
 */
final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
        // Prevent instantiation
    }

    /**
     * Approach 1: Dynamic programming with an auxiliary array.
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     *
     * @param values the input array of integers
     * @return the maximum sum of non-adjacent elements
     */
    public static int getMaxSumApproach1(int[] values) {
        if (values.length == 0) {
            return 0;
        }

        int n = values.length;
        int[] maxSumUpToIndex = new int[n];

        maxSumUpToIndex[0] = values[0];

        for (int i = 1; i < n; i++) {
            int maxIfExcluded = maxSumUpToIndex[i - 1];

            int maxIfIncluded = values[i];
            if (i > 1) {
                maxIfIncluded += maxSumUpToIndex[i - 2];
            }

            maxSumUpToIndex[i] = Math.max(maxIfIncluded, maxIfExcluded);
        }

        return maxSumUpToIndex[n - 1];
    }

    /**
     * Approach 2: Space-optimized dynamic programming using two variables.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param values the input array of integers
     * @return the maximum sum of non-adjacent elements
     */
    public static int getMaxSumApproach2(int[] values) {
        if (values.length == 0) {
            return 0;
        }

        int n = values.length;

        int maxSumIncludingCurrent = values[0];
        int maxSumExcludingCurrent = 0;

        for (int i = 1; i < n; i++) {
            int maxIfExcluded = maxSumIncludingCurrent;

            int maxIfIncluded = values[i];
            if (i > 1) {
                maxIfIncluded += maxSumExcludingCurrent;
            }

            int currentMaxSum = Math.max(maxIfIncluded, maxIfExcluded);

            maxSumExcludingCurrent = maxSumIncludingCurrent;
            maxSumIncludingCurrent = currentMaxSum;
        }

        return maxSumIncludingCurrent;
    }
}