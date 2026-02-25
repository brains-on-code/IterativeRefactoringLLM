package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for dynamic programming algorithms.
 *
 * <p>Provides methods to compute the maximum sum of non-adjacent elements in an array.
 */
final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the maximum sum of non-adjacent elements using O(n) extra space.
     *
     * <p>For each index {@code i}, this method decides whether to include or exclude
     * {@code nums[i]} to maximize the sum, ensuring that no two chosen elements are adjacent.
     *
     * @param nums the input array of integers
     * @return the maximum sum of non-adjacent elements
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
     * <p>This is a space-optimized version of {@link #method1(int[])} that keeps only
     * the last two dynamic programming states.
     *
     * @param nums the input array of integers
     * @return the maximum sum of non-adjacent elements
     */
    public static int method2(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int prevMax = nums[0];    // Maximum sum up to the previous index
        int prevPrevMax = 0;      // Maximum sum up to the index before the previous one

        for (int i = 1; i < nums.length; i++) {
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