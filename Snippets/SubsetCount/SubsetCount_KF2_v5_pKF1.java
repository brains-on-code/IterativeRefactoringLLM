package com.thealgorithms.dynamicprogramming;

public final class SubsetCount {

    private SubsetCount() {}

    public static int countSubsets(int[] nums, int targetSum) {
        int n = nums.length;
        int[][] dp = new int[n][targetSum + 1];

        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        if (nums[0] <= targetSum) {
            dp[0][nums[0]] = 1;
        }

        for (int sum = 1; sum <= targetSum; sum++) {
            for (int i = 1; i < n; i++) {
                int excludeCurrent = dp[i - 1][sum];
                int includeCurrent = 0;

                if (nums[i] <= sum) {
                    includeCurrent = dp[i - 1][sum - nums[i]];
                }

                dp[i][sum] = includeCurrent + excludeCurrent;
            }
        }

        return dp[n - 1][targetSum];
    }

    public static int countSubsetsSpaceOptimized(int[] nums, int targetSum) {
        int n = nums.length;
        int[] previousRow = new int[targetSum + 1];

        previousRow[0] = 1;
        if (nums[0] <= targetSum) {
            previousRow[nums[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            int[] currentRow = new int[targetSum + 1];
            currentRow[0] = 1;

            for (int sum = 1; sum <= targetSum; sum++) {
                int excludeCurrent = previousRow[sum];
                int includeCurrent = 0;

                if (nums[i] <= sum) {
                    includeCurrent = previousRow[sum - nums[i]];
                }

                currentRow[sum] = includeCurrent + excludeCurrent;
            }

            previousRow = currentRow;
        }

        return previousRow[targetSum];
    }
}