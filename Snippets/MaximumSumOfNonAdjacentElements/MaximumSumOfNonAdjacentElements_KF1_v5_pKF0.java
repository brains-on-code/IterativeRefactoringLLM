package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for dynamic programming algorithms.
 */
final class MaxNonAdjacentSum {

    private MaxNonAdjacentSum() {
        // Prevent instantiation
    }

    /**
     * Computes the maximum sum of non-adjacent elements using a DP array.
     *
     * @param nums input array
     * @return maximum sum of non-adjacent elements
     */
    public static int maxNonAdjacentSumDP(int[] nums) {
        if (isNullOrEmpty(nums)) {
            return 0;
        }

        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }

        int[] maxSums = new int[length];
        maxSums[0] = nums[0];
        maxSums[1] = Math.max(nums[0], nums[1]);

        for (int index = 2; index < length; index++) {
            int includeCurrent = nums[index] + maxSums[index - 2];
            int excludeCurrent = maxSums[index - 1];
            maxSums[index] = Math.max(includeCurrent, excludeCurrent);
        }

        return maxSums[length - 1];
    }

    /**
     * Computes the maximum sum of non-adjacent elements using O(1) extra space.
     *
     * @param nums input array
     * @return maximum sum of non-adjacent elements
     */
    public static int maxNonAdjacentSumOptimized(int[] nums) {
        if (isNullOrEmpty(nums)) {
            return 0;
        }

        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }

        int maxExcludingPrevious = nums[0];
        int maxIncludingPrevious = Math.max(nums[0], nums[1]);

        for (int index = 2; index < length; index++) {
            int includeCurrent = nums[index] + maxExcludingPrevious;
            int excludeCurrent = maxIncludingPrevious;
            int currentMax = Math.max(includeCurrent, excludeCurrent);

            maxExcludingPrevious = maxIncludingPrevious;
            maxIncludingPrevious = currentMax;
        }

        return maxIncludingPrevious;
    }

    private static boolean isNullOrEmpty(int[] nums) {
        return nums == null || nums.length == 0;
    }
}