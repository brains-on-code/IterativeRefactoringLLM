package com.thealgorithms.dynamicprogramming;

public final class WineProfitCalculator {

    private WineProfitCalculator() {
    }

    public static int maxWineProfitRecursive(int[] winePrices, int leftIndex, int rightIndex) {
        int totalYears = winePrices.length;
        int currentYear = totalYears - (rightIndex - leftIndex + 1) + 1;

        if (leftIndex == rightIndex) {
            return winePrices[leftIndex] * currentYear;
        }

        int profitIfSellLeft =
            maxWineProfitRecursive(winePrices, leftIndex + 1, rightIndex) + winePrices[leftIndex] * currentYear;
        int profitIfSellRight =
            maxWineProfitRecursive(winePrices, leftIndex, rightIndex - 1) + winePrices[rightIndex] * currentYear;

        return Math.max(profitIfSellLeft, profitIfSellRight);
    }

    public static int maxWineProfitMemoized(int[] winePrices, int leftIndex, int rightIndex, int[][] memoTable) {
        int totalYears = winePrices.length;
        int currentYear = totalYears - (rightIndex - leftIndex + 1) + 1;

        if (leftIndex == rightIndex) {
            return winePrices[leftIndex] * currentYear;
        }

        if (memoTable[leftIndex][rightIndex] != 0) {
            return memoTable[leftIndex][rightIndex];
        }

        int profitIfSellLeft =
            maxWineProfitMemoized(winePrices, leftIndex + 1, rightIndex, memoTable)
                + winePrices[leftIndex] * currentYear;
        int profitIfSellRight =
            maxWineProfitMemoized(winePrices, leftIndex, rightIndex - 1, memoTable)
                + winePrices[rightIndex] * currentYear;

        int bestProfit = Math.max(profitIfSellLeft, profitIfSellRight);
        memoTable[leftIndex][rightIndex] = bestProfit;

        return bestProfit;
    }

    public static int maxWineProfitBottomUp(int[] winePrices) {
        if (winePrices == null || winePrices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int totalYears = winePrices.length;
        int[][] dpTable = new int[totalYears][totalYears];

        for (int length = 0; length <= totalYears - 1; length++) {
            for (int leftIndex = 0; leftIndex <= totalYears - length - 1; leftIndex++) {
                int rightIndex = leftIndex + length;
                int currentYear = totalYears - (rightIndex - leftIndex + 1) + 1;

                if (leftIndex == rightIndex) {
                    dpTable[leftIndex][rightIndex] = winePrices[leftIndex] * currentYear;
                } else {
                    int profitIfSellLeft =
                        dpTable[leftIndex + 1][rightIndex] + winePrices[leftIndex] * currentYear;
                    int profitIfSellRight =
                        dpTable[leftIndex][rightIndex - 1] + winePrices[rightIndex] * currentYear;
                    dpTable[leftIndex][rightIndex] = Math.max(profitIfSellLeft, profitIfSellRight);
                }
            }
        }

        return dpTable[0][totalYears - 1];
    }
}