package com.thealgorithms.dynamicprogramming;

public final class SubsetCount {

    private SubsetCount() {}

    public static int countSubsets(int[] values, int targetSum) {
        int valueCount = values.length;
        int[][] subsetCounts = new int[valueCount][targetSum + 1];

        for (int valueIndex = 0; valueIndex < valueCount; valueIndex++) {
            subsetCounts[valueIndex][0] = 1;
        }

        if (values[0] <= targetSum) {
            subsetCounts[0][values[0]] = 1;
        }

        for (int currentSum = 1; currentSum <= targetSum; currentSum++) {
            for (int valueIndex = 1; valueIndex < valueCount; valueIndex++) {
                int subsetsExcludingCurrent = subsetCounts[valueIndex - 1][currentSum];
                int subsetsIncludingCurrent = 0;

                if (values[valueIndex] <= currentSum) {
                    subsetsIncludingCurrent =
                        subsetCounts[valueIndex - 1][currentSum - values[valueIndex]];
                }

                subsetCounts[valueIndex][currentSum] =
                    subsetsIncludingCurrent + subsetsExcludingCurrent;
            }
        }

        return subsetCounts[valueCount - 1][targetSum];
    }

    public static int countSubsetsSpaceOptimized(int[] values, int targetSum) {
        int valueCount = values.length;
        int[] previousSubsetCounts = new int[targetSum + 1];

        previousSubsetCounts[0] = 1;
        if (values[0] <= targetSum) {
            previousSubsetCounts[values[0]] = 1;
        }

        for (int valueIndex = 1; valueIndex < valueCount; valueIndex++) {
            int[] currentSubsetCounts = new int[targetSum + 1];
            currentSubsetCounts[0] = 1;

            for (int currentSum = 1; currentSum <= targetSum; currentSum++) {
                int subsetsExcludingCurrent = previousSubsetCounts[currentSum];
                int subsetsIncludingCurrent = 0;

                if (values[valueIndex] <= currentSum) {
                    subsetsIncludingCurrent =
                        previousSubsetCounts[currentSum - values[valueIndex]];
                }

                currentSubsetCounts[currentSum] =
                    subsetsIncludingCurrent + subsetsExcludingCurrent;
            }

            previousSubsetCounts = currentSubsetCounts;
        }

        return previousSubsetCounts[targetSum];
    }
}