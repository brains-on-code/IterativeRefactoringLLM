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
        if (values == null || values.length == 0) {
            return 0;
        }

        int length = values.length;
        int[] maxSumAtIndex = new int[length];

        maxSumAtIndex[0] = values[0];

        for (int index = 1; index < length; index++) {
            int maxSumExcludingCurrent = maxSumAtIndex[index - 1];

            int maxSumIncludingCurrent = values[index];
            if (index > 1) {
                maxSumIncludingCurrent += maxSumAtIndex[index - 2];
            }

            maxSumAtIndex[index] = Math.max(maxSumIncludingCurrent, maxSumExcludingCurrent);
        }

        return maxSumAtIndex[length - 1];
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
        if (values == null || values.length == 0) {
            return 0;
        }

        int maxSumIncludingPrevious = values[0];
        int maxSumExcludingPrevious = 0;

        for (int value : java.util.Arrays.copyOfRange(values, 1, values.length)) {
            int maxSumIfExcludingCurrent = maxSumIncludingPrevious;
            int maxSumIfIncludingCurrent = value + maxSumExcludingPrevious;

            int currentMaxSum = Math.max(maxSumIfIncludingCurrent, maxSumIfExcludingCurrent);

            maxSumExcludingPrevious = maxSumIncludingPrevious;
            maxSumIncludingPrevious = currentMaxSum;
        }

        return maxSumIncludingPrevious;
    }
}