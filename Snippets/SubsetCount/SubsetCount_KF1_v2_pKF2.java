package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for subset-sum style dynamic programming operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Counts the number of subsets of the given array whose elements sum to the given target.
     * Uses a 2D dynamic programming table.
     *
     * dp[i][s] = number of subsets using elements nums[0..i] that sum to s.
     *
     * @param nums   the input array of integers
     * @param target the target sum
     * @return the number of subsets that sum to {@code target}
     */
    public static int method1(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n][target + 1];

        // Base case: there is exactly one subset (empty subset) that makes sum 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Initialize first row: using only nums[0]
        if (nums[0] <= target) {
            dp[0][nums[0]] = 1;
        }

        // Fill the DP table
        for (int i = 1; i < n; i++) {
            for (int sum = 1; sum <= target; sum++) {
                int withoutCurrent = dp[i - 1][sum];
                int withCurrent = 0;

                if (nums[i] <= sum) {
                    withCurrent = dp[i - 1][sum - nums[i]];
                }

                dp[i][sum] = withoutCurrent + withCurrent;
            }
        }

        return dp[n - 1][target];
    }

    /**
     * Counts the number of subsets of the given array whose elements sum to the given target.
     * Uses a space-optimized 1D dynamic programming approach.
     *
     * prev[s] = number of subsets using processed elements that sum to s.
     *
     * @param nums   the input array of integers
     * @param target the target sum
     * @return the number of subsets that sum to {@code target}
     */
    public static int method2(int[] nums, int target) {
        int n = nums.length;
        int[] prev = new int[target + 1];

        // Base case: one way to make sum 0 (empty subset)
        prev[0] = 1;

        // Initialize for the first element
        if (nums[0] <= target) {
            prev[nums[0]] = 1;
        }

        // Process remaining elements
        for (int i = 1; i < n; i++) {
            int[] curr = new int[target + 1];
            curr[0] = 1; // one way to make sum 0: empty subset

            for (int sum = 1; sum <= target; sum++) {
                int withoutCurrent = prev[sum];
                int withCurrent = 0;

                if (nums[i] <= sum) {
                    withCurrent = prev[sum - nums[i]];
                }

                curr[sum] = withoutCurrent + withCurrent;
            }

            prev = curr;
        }

        return prev[target];
    }
}