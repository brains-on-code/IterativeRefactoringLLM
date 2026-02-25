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

        initializeBaseCase2D(dp);

        setFirstElement2D(nums[0], dp, target);

        for (int i = 1; i < n; i++) {
            int current = nums[i];
            for (int sum = 1; sum <= target; sum++) {
                dp[i][sum] = dp[i - 1][sum] + countWithCurrent2D(dp, i, sum, current);
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
        dp[0] = 1;

        setFirstElement1D(nums[0], dp, target);

        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];
            int[] next = new int[target + 1];
            next[0] = 1;

            for (int sum = 1; sum <= target; sum++) {
                next[sum] = dp[sum] + countWithCurrent1D(dp, sum, current);
            }

            dp = next;
        }

        return dp[target];
    }

    private static boolean isInvalidInput(int[] nums, int target) {
        return nums == null || nums.length == 0 || target < 0;
    }

    private static void initializeBaseCase2D(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
        }
    }

    private static void setFirstElement2D(int first, int[][] dp, int target) {
        if (first >= 0 && first <= target) {
            dp[0][first] = 1;
        }
    }

    private static int countWithCurrent2D(int[][] dp, int i, int sum, int current) {
        return current <= sum ? dp[i - 1][sum - current] : 0;
    }

    private static void setFirstElement1D(int first, int[] dp, int target) {
        if (first >= 0 && first <= target) {
            dp[first] = 1;
        }
    }

    private static int countWithCurrent1D(int[] dp, int sum, int current) {
        return current <= sum ? dp[sum - current] : 0;
    }
}