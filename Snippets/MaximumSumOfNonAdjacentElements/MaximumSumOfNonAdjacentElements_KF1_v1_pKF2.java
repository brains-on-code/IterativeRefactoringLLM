package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for dynamic programming algorithms.
 *
 * <p>Contains methods to compute the maximum sum of non-adjacent elements in an array.
 */
final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the maximum sum of non-adjacent elements using O(n) extra space.
     *
     * <p>For each index i, this method decides whether to include or exclude the element
     * at i to maximize the sum, ensuring that no two chosen elements are adjacent.
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

        // Base case: only one element
        dp[0] = nums[0];

        for (int i = 1; i < n; i++) {
            // Option 1: exclude current element, take best up to previous index
            int exclude = dp[i - 1];

            // Option 2: include current element, add best up to i - 2 (if it exists)
            int include = nums[i];
            if (i > 1) {
                include += dp[i - 2];
            }

            // Best of including or excluding current element
            dp[i] = Math.max(include, exclude);
        }

        return dp[n - 1];
    }

    /**
     * Computes the maximum sum of non-adjacent elements using O(1) extra space.
     *
     * <p>Space-optimized version of {@link #method1(int[])} that keeps only the last
     * two DP states.
     *
     * @param nums the input array of integers
     * @return the maximum sum of non-adjacent elements
     */
    public static int method2(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int n = nums.length;

        // prevMax: dp[i - 1] – best sum up to previous index
        // prevPrevMax: dp[i - 2] – best sum up to index before previous
        int prevMax = nums[0];
        int prevPrevMax = 0;

        for (int i = 1; i < n; i++) {
            // Option 1: exclude current element
            int exclude = prevMax;

            // Option 2: include current element
            int include = nums[i];
            if (i > 1) {
                include += prevPrevMax;
            }

            int currentMax = Math.max(include, exclude);

            // Shift window for next iteration
            prevPrevMax = prevMax;
            prevMax = currentMax;
        }

        return prevMax;
    }
}