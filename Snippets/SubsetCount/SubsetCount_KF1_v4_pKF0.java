package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for subset sum counting using dynamic programming.
 */
public final class SubsetSumCounter {

    private SubsetSumCounter() {
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
    public static int countSubsets2D(int[] nums, int target) {
        if (isInvalidInput(nums, target)) {
            return 0;
        }

        int n = nums.length;
        int[][] dp = new int[n][target + 1];

        // Base case: one subset (empty set) with sum 0 for any prefix
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Handle first element explicitly
        int first = nums[0];
        if (first >= 0 && first <= target) {
            dp[0][first] = 1;
        }

        for (int i = 1; i < n; i++) {
            int current = nums[i];
            for (int sum = 1; sum <= target; sum++) {
                int withoutCurrent = dp[i - 1][sum];
                int withCurrent = (current <= sum) ? dp[i - 1][sum - current] : 0;
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
    public static int countSubsets1D(int[] nums, int target) {
        if (isInvalidInput(nums, target)) {
            return 0;
        }

        int[] dp = new int[target + 1];

        // Base case: one subset (empty set) with sum 0
        dp[0] = 1;

        // Handle first element explicitly
        int first = nums[0];
        if (first >= 0 && first <= target) {
            dp[first] = 1;
        }

        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];
            int[] next = new int[target + 1];
            next[0] = 1; // empty subset for sum 0

            for (int sum = 1; sum <= target; sum++) {
                int withoutCurrent = dp[sum];
                int withCurrent = (current <= sum) ? dp[sum - current] : 0;
                next[sum] = withoutCurrent + withCurrent;
            }

            dp = next;
        }

        return dp[target];
    }

    private static boolean isInvalidInput(int[] nums, int target) {
        return nums == null || nums.length == 0 || target < 0;
    }
}