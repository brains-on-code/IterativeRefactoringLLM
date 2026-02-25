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
        int numberCount = numbers.length;
        int[][] subsetCountByIndexAndSum = new int[numberCount][targetSum + 1];

        // There is always exactly one subset (empty set) with sum 0
        for (int index = 0; index < numberCount; index++) {
            subsetCountByIndexAndSum[index][0] = 1;
        }

        // Initialize first row: using only numbers[0]
        if (numbers[0] <= targetSum) {
            subsetCountByIndexAndSum[0][numbers[0]] = 1;
        }

        for (int currentSum = 1; currentSum <= targetSum; currentSum++) {
            for (int index = 1; index < numberCount; index++) {
                int subsetsExcludingCurrent = subsetCountByIndexAndSum[index - 1][currentSum];
                int subsetsIncludingCurrent = 0;

                if (numbers[index] <= currentSum) {
                    subsetsIncludingCurrent =
                        subsetCountByIndexAndSum[index - 1][currentSum - numbers[index]];
                }

                subsetCountByIndexAndSum[index][currentSum] =
                    subsetsIncludingCurrent + subsetsExcludingCurrent;
            }
        }

        return subsetCountByIndexAndSum[numberCount - 1][targetSum];
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
        int numberCount = numbers.length;
        int[] previousRowSubsetCounts = new int[targetSum + 1];

        // There is always exactly one subset (empty set) with sum 0
        previousRowSubsetCounts[0] = 1;

        // Initialize using only numbers[0]
        if (numbers[0] <= targetSum) {
            previousRowSubsetCounts[numbers[0]] = 1;
        }

        for (int index = 1; index < numberCount; index++) {
            int[] currentRowSubsetCounts = new int[targetSum + 1];
            currentRowSubsetCounts[0] = 1;

            for (int currentSum = 1; currentSum <= targetSum; currentSum++) {
                int subsetsExcludingCurrent = previousRowSubsetCounts[currentSum];
                int subsetsIncludingCurrent = 0;

                if (numbers[index] <= currentSum) {
                    subsetsIncludingCurrent =
                        previousRowSubsetCounts[currentSum - numbers[index]];
                }

                currentRowSubsetCounts[currentSum] =
                    subsetsIncludingCurrent + subsetsExcludingCurrent;
            }

            previousRowSubsetCounts = currentRowSubsetCounts;
        }

        return previousRowSubsetCounts[targetSum];
    }
}