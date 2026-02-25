package com.thealgorithms.dynamicprogramming;

/**
 * Find the number of subsets present in the given array with a sum equal to target.
 * Based on solution discussed on
 * <a href="https://stackoverflow.com/questions/22891076/count-number-of-subsets-with-sum-equal-to-k">StackOverflow</a>
 * @author <a href="https://github.com/samratpodder">Samrat Podder</a>
 */
public final class SubsetCount {

    private SubsetCount() {
    }

    /**
     * Dynamic Programming implementation.
     * Finds the number of subsets in the given array whose sum equals targetSum.
     * Time Complexity: O(n * targetSum)
     * Space Complexity: O(n * targetSum)
     *
     * @param numbers   the input array in which subsets are to be searched
     * @param targetSum the desired subset sum
     */
    public static int getCount(int[] numbers, int targetSum) {
        int n = numbers.length;
        int[][] dp = new int[n][targetSum + 1];

        // There is always exactly one subset (empty set) with sum 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // Initialize first row: using only numbers[0]
        if (numbers[0] <= targetSum) {
            dp[0][numbers[0]] = 1;
        }

        for (int sum = 1; sum <= targetSum; sum++) {
            for (int i = 1; i < n; i++) {
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
     * Space-optimized version of getCount(int[], int).
     * Time Complexity: O(n * targetSum)
     * Space Complexity: O(targetSum)
     *
     * @param numbers   the input array in which subsets are to be searched
     * @param targetSum the desired subset sum
     */
    public static int getCountSO(int[] numbers, int targetSum) {
        int n = numbers.length;
        int[] previous = new int[targetSum + 1];

        // There is always exactly one subset (empty set) with sum 0
        previous[0] = 1;

        // Initialize using only numbers[0]
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