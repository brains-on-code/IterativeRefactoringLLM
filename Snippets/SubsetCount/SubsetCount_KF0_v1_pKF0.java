package com.thealgorithms.dynamicprogramming;

/**
 * Find the number of subsets present in the given array with a sum equal to target.
 * Based on solution discussed on
 * <a href="https://stackoverflow.com/questions/22891076/count-number-of-subsets-with-sum-equal-to-k">StackOverflow</a>
 * @author <a href="https://github.com/samratpodder">Samrat Podder</a>
 */
public final class SubsetCount {

    private SubsetCount() {
        // Utility class
    }

    /**
     * Dynamic Programming implementation.
     * Finds the number of subsets in the given array whose sum equals target.
     * Time Complexity: O(n * target)
     * Space Complexity: O(n * target)
     *
     * @param arr    the input array
     * @param target the desired subset sum
     * @return number of subsets with sum equal to target
     */
    public static int getCount(int[] arr, int target) {
        int n = arr.length;
        if (n == 0 || target < 0) {
            return 0;
        }

        int[][] dp = new int[n][target + 1];

        // There is always one subset (empty subset) with sum 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Initialize first element
        if (arr[0] >= 0 && arr[0] <= target) {
            dp[0][arr[0]] = 1;
        }

        for (int idx = 1; idx < n; idx++) {
            for (int sum = 1; sum <= target; sum++) {
                int notTaken = dp[idx - 1][sum];
                int taken = 0;
                if (arr[idx] <= sum) {
                    taken = dp[idx - 1][sum - arr[idx]];
                }
                dp[idx][sum] = notTaken + taken;
            }
        }

        return dp[n - 1][target];
    }

    /**
     * Space-optimized version of getCount(int[], int).
     * Time Complexity: O(n * target)
     * Space Complexity: O(target)
     *
     * @param arr    the input array
     * @param target the desired subset sum
     * @return number of subsets with sum equal to target
     */
    public static int getCountSO(int[] arr, int target) {
        int n = arr.length;
        if (n == 0 || target < 0) {
            return 0;
        }

        int[] prev = new int[target + 1];
        prev[0] = 1;

        if (arr[0] >= 0 && arr[0] <= target) {
            prev[arr[0]] = 1;
        }

        for (int idx = 1; idx < n; idx++) {
            int[] cur = new int[target + 1];
            cur[0] = 1;

            for (int sum = 1; sum <= target; sum++) {
                int notTaken = prev[sum];
                int taken = 0;
                if (arr[idx] <= sum) {
                    taken = prev[sum - arr[idx]];
                }
                cur[sum] = notTaken + taken;
            }

            prev = cur;
        }

        return prev[target];
    }
}