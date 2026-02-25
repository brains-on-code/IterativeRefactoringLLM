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

        initializeBaseCase2D(dp, n);
        initializeFirstElement2D(dp, nums[0], target);

        for (int i = 1; i < n; i++) {
            for (int sum = 1; sum <= target; sum++) {
                int withoutCurrent = dp[i - 1][sum];
                int withCurrent = (nums[i] <= sum) ? dp[i - 1][sum - nums[i]] : 0;
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

        int n = nums.length;
        int[] previousRow = new int[target + 1];

        initializeBaseCase1D(previousRow);
        initializeFirstElement1D(previousRow, nums[0], target);

        for (int i = 1; i < n; i++) {
            int[] currentRow = new int[target + 1];
            currentRow[0] = 1; // empty subset for sum 0

            for (int sum = 1; sum <= target; sum++) {
                int withoutCurrent = previousRow[sum];
                int withCurrent = (nums[i] <= sum) ? previousRow[sum - nums[i]] : 0;
                currentRow[sum] = withoutCurrent + withCurrent;
            }

            previousRow = currentRow;
        }

        return previousRow[target];
    }

    private static boolean isInvalidInput(int[] nums, int target) {
        return nums == null || nums.length == 0 || target < 0;
    }

    private static void initializeBaseCase2D(int[][] dp, int n) {
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1; // one subset (empty set) with sum 0 for any prefix
        }
    }

    private static void initializeFirstElement2D(int[][] dp, int firstNum, int target) {
        if (firstNum >= 0 && firstNum <= target) {
            dp[0][firstNum] = 1;
        }
    }

    private static void initializeBaseCase1D(int[] dpRow) {
        dpRow[0] = 1; // one subset (empty set) with sum 0
    }

    private static void initializeFirstElement1D(int[] dpRow, int firstNum, int target) {
        if (firstNum >= 0 && firstNum <= target) {
            dpRow[firstNum] = 1;
        }
    }
}