package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for subset sum counting using dynamic programming.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Counts the number of subsets of the given array that sum to the target value
     * using a 2D dynamic programming table.
     *
     * @param nums   the input array of integers
     * @param target the target sum
     * @return the number of subsets whose elements sum to {@code target}
     */
    public static int method1(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n][target + 1];

        // There is always one subset (empty set) with sum 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Initialize first element
        if (nums[0] <= target) {
            dp[0][nums[0]] = 1;
        }

        // Fill DP table
        for (int sum = 1; sum <= target; sum++) {
            for (int i = 1; i < n; i++) {
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
     * Counts the number of subsets of the given array that sum to the target value
     * using a space-optimized 1D dynamic programming approach.
     *
     * @param nums   the input array of integers
     * @param target the target sum
     * @return the number of subsets whose elements sum to {@code target}
     */
    public static int method2(int[] nums, int target) {
        int n = nums.length;
        int[] prev = new int[target + 1];

        // There is always one subset (empty set) with sum 0
        prev[0] = 1;

        // Initialize first element
        if (nums[0] <= target) {
            prev[nums[0]] = 1;
        }

        // Build DP iteratively
        for (int i = 1; i < n; i++) {
            int[] curr = new int[target + 1];
            curr[0] = 1; // empty subset for sum 0

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