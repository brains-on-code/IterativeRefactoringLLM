package com.thealgorithms.dynamicprogramming;

public final class WineProblem {

    private WineProblem() {}

    public static int maxProfitRecursive(int[] prices, int startIndex, int endIndex) {
        int totalYears = prices.length;
        int currentYear = totalYears - (endIndex - startIndex + 1) + 1;

        if (startIndex == endIndex) {
            return prices[startIndex] * currentYear;
        }

        int profitIfSellStart =
                maxProfitRecursive(prices, startIndex + 1, endIndex) + prices[startIndex] * currentYear;
        int profitIfSellEnd =
                maxProfitRecursive(prices, startIndex, endIndex - 1) + prices[endIndex] * currentYear;

        return Math.max(profitIfSellStart, profitIfSellEnd);
    }

    public static int maxProfitTopDown(int[] prices, int startIndex, int endIndex, int[][] memo) {
        int totalYears = prices.length;
        int currentYear = totalYears - (endIndex - startIndex + 1) + 1;

        if (startIndex == endIndex) {
            return prices[startIndex] * currentYear;
        }

        if (memo[startIndex][endIndex] != 0) {
            return memo[startIndex][endIndex];
        }

        int profitIfSellStart =
                maxProfitTopDown(prices, startIndex + 1, endIndex, memo) + prices[startIndex] * currentYear;
        int profitIfSellEnd =
                maxProfitTopDown(prices, startIndex, endIndex - 1, memo) + prices[endIndex] * currentYear;

        int bestProfit = Math.max(profitIfSellStart, profitIfSellEnd);
        memo[startIndex][endIndex] = bestProfit;

        return bestProfit;
    }

    public static int maxProfitBottomUp(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int n = prices.length;
        int[][] dp = new int[n][n];

        for (int windowSize = 0; windowSize <= n - 1; windowSize++) {
            for (int startIndex = 0; startIndex <= n - windowSize - 1; startIndex++) {
                int endIndex = startIndex + windowSize;
                int currentYear = n - (endIndex - startIndex + 1) + 1;

                if (startIndex == endIndex) {
                    dp[startIndex][endIndex] = prices[startIndex] * currentYear;
                } else {
                    int profitIfSellStart =
                            dp[startIndex + 1][endIndex] + prices[startIndex] * currentYear;
                    int profitIfSellEnd =
                            dp[startIndex][endIndex - 1] + prices[endIndex] * currentYear;

                    dp[startIndex][endIndex] = Math.max(profitIfSellStart, profitIfSellEnd);
                }
            }
        }

        return dp[0][n - 1];
    }
}