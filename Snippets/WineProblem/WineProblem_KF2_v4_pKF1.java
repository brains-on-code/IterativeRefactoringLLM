package com.thealgorithms.dynamicprogramming;

public final class WineProblem {

    private WineProblem() {}

    public static int maxProfitRecursive(int[] winePrices, int leftIndex, int rightIndex) {
        int totalYears = winePrices.length;
        int currentYear = totalYears - (rightIndex - leftIndex + 1) + 1;

        if (leftIndex == rightIndex) {
            return winePrices[leftIndex] * currentYear;
        }

        int profitIfSellLeft =
                maxProfitRecursive(winePrices, leftIndex + 1, rightIndex)
                        + winePrices[leftIndex] * currentYear;
        int profitIfSellRight =
                maxProfitRecursive(winePrices, leftIndex, rightIndex - 1)
                        + winePrices[rightIndex] * currentYear;

        return Math.max(profitIfSellLeft, profitIfSellRight);
    }

    public static int maxProfitTopDown(int[] winePrices, int leftIndex, int rightIndex, int[][] memo) {
        int totalYears = winePrices.length;
        int currentYear = totalYears - (rightIndex - leftIndex + 1) + 1;

        if (leftIndex == rightIndex) {
            return winePrices[leftIndex] * currentYear;
        }

        if (memo[leftIndex][rightIndex] != 0) {
            return memo[leftIndex][rightIndex];
        }

        int profitIfSellLeft =
                maxProfitTopDown(winePrices, leftIndex + 1, rightIndex, memo)
                        + winePrices[leftIndex] * currentYear;
        int profitIfSellRight =
                maxProfitTopDown(winePrices, leftIndex, rightIndex - 1, memo)
                        + winePrices[rightIndex] * currentYear;

        int bestProfit = Math.max(profitIfSellLeft, profitIfSellRight);
        memo[leftIndex][rightIndex] = bestProfit;

        return bestProfit;
    }

    public static int maxProfitBottomUp(int[] winePrices) {
        if (winePrices == null || winePrices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int totalYears = winePrices.length;
        int[][] maxProfit = new int[totalYears][totalYears];

        for (int windowSize = 0; windowSize <= totalYears - 1; windowSize++) {
            for (int leftIndex = 0; leftIndex <= totalYears - windowSize - 1; leftIndex++) {
                int rightIndex = leftIndex + windowSize;
                int currentYear = totalYears - (rightIndex - leftIndex + 1) + 1;

                if (leftIndex == rightIndex) {
                    maxProfit[leftIndex][rightIndex] = winePrices[leftIndex] * currentYear;
                } else {
                    int profitIfSellLeft =
                            maxProfit[leftIndex + 1][rightIndex] + winePrices[leftIndex] * currentYear;
                    int profitIfSellRight =
                            maxProfit[leftIndex][rightIndex - 1] + winePrices[rightIndex] * currentYear;
                    maxProfit[leftIndex][rightIndex] = Math.max(profitIfSellLeft, profitIfSellRight);
                }
            }
        }

        return maxProfit[0][totalYears - 1];
    }
}