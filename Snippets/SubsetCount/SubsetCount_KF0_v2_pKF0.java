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
        if (arr == null || target < 0 || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        // There is always one subset (empty subset) with sum 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Initialize first element
        if (isWithinTarget(arr[0], target)) {
            dp[0][arr[0]] = 1;
        }

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
        if (arr == null || target < 0 || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int[] previous = new int[target + 1];
        previous[0] = 1;

        if (isWithinTarget(arr[0], target)) {
            previous[arr[0]] = 1;
        }

        for (int index = 1; index < n; index++) {
            int[] current = new int[target + 1];
            current[0] = 1;

            for (int sum = 1; sum <= target; sum++) {
                int notTaken = previous[sum];
                int taken = 0;

                if (arr[index] <= sum) {
                    taken = previous[sum - arr[index]];
                }

                current[sum] = notTaken + taken;
            }

            previous = current;
        }

        return previous[target];
    }

    private static boolean isWithinTarget(int value, int target) {
        return value >= 0 && value <= target;
    }
}