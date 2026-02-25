package com.thealgorithms.dynamicprogramming;

public final class SubsetCount {

    private SubsetCount() {
        // Utility class; prevent instantiation
    }

    public static int getCount(int[] arr, int target) {
        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        // Base case: sum 0 can always be formed by choosing no elements
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Base case for the first element
        if (arr[0] <= target) {
            dp[0][arr[0]] = 1;
        }

        for (int idx = 1; idx < n; idx++) {
            for (int t = 1; t <= target; t++) {
                int notTaken = dp[idx - 1][t];
                int taken = 0;

                if (arr[idx] <= t) {
                    taken = dp[idx - 1][t - arr[idx]];
                }

                dp[idx][t] = notTaken + taken;
            }
        }

        return dp[n - 1][target];
    }

    public static int getCountSO(int[] arr, int target) {
        int n = arr.length;
        int[] prev = new int[target + 1];

        // Base case: sum 0 can always be formed by choosing no elements
        prev[0] = 1;

        // Base case for the first element
        if (arr[0] <= target) {
            prev[arr[0]] = 1;
        }

        for (int idx = 1; idx < n; idx++) {
            int[] cur = new int[target + 1];
            cur[0] = 1;

            for (int t = 1; t <= target; t++) {
                int notTaken = prev[t];
                int taken = 0;

                if (arr[idx] <= t) {
                    taken = prev[t - arr[idx]];
                }

                cur[t] = notTaken + taken;
            }

            prev = cur;
        }

        return prev[target];
    }
}