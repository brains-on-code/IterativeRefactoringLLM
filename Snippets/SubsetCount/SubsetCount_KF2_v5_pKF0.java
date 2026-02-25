package com.thealgorithms.dynamicprogramming;

public final class SubsetCount {

    private SubsetCount() {
        // Utility class; prevent instantiation
    }

    public static int getCount(int[] numbers, int targetSum) {
        if (isInvalidInput(numbers, targetSum)) {
            return 0;
        }

        int n = numbers.length;
        int[][] dp = new int[n][targetSum + 1];

        initializeBaseCases2D(numbers, targetSum, dp);

        for (int i = 1; i < n; i++) {
            for (int sum = 1; sum <= targetSum; sum++) {
                int notTaken = dp[i - 1][sum];
                int taken = 0;

                if (numbers[i] <= sum) {
                    taken = dp[i - 1][sum - numbers[i]];
                }

                dp[i][sum] = notTaken + taken;
            }
        }

        return dp[n - 1][targetSum];
    }

    public static int getCountSO(int[] numbers, int targetSum) {
        if (isInvalidInput(numbers, targetSum)) {
            return 0;
        }

        int n = numbers.length;
        int[] previousRow = new int[targetSum + 1];

        initializeBaseCases1D(numbers, targetSum, previousRow);

        for (int i = 1; i < n; i++) {
            int[] currentRow = new int[targetSum + 1];
            currentRow[0] = 1; // sum 0 can always be formed

            for (int sum = 1; sum <= targetSum; sum++) {
                int notTaken = previousRow[sum];
                int taken = 0;

                if (numbers[i] <= sum) {
                    taken = previousRow[sum - numbers[i]];
                }

                currentRow[sum] = notTaken + taken;
            }

            previousRow = currentRow;
        }

        return previousRow[targetSum];
    }

    private static boolean isInvalidInput(int[] numbers, int targetSum) {
        return numbers == null || numbers.length == 0 || targetSum < 0;
    }

    private static void initializeBaseCases2D(int[] numbers, int targetSum, int[][] dp) {
        int n = numbers.length;

        for (int i = 0; i < n; i++) {
            dp[i][0] = 1; // sum 0 can always be formed by choosing no elements
        }

        if (numbers[0] <= targetSum) {
            dp[0][numbers[0]] = 1; // using only the first element
        }
    }

    private static void initializeBaseCases1D(int[] numbers, int targetSum, int[] dpRow) {
        dpRow[0] = 1; // sum 0 can always be formed by choosing no elements

        if (numbers[0] <= targetSum) {
            dpRow[numbers[0]] = 1; // using only the first element
        }
    }
}