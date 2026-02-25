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
        int length = numbers.length;
        int[][] subsetCount = new int[length][targetSum + 1];

        for (int i = 0; i < length; i++) {
            subsetCount[i][0] = 1;
        }

        if (numbers[0] <= targetSum) {
            subsetCount[0][numbers[0]] = 1;
        }

        for (int sum = 1; sum <= targetSum; sum++) {
            for (int index = 1; index < length; index++) {
                int excludeCurrent = subsetCount[index - 1][sum];
                int includeCurrent = 0;

                if (numbers[index] <= sum) {
                    includeCurrent = subsetCount[index - 1][sum - numbers[index]];
                }

                subsetCount[index][sum] = includeCurrent + excludeCurrent;
            }
        }

        return subsetCount[length - 1][targetSum];
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
        int length = numbers.length;
        int[] previousRow = new int[targetSum + 1];

        previousRow[0] = 1;
        if (numbers[0] <= targetSum) {
            previousRow[numbers[0]] = 1;
        }

        for (int index = 1; index < length; index++) {
            int[] currentRow = new int[targetSum + 1];
            currentRow[0] = 1;

            for (int sum = 1; sum <= targetSum; sum++) {
                int excludeCurrent = previousRow[sum];
                int includeCurrent = 0;

                if (numbers[index] <= sum) {
                    includeCurrent = previousRow[sum - numbers[index]];
                }

                currentRow[sum] = excludeCurrent + includeCurrent;
            }

            previousRow = currentRow;
        }

        return previousRow[targetSum];
    }
}