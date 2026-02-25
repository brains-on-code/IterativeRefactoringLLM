package com.thealgorithms.dynamicprogramming;

public final class SubsetCount {

    private SubsetCount() {
        // Prevent instantiation of utility class
    }

    /**
     * Returns the number of subsets of {@code arr} whose elements sum to {@code target},
     * using a 2D dynamic programming table.
     *
     * @param arr    input array of non-negative integers
     * @param target target sum
     * @return number of subsets that sum to {@code target}
     */
    public static int getCount(int[] arr, int target) {
        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        // There is exactly one subset (the empty set) that makes sum 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Initialize using the first element
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

    /**
     * Returns the number of subsets of {@code arr} whose elements sum to {@code target},
     * using a space-optimized 1D dynamic programming approach.
     *
     * @param arr    input array of non-negative integers
     * @param target target sum
     * @return number of subsets that sum to {@code target}
     */
    public static int getCountSO(int[] arr, int target) {
        int n = arr.length;
        int[] prev = new int[target + 1];

        // One subset (the empty set) for sum 0
        prev[0] = 1;

        // Initialize using the first element
        if (arr[0] <= target) {
            prev[arr[0]] = 1;
        }

        for (int idx = 1; idx < n; idx++) {
            int[] cur = new int[target + 1];
            // Empty subset always makes sum 0
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