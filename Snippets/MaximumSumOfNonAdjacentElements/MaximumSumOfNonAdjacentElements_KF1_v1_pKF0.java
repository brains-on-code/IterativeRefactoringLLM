package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for dynamic programming algorithms.
 */
final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the maximum sum of non-adjacent elements using a DP array.
     *
     * @param nums input array
     * @return maximum sum of non-adjacent elements
     */
    public static int method1(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int[] dp = new int[n];

        dp[0] = nums[0];

        for (int i = 1; i < n; i++) {
            int excludeCurrent = dp[i - 1];
            int includeCurrent = nums[i];
            if (i > 1) {
                includeCurrent += dp[i - 2];
            }
            dp[i] = Math.max(includeCurrent, excludeCurrent);
        }

        return dp[n - 1];
    }

    /**
     * Computes the maximum sum of non-adjacent elements using O(1) extra space.
     *
     * @param nums input array
     * @return maximum sum of non-adjacent elements
     */
    public static int method2(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int n = nums.length;

        int prevMax = nums[0];
        int prevPrevMax = 0;

        for (int i = 1; i < n; i++) {
            int excludeCurrent = prevMax;
            int includeCurrent = nums[i];
            if (i > 1) {
                includeCurrent += prevPrevMax;
            }

            int currentMax = Math.max(includeCurrent, excludeCurrent);

            prevPrevMax = prevMax;
            prevMax = currentMax;
        }

        return prevMax;
    }
}