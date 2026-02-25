package com.thealgorithms.dynamicprogramming;

public final class WineProfitCalculator {

    private WineProfitCalculator() {
    }

    public static int maxWineProfitRecursive(int[] winePrices, int startIndex, int endIndex) {
        int totalYears = winePrices.length;
        int currentYear = totalYears - (endIndex - startIndex + 1) + 1;

        if (startIndex == endIndex) {
            return winePrices[startIndex] * currentYear;
        }

        int profitIfSellStart =
            maxWineProfitRecursive(winePrices, startIndex + 1, endIndex) + winePrices[startIndex] * currentYear;
        int profitIfSellEnd =
            maxWineProfitRecursive(winePrices, startIndex, endIndex - 1) + winePrices[endIndex] * currentYear;

        return Math.max(profitIfSellStart, profitIfSellEnd);
    }

    public static int maxWineProfitMemoized(int[] winePrices, int startIndex, int endIndex, int[][] memoizedProfits) {
        int totalYears = winePrices.length;
        int currentYear = totalYears - (endIndex - startIndex + 1) + 1;

        if (startIndex == endIndex) {
            return winePrices[startIndex] * currentYear;
        }

        if (memoizedProfits[startIndex][endIndex] != 0) {
            return memoizedProfits[startIndex][endIndex];
        }

        int profitIfSellStart =
            maxWineProfitMemoized(winePrices, startIndex + 1, endIndex, memoizedProfits)
                + winePrices[startIndex] * currentYear;
        int profitIfSellEnd =
            maxWineProfitMemoized(winePrices, startIndex, endIndex - 1, memoizedProfits)
                + winePrices[endIndex] * currentYear;

        int maxProfit = Math.max(profitIfSellStart, profitIfSellEnd);
        memoizedProfits[startIndex][endIndex] = maxProfit;

        return maxProfit;
    }

    public static int maxWineProfitBottomUp(int[] winePrices) {
        if (winePrices == null || winePrices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int totalYears = winePrices.length;
        int[][] maxProfitForRange = new int[totalYears][totalYears];

        for (int rangeLength = 0; rangeLength <= totalYears - 1; rangeLength++) {
            for (int startIndex = 0; startIndex <= totalYears - rangeLength - 1; startIndex++) {
                int endIndex = startIndex + rangeLength;
                int currentYear = totalYears - (endIndex - startIndex + 1) + 1;

                if (startIndex == endIndex) {
                    maxProfitForRange[startIndex][endIndex] = winePrices[startIndex] * currentYear;
                } else {
                    int profitIfSellStart =
                        maxProfitForRange[startIndex + 1][endIndex] + winePrices[startIndex] * currentYear;
                    int profitIfSellEnd =
                        maxProfitForRange[startIndex][endIndex - 1] + winePrices[endIndex] * currentYear;
                    maxProfitForRange[startIndex][endIndex] = Math.max(profitIfSellStart, profitIfSellEnd);
                }
            }
        }

        return maxProfitForRange[0][totalYears - 1];
    }
}