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
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }

        int[] dp = new int[length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < length; i++) {
            int includeCurrent = nums[i] + dp[i - 2];
            int excludeCurrent = dp[i - 1];
            dp[i] = Math.max(includeCurrent, excludeCurrent);
        }

        return dp[length - 1];
    }

    /**
     * Computes the maximum sum of non-adjacent elements using O(1) extra space.
     *
     * @param nums input array
     * @return maximum sum of non-adjacent elements
     */
    public static int maxNonAdjacentSumOptimized(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }

        int prevPrevMax = nums[0];
        int prevMax = Math.max(nums[0], nums[1]);

        for (int i = 2; i < length; i++) {
            int includeCurrent = nums[i] + prevPrevMax;
            int excludeCurrent = prevMax;
            int currentMax = Math.max(includeCurrent, excludeCurrent);

            prevPrevMax = prevMax;
            prevMax = currentMax;
        }

        return prevMax;
    }
}