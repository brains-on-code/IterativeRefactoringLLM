package com.thealgorithms.dynamicprogramming;

public final class SubsetCount {

    private SubsetCount() {
        // Utility class; prevent instantiation
    }

    /**
     * Counts the number of subsets of {@code arr} whose elements sum to {@code target}
     * using a 2D dynamic programming table.
     *
     * @param arr    input array of non-negative integers
     * @param target target sum
     * @return number of subsets that sum to target
     */
    public static int getCount(int[] arr, int target) {
        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        // Base case: there is exactly one subset (empty set) that makes sum 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Base case for the first element
        if (arr[0] <= target) {
            dp[0][arr[0]] = 1;
        }

        // Fill the DP table
        for (int idx = 1; idx < n; idx++) {
            for (int t = 1; t <= target; t++) {
                int notTaken = dp[idx - 1][t]; // Exclude current element
                int taken = 0;

                // Include current element if it does not exceed current sum t
                if (arr[idx] <= t) {
                    taken = dp[idx - 1][t - arr[idx]];
                }

                dp[idx][t] = notTaken + taken;
            }
        }

        return dp[n - 1][target];
    }

    /**
     * Counts the number of subsets of {@code arr} whose elements sum to {@code target}
     * using a space-optimized 1D dynamic programming approach.
     *
     * @param arr    input array of non-negative integers
     * @param target target sum
     * @return number of subsets that sum to target
     */
    public static int getCountSO(int[] arr, int target) {
        int n = arr.length;
        int[] prev = new int[target + 1];

        // Base case: one subset (empty set) for sum 0
        prev[0] = 1;

        // Base case for the first element
        if (arr[0] <= target) {
            prev[arr[0]] = 1;
        }

        // Build up the DP array for each element
        for (int idx = 1; idx < n; idx++) {
            int[] cur = new int[target + 1];
            cur[0] = 1; // Empty subset always makes sum 0

            for (int t = 1; t <= target; t++) {
                int notTaken = prev[t]; // Exclude current element
                int taken = 0;

                // Include current element if it does not exceed current sum t
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