package com.thealgorithms.dynamicprogramming;

public final class SubsetCount {

    private SubsetCount() {}

    public static int getCount(int[] numbers, int targetSum) {
        int n = numbers.length;
        int[][] subsetCount = new int[n][targetSum + 1];

        for (int i = 0; i < n; i++) {
            subsetCount[i][0] = 1;
        }

        if (numbers[0] <= targetSum) {
            subsetCount[0][numbers[0]] = 1;
        }

        for (int sum = 1; sum <= targetSum; sum++) {
            for (int index = 1; index < n; index++) {
                int countExcludingCurrent = subsetCount[index - 1][sum];
                int countIncludingCurrent = 0;

                if (numbers[index] <= sum) {
                    countIncludingCurrent = subsetCount[index - 1][sum - numbers[index]];
                }

                subsetCount[index][sum] = countIncludingCurrent + countExcludingCurrent;
            }
        }

        return subsetCount[n - 1][targetSum];
    }

    public static int getCountSO(int[] numbers, int targetSum) {
        int n = numbers.length;
        int[] previousRow = new int[targetSum + 1];

        previousRow[0] = 1;
        if (numbers[0] <= targetSum) {
            previousRow[numbers[0]] = 1;
        }

        for (int index = 1; index < n; index++) {
            int[] currentRow = new int[targetSum + 1];
            currentRow[0] = 1;

            for (int sum = 1; sum <= targetSum; sum++) {
                int countExcludingCurrent = previousRow[sum];
                int countIncludingCurrent = 0;

                if (numbers[index] <= sum) {
                    countIncludingCurrent = previousRow[sum - numbers[index]];
                }

                currentRow[sum] = countExcludingCurrent + countIncludingCurrent;
            }

            previousRow = currentRow;
        }

        return previousRow[targetSum];
    }
}