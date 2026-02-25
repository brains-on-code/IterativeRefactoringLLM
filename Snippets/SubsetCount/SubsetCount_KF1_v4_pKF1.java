package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for counting subsets with a given sum.
 */
public final class SubsetSumCounter {

    private SubsetSumCounter() {
        // Prevent instantiation
    }

    /**
     * Counts the number of subsets of the given array that sum to the target value,
     * using a 2D dynamic programming table.
     *
     * @param numbers   the input array of integers
     * @param targetSum the target sum
     * @return the number of subsets whose elements sum to targetSum
     */
    public static int countSubsetsWithSum2D(int[] numbers, int targetSum) {
        int n = numbers.length;
        int[][] dp = new int[n][targetSum + 1];

        // Base case: sum 0 can always be formed by choosing no elements
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Initialize first element
        if (numbers[0] <= targetSum) {
            dp[0][numbers[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int sum = 1; sum <= targetSum; sum++) {
                int countExcludingCurrent = dp[i - 1][sum];
                int countIncludingCurrent = 0;

                if (numbers[i] <= sum) {
                    countIncludingCurrent = dp[i - 1][sum - numbers[i]];
                }

                dp[i][sum] = countIncludingCurrent + countExcludingCurrent;
            }
        }

        return dp[n - 1][targetSum];
    }

    /**
     * Counts the number of subsets of the given array that sum to the target value,
     * using a space-optimized 1D dynamic programming approach.
     *
     * @param numbers   the input array of integers
     * @param targetSum the target sum
     * @return the number of subsets whose elements sum to targetSum
     */
    public static int countSubsetsWithSum1D(int[] numbers, int targetSum) {
        int n = numbers.length;
        int[] previous = new int[targetSum + 1];

        // Base case: sum 0 can always be formed by choosing no elements
        previous[0] = 1;

        // Initialize first element
        if (numbers[0] <= targetSum) {
            previous[numbers[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            int[] current = new int[targetSum + 1];
            current[0] = 1;

            for (int sum = 1; sum <= targetSum; sum++) {
                int countExcludingCurrent = previous[sum];
                int countIncludingCurrent = 0;

                if (numbers[i] <= sum) {
                    countIncludingCurrent = previous[sum - numbers[i]];
                }

                current[sum] = countExcludingCurrent + countIncludingCurrent;
            }

            previous = current;
        }

        return previous[targetSum];
    }
}