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
        int numberCount = numbers.length;
        int[][] subsetCountForSum = new int[numberCount][targetSum + 1];

        // Base case: sum 0 can always be formed by choosing no elements
        for (int index = 0; index < numberCount; index++) {
            subsetCountForSum[index][0] = 1;
        }

        // Initialize first element
        if (numbers[0] <= targetSum) {
            subsetCountForSum[0][numbers[0]] = 1;
        }

        for (int index = 1; index < numberCount; index++) {
            for (int currentSum = 1; currentSum <= targetSum; currentSum++) {
                int countExcludingCurrent = subsetCountForSum[index - 1][currentSum];
                int countIncludingCurrent = 0;

                if (numbers[index] <= currentSum) {
                    countIncludingCurrent =
                        subsetCountForSum[index - 1][currentSum - numbers[index]];
                }

                subsetCountForSum[index][currentSum] =
                    countIncludingCurrent + countExcludingCurrent;
            }
        }

        return subsetCountForSum[numberCount - 1][targetSum];
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
        int numberCount = numbers.length;
        int[] previousRowSubsetCounts = new int[targetSum + 1];

        // Base case: sum 0 can always be formed by choosing no elements
        previousRowSubsetCounts[0] = 1;

        // Initialize first element
        if (numbers[0] <= targetSum) {
            previousRowSubsetCounts[numbers[0]] = 1;
        }

        for (int index = 1; index < numberCount; index++) {
            int[] currentRowSubsetCounts = new int[targetSum + 1];
            currentRowSubsetCounts[0] = 1;

            for (int currentSum = 1; currentSum <= targetSum; currentSum++) {
                int countExcludingCurrent = previousRowSubsetCounts[currentSum];
                int countIncludingCurrent = 0;

                if (numbers[index] <= currentSum) {
                    countIncludingCurrent =
                        previousRowSubsetCounts[currentSum - numbers[index]];
                }

                currentRowSubsetCounts[currentSum] =
                    countIncludingCurrent + countExcludingCurrent;
            }

            previousRowSubsetCounts = currentRowSubsetCounts;
        }

        return previousRowSubsetCounts[targetSum];
    }
}