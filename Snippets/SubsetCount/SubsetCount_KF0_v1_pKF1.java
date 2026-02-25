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
        /*
         * Base Cases - If target becomes zero, we have reached the required sum for the subset
         * If we reach the end of the array then, either if target==arr[end], then we add one to
         * the final count Otherwise we add 0 to the final count
         */
        int length = numbers.length;
        int[][] subsetCount = new int[length][targetSum + 1];

        for (int index = 0; index < length; index++) {
            subsetCount[index][0] = 1;
        }

        if (numbers[0] <= targetSum) {
            subsetCount[0][numbers[0]] = 1;
        }

        for (int currentTarget = 1; currentTarget <= targetSum; currentTarget++) {
            for (int index = 1; index < length; index++) {
                int excludeCurrent = subsetCount[index - 1][currentTarget];
                int includeCurrent = 0;

                if (numbers[index] <= currentTarget) {
                    includeCurrent = subsetCount[index - 1][currentTarget - numbers[index]];
                }

                subsetCount[index][currentTarget] = includeCurrent + excludeCurrent;
            }
        }

        return subsetCount[length - 1][targetSum];
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
        int length = numbers.length;
        int[] previousRow = new int[targetSum + 1];

        previousRow[0] = 1;
        if (numbers[0] <= targetSum) {
            previousRow[numbers[0]] = 1;
        }

        for (int index = 1; index < length; index++) {
            int[] currentRow = new int[targetSum + 1];
            currentRow[0] = 1;

            for (int currentTarget = 1; currentTarget <= targetSum; currentTarget++) {
                int excludeCurrent = previousRow[currentTarget];
                int includeCurrent = 0;

                if (numbers[index] <= currentTarget) {
                    includeCurrent = previousRow[currentTarget - numbers[index]];
                }

                currentRow[currentTarget] = excludeCurrent + includeCurrent;
            }

            previousRow = currentRow;
        }

        return previousRow[targetSum];
    }
}