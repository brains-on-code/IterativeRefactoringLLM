package com.thealgorithms.dynamicprogramming;

public final class SubsetCount {

    private SubsetCount() {}

    public static int getCount(int[] nums, int targetSum) {
        int length = nums.length;
        int[][] dp = new int[length][targetSum + 1];

        for (int i = 0; i < length; i++) {
            dp[i][0] = 1;
        }

        if (nums[0] <= targetSum) {
            dp[0][nums[0]] = 1;
        }

        for (int sum = 1; sum <= targetSum; sum++) {
            for (int i = 1; i < length; i++) {
                int excludeCurrent = dp[i - 1][sum];
                int includeCurrent = 0;

                if (nums[i] <= sum) {
                    includeCurrent = dp[i - 1][sum - nums[i]];
                }

                dp[i][sum] = includeCurrent + excludeCurrent;
            }
        }

        return dp[length - 1][targetSum];
    }

    public static int getCountSO(int[] nums, int targetSum) {
        int length = nums.length;
        int[] previous = new int[targetSum + 1];

        previous[0] = 1;
        if (nums[0] <= targetSum) {
            previous[nums[0]] = 1;
        }

        for (int i = 1; i < length; i++) {
            int[] current = new int[targetSum + 1];
            current[0] = 1;

            for (int sum = 1; sum <= targetSum; sum++) {
                int excludeCurrent = previous[sum];
                int includeCurrent = 0;

                if (nums[i] <= sum) {
                    includeCurrent = previous[sum - nums[i]];
                }

                current[sum] = excludeCurrent + includeCurrent;
            }

            previous = current;
        }

        return previous[targetSum];
    }
}