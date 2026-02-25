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
        if (isInvalidInput(arr, target)) {
            return 0;
        }

        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        // There is always one subset (empty subset) with sum 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Initialize first element
        int firstElement = arr[0];
        if (isWithinTarget(firstElement, target)) {
            dp[0][firstElement] = 1;
        }

        for (int index = 1; index < n; index++) {
            int currentValue = arr[index];
            for (int sum = 1; sum <= target; sum++) {
                int notTaken = dp[index - 1][sum];
                int taken = 0;

                if (currentValue <= sum) {
                    taken = dp[index - 1][sum - currentValue];
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
        if (isInvalidInput(arr, target)) {
            return 0;
        }

        int n = arr.length;
        int[] previous = new int[target + 1];
        previous[0] = 1;

        int firstElement = arr[0];
        if (isWithinTarget(firstElement, target)) {
            previous[firstElement] = 1;
        }

        for (int index = 1; index < n; index++) {
            int currentValue = arr[index];
            int[] current = new int[target + 1];
            current[0] = 1;

            for (int sum = 1; sum <= target; sum++) {
                int notTaken = previous[sum];
                int taken = 0;

                if (currentValue <= sum) {
                    taken = previous[sum - currentValue];
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

    private static boolean isInvalidInput(int[] arr, int target) {
        return arr == null || arr.length == 0 || target < 0;
    }
}