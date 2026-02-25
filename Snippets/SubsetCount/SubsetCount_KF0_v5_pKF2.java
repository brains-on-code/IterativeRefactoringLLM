package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for counting the number of subsets in an array that sum to a given target.
 *
 * <p>All methods are static; this class is not meant to be instantiated.
 */
public final class SubsetCount {

    private SubsetCount() {
        // Prevent instantiation
    }

    /**
     * Counts the number of subsets whose sum equals {@code target} using a 2D dynamic programming table.
     *
     * <p>Definition:
     * <ul>
     *   <li>{@code dp[i][t]} = number of subsets using elements from index {@code 0} to {@code i}
     *       (inclusive) that sum to {@code t}.</li>
     * </ul>
     *
     * <p>Transition:
     * <pre>
     * dp[i][t] = dp[i - 1][t]                                // exclude arr[i]
     *          + (t >= arr[i] ? dp[i - 1][t - arr[i]] : 0)   // include arr[i]
     * </pre>
     *
     * <p>Time Complexity: O(n * target)<br>
     * Space Complexity: O(n * target)</p>
     *
     * @param arr the input array
     * @param target the desired subset sum
     * @return the number of subsets whose sum equals {@code target}
     */
    public static int getCount(int[] arr, int target) {
        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        // Base case: there is exactly one subset (the empty subset) that sums to 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Handle the first element explicitly
        if (arr[0] <= target) {
            dp[0][arr[0]] = 1;
        }

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
     * Space-optimized version of {@link #getCount(int[], int)} using a 1D dynamic programming array.
     *
     * <p>Definition:
     * <ul>
     *   <li>{@code prev[t]} = number of subsets that sum to {@code t} using elements processed so far.</li>
     * </ul>
     *
     * <p>Time Complexity: O(n * target)<br>
     * Space Complexity: O(target)</p>
     *
     * @param arr the input array
     * @param target the desired subset sum
     * @return the number of subsets whose sum equals {@code target}
     */
    public static int getCountSO(int[] arr, int target) {
        int n = arr.length;
        int[] prev = new int[target + 1];

        // Base case: there is exactly one subset (the empty subset) that sums to 0
        prev[0] = 1;

        // Handle the first element explicitly
        if (arr[0] <= target) {
            prev[arr[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            int[] cur = new int[target + 1];

            // The empty subset always contributes to sum 0
            cur[0] = 1;

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