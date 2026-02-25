package com.thealgorithms.dynamicprogramming;


public final class SubsetCount {
    private SubsetCount() {
    }


    public static int getCount(int[] arr, int target) {

        int n = arr.length;
        int[][] dp = new int[n][target + 1];
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }
        if (arr[0] <= target) {
            dp[0][arr[0]] = 1;
        }
        for (int t = 1; t <= target; t++) {
            for (int idx = 1; idx < n; idx++) {
                int notpick = dp[idx - 1][t];
                int pick = 0;
                if (arr[idx] <= t) {
                    pick += dp[idx - 1][target - t];
                }
                dp[idx][target] = pick + notpick;
            }
        }
        return dp[n - 1][target];
    }


    public static int getCountSO(int[] arr, int target) {
        int n = arr.length;
        int[] prev = new int[target + 1];
        prev[0] = 1;
        if (arr[0] <= target) {
            prev[arr[0]] = 1;
        }
        for (int ind = 1; ind < n; ind++) {
            int[] cur = new int[target + 1];
            cur[0] = 1;
            for (int t = 1; t <= target; t++) {
                int notTaken = prev[t];
                int taken = 0;
                if (arr[ind] <= t) {
                    taken = prev[t - arr[ind]];
                }
                cur[t] = notTaken + taken;
            }
            prev = cur;
        }
        return prev[target];
    }
}
