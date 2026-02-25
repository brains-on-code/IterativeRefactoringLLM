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
        int elementCount = numbers.length;
        int[][] subsetCountByElementAndSum = new int[elementCount][targetSum + 1];

        // There is always exactly one subset (empty set) with sum 0
        for (int elementIndex = 0; elementIndex < elementCount; elementIndex++) {
            subsetCountByElementAndSum[elementIndex][0] = 1;
        }

        // Initialize first row: using only numbers[0]
        if (numbers[0] <= targetSum) {
            subsetCountByElementAndSum[0][numbers[0]] = 1;
        }

        for (int currentSum = 1; currentSum <= targetSum; currentSum++) {
            for (int elementIndex = 1; elementIndex < elementCount; elementIndex++) {
                int subsetsExcludingCurrentElement =
                    subsetCountByElementAndSum[elementIndex - 1][currentSum];
                int subsetsIncludingCurrentElement = 0;

                if (numbers[elementIndex] <= currentSum) {
                    subsetsIncludingCurrentElement =
                        subsetCountByElementAndSum[elementIndex - 1][currentSum - numbers[elementIndex]];
                }

                subsetCountByElementAndSum[elementIndex][currentSum] =
                    subsetsIncludingCurrentElement + subsetsExcludingCurrentElement;
            }
        }

        return subsetCountByElementAndSum[elementCount - 1][targetSum];
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
        int elementCount = numbers.length;
        int[] previousSubsetCounts = new int[targetSum + 1];

        // There is always exactly one subset (empty set) with sum 0
        previousSubsetCounts[0] = 1;

        // Initialize using only numbers[0]
        if (numbers[0] <= targetSum) {
            previousSubsetCounts[numbers[0]] = 1;
        }

        for (int elementIndex = 1; elementIndex < elementCount; elementIndex++) {
            int[] currentSubsetCounts = new int[targetSum + 1];
            currentSubsetCounts[0] = 1;

            for (int currentSum = 1; currentSum <= targetSum; currentSum++) {
                int subsetsExcludingCurrentElement = previousSubsetCounts[currentSum];
                int subsetsIncludingCurrentElement = 0;

                if (numbers[elementIndex] <= currentSum) {
                    subsetsIncludingCurrentElement =
                        previousSubsetCounts[currentSum - numbers[elementIndex]];
                }

                currentSubsetCounts[currentSum] =
                    subsetsIncludingCurrentElement + subsetsExcludingCurrentElement;
            }

            previousSubsetCounts = currentSubsetCounts;
        }

        return previousSubsetCounts[targetSum];
    }
}