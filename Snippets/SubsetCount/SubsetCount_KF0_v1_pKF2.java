package com.thealgorithms.dynamicprogramming;

/**
 * Counts the number of subsets in a given array whose elements sum to a target value.
 *
 * <p>Based on the solution discussed on:
 * <a href="https://stackoverflow.com/questions/22891076/count-number-of-subsets-with-sum-equal-to-k">
 * StackOverflow</a>
 *
 * @author <a href="https://github.com/samratpodder">Samrat Podder</a>
 */
public final class SubsetCount {

    private SubsetCount() {
        // Utility class; prevent instantiation
    }

    /**
     * Counts the number of subsets whose sum equals {@code target} using a 2D DP table.
     *
     * <p>Time Complexity: O(n * target)<br>
     * Space Complexity: O(n * target)
     *
     * @param arr the input array
     * @param target the desired subset sum
     * @return the number of subsets whose sum equals {@code target}
     */
    public static int getCount(int[] arr, int target) {
        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        // There is always exactly one subset (empty set) with sum 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Initialize first element: either we take it (if it fits) or we don't
        if (arr[0] <= target) {
            dp[0][arr[0]] = 1;
        }

        // Build the DP table:
        // dp[i][t] = number of subsets using elements up to index i that sum to t
        for (int i = 1; i < n; i++) {
            for (int t = 1; t <= target; t++) {
                int notTaken = dp[i - 1][t];
                int taken = 0;
                if (arr[i] <= t) {
                    taken = dp[i - 1][t - arr[i]];
                }
                dp[i][t] = notTaken + taken;
            }
        }

        return dp[n - 1][target];
    }

    /**
     * Space-optimized version of {@link #getCount(int[], int)} using a 1D DP array.
     *
     * <p>Time Complexity: O(n * target)<br>
     * Space Complexity: O(target)
     *
     * @param arr the input array
     * @param target the desired subset sum
     * @return the number of subsets whose sum equals {@code target}
     */
    public static int getCountSO(int[] arr, int target) {
        int n = arr.length;
        int[] prev = new int[target + 1];

        // Base case: one subset (empty set) with sum 0
        prev[0] = 1;

        // Initialize for the first element
        if (arr[0] <= target) {
            prev[arr[0]] = 1;
        }

        // Iteratively update the DP array for each element
        for (int i = 1; i < n; i++) {
            int[] cur = new int[target + 1];
            cur[0] = 1; // empty subset always contributes to sum 0

            for (int t = 1; t <= target; t++) {
                int notTaken = prev[t];
                int taken = 0;
                if (arr[i] <= t) {
                    taken = prev[t - arr[i]];
                }
                cur[t] = notTaken + taken;
            }

            prev = cur;
        }

        return prev[target];
    }
}