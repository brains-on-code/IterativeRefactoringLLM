package com.thealgorithms.dynamicprogramming;

/**
 * Find the number of subsets present in the given array with a sum equal to target.
 * Based on Solution discussed on
 * <a href="https://stackoverflow.com/questions/22891076/count-number-of-subsets-with-sum-equal-to-k">StackOverflow</a>
 * @author <a href="https://github.com/samratpodder">Samrat Podder</a>
 */
public final class SubsetCount {

    private SubsetCount() {
    }

    /**
     * Dynamic Programming Implementation.
     * Method to find out the number of subsets present in the given array with a sum equal to
     * target. Time Complexity is O(n*target) and Space Complexity is O(n*target)
     *
     * @param numbers is the input array on which subsets are to be searched
     * @param targetSum is the sum of each element of the subset taken together
     */
    public static int getCount(int[] numbers, int targetSum) {
        int numberCount = numbers.length;
        int[][] subsetCountTable = new int[numberCount][targetSum + 1];

        for (int numberIndex = 0; numberIndex < numberCount; numberIndex++) {
            subsetCountTable[numberIndex][0] = 1;
        }

        if (numbers[0] <= targetSum) {
            subsetCountTable[0][numbers[0]] = 1;
        }

        for (int currentSum = 1; currentSum <= targetSum; currentSum++) {
            for (int numberIndex = 1; numberIndex < numberCount; numberIndex++) {
                int subsetsExcludingCurrent =
                        subsetCountTable[numberIndex - 1][currentSum];
                int subsetsIncludingCurrent = 0;

                if (numbers[numberIndex] <= currentSum) {
                    subsetsIncludingCurrent =
                            subsetCountTable[numberIndex - 1][currentSum - numbers[numberIndex]];
                }

                subsetCountTable[numberIndex][currentSum] =
                        subsetsIncludingCurrent + subsetsExcludingCurrent;
            }
        }

        return subsetCountTable[numberCount - 1][targetSum];
    }

    /**
     * This Method is a Space Optimized version of the getCount(int[], int) method and solves the
     * same problem. This approach is a bit better in terms of Space Used.
     * Time Complexity is O(n*target) and Space Complexity is O(target)
     *
     * @param numbers is the input array on which subsets are to be searched
     * @param targetSum is the sum of each element of the subset taken together
     */
    public static int getCountSO(int[] numbers, int targetSum) {
        int numberCount = numbers.length;
        int[] previousRow = new int[targetSum + 1];

        previousRow[0] = 1;
        if (numbers[0] <= targetSum) {
            previousRow[numbers[0]] = 1;
        }

        for (int numberIndex = 1; numberIndex < numberCount; numberIndex++) {
            int[] currentRow = new int[targetSum + 1];
            currentRow[0] = 1;

            for (int currentSum = 1; currentSum <= targetSum; currentSum++) {
                int subsetsExcludingCurrent = previousRow[currentSum];
                int subsetsIncludingCurrent = 0;

                if (numbers[numberIndex] <= currentSum) {
                    subsetsIncludingCurrent =
                            previousRow[currentSum - numbers[numberIndex]];
                }

                currentRow[currentSum] =
                        subsetsExcludingCurrent + subsetsIncludingCurrent;
            }

            previousRow = currentRow;
        }

        return previousRow[targetSum];
    }
}