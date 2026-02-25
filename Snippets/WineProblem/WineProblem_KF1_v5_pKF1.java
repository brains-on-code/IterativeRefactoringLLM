package com.thealgorithms.dynamicprogramming;

public final class WineProfitCalculator {

    private WineProfitCalculator() {
    }

    public static int calculateMaxProfitRecursive(int[] prices, int leftIndex, int rightIndex) {
        int totalBottles = prices.length;
        int currentYear = totalBottles - (rightIndex - leftIndex + 1) + 1;

        if (leftIndex == rightIndex) {
            return prices[leftIndex] * currentYear;
        }

        int profitIfSellLeft =
            calculateMaxProfitRecursive(prices, leftIndex + 1, rightIndex) + prices[leftIndex] * currentYear;
        int profitIfSellRight =
            calculateMaxProfitRecursive(prices, leftIndex, rightIndex - 1) + prices[rightIndex] * currentYear;

        return Math.max(profitIfSellLeft, profitIfSellRight);
    }

    public static int calculateMaxProfitMemoized(
        int[] prices,
        int leftIndex,
        int rightIndex,
        int[][] memoizedProfits
    ) {
        int totalBottles = prices.length;
        int currentYear = totalBottles - (rightIndex - leftIndex + 1) + 1;

        if (leftIndex == rightIndex) {
            return prices[leftIndex] * currentYear;
        }

        if (memoizedProfits[leftIndex][rightIndex] != 0) {
            return memoizedProfits[leftIndex][rightIndex];
        }

        int profitIfSellLeft =
            calculateMaxProfitMemoized(prices, leftIndex + 1, rightIndex, memoizedProfits)
                + prices[leftIndex] * currentYear;
        int profitIfSellRight =
            calculateMaxProfitMemoized(prices, leftIndex, rightIndex - 1, memoizedProfits)
                + prices[rightIndex] * currentYear;

        int maxProfit = Math.max(profitIfSellLeft, profitIfSellRight);
        memoizedProfits[leftIndex][rightIndex] = maxProfit;

        return maxProfit;
    }

    public static int calculateMaxProfitBottomUp(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int totalBottles = prices.length;
        int[][] maxProfitForRange = new int[totalBottles][totalBottles];

        for (int rangeLength = 0; rangeLength <= totalBottles - 1; rangeLength++) {
            for (int leftIndex = 0; leftIndex <= totalBottles - rangeLength - 1; leftIndex++) {
                int rightIndex = leftIndex + rangeLength;
                int currentYear = totalBottles - (rightIndex - leftIndex + 1) + 1;

                if (leftIndex == rightIndex) {
                    maxProfitForRange[leftIndex][rightIndex] = prices[leftIndex] * currentYear;
                } else {
                    int profitIfSellLeft =
                        maxProfitForRange[leftIndex + 1][rightIndex] + prices[leftIndex] * currentYear;
                    int profitIfSellRight =
                        maxProfitForRange[leftIndex][rightIndex - 1] + prices[rightIndex] * currentYear;
                    maxProfitForRange[leftIndex][rightIndex] = Math.max(profitIfSellLeft, profitIfSellRight);
                }
            }
        }

        return maxProfitForRange[0][totalBottles - 1];
    }
}