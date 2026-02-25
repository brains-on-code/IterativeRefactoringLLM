package com.thealgorithms.dynamicprogramming;

public final class SubsetCount {

    private SubsetCount() {
        // Utility class; prevent instantiation
    }

    public static int getCount(int[] arr, int target) {
        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        initializeBaseCases2D(arr, target, dp);

        for (int index = 1; index < n; index++) {
            for (int sum = 1; sum <= target; sum++) {
                int notTaken = dp[index - 1][sum];
                int taken = 0;

                if (arr[index] <= sum) {
                    taken = dp[index - 1][sum - arr[index]];
                }

                dp[index][sum] = notTaken + taken;
            }
        }

        return dp[n - 1][target];
    }

    public static int getCountSO(int[] arr, int target) {
        int n = arr.length;
        int[] previousRow = new int[target + 1];

        initializeBaseCases1D(arr, target, previousRow);

        for (int index = 1; index < n; index++) {
            int[] currentRow = new int[target + 1];
            currentRow[0] = 1; // sum 0 can always be formed

            for (int sum = 1; sum <= target; sum++) {
                int notTaken = previousRow[sum];
                int taken = 0;

                if (arr[index] <= sum) {
                    taken = previousRow[sum - arr[index]];
                }

                currentRow[sum] = notTaken + taken;
            }

            previousRow = currentRow;
        }

        return previousRow[target];
    }

    private static void initializeBaseCases2D(int[] arr, int target, int[][] dp) {
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            dp[i][0] = 1; // sum 0 can always be formed by choosing no elements
        }

        if (arr[0] <= target) {
            dp[0][arr[0]] = 1; // using only the first element
        }
    }

    private static void initializeBaseCases1D(int[] arr, int target, int[] dpRow) {
        dpRow[0] = 1; // sum 0 can always be formed by choosing no elements

        if (arr[0] <= target) {
            dpRow[arr[0]] = 1; // using only the first element
        }
    }
}