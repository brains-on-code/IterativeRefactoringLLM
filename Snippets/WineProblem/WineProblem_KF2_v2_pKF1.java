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
                maxProfitRecursive(winePrices, leftIndex + 1, rightIndex) + winePrices[leftIndex] * currentYear;
        int profitIfSellRight =
                maxProfitRecursive(winePrices, leftIndex, rightIndex - 1) + winePrices[rightIndex] * currentYear;

        return Math.max(profitIfSellLeft, profitIfSellRight);
    }

    public static int maxProfitTopDown(int[] winePrices, int leftIndex, int rightIndex, int[][] memoizedProfits) {
        int totalYears = winePrices.length;
        int currentYear = totalYears - (rightIndex - leftIndex + 1) + 1;

        if (leftIndex == rightIndex) {
            return winePrices[leftIndex] * currentYear;
        }

        if (memoizedProfits[leftIndex][rightIndex] != 0) {
            return memoizedProfits[leftIndex][rightIndex];
        }

        int profitIfSellLeft =
                maxProfitTopDown(winePrices, leftIndex + 1, rightIndex, memoizedProfits)
                        + winePrices[leftIndex] * currentYear;
        int profitIfSellRight =
                maxProfitTopDown(winePrices, leftIndex, rightIndex - 1, memoizedProfits)
                        + winePrices[rightIndex] * currentYear;

        int bestProfit = Math.max(profitIfSellLeft, profitIfSellRight);
        memoizedProfits[leftIndex][rightIndex] = bestProfit;

        return bestProfit;
    }

    public static int maxProfitBottomUp(int[] winePrices) {
        if (winePrices == null || winePrices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int totalYears = winePrices.length;
        int[][] maxProfitForRange = new int[totalYears][totalYears];

        for (int windowSize = 0; windowSize <= totalYears - 1; windowSize++) {
            for (int leftIndex = 0; leftIndex <= totalYears - windowSize - 1; leftIndex++) {
                int rightIndex = leftIndex + windowSize;
                int currentYear = totalYears - (rightIndex - leftIndex + 1) + 1;

                if (leftIndex == rightIndex) {
                    maxProfitForRange[leftIndex][rightIndex] = winePrices[leftIndex] * currentYear;
                } else {
                    int profitIfSellLeft =
                            maxProfitForRange[leftIndex + 1][rightIndex] + winePrices[leftIndex] * currentYear;
                    int profitIfSellRight =
                            maxProfitForRange[leftIndex][rightIndex - 1] + winePrices[rightIndex] * currentYear;

                    maxProfitForRange[leftIndex][rightIndex] = Math.max(profitIfSellLeft, profitIfSellRight);
                }
            }
        }

        return maxProfitForRange[0][totalYears - 1];
    }
}