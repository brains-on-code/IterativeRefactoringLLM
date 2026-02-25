package com.thealgorithms.dynamicprogramming;

public final class WineProblem {

    private WineProblem() {}

    public static int maxProfitRecursive(int[] prices, int left, int right) {
        int n = prices.length;
        int year = n - (right - left + 1) + 1;

        if (left == right) {
            return prices[left] * year;
        }

        int profitIfSellLeft =
                maxProfitRecursive(prices, left + 1, right) + prices[left] * year;
        int profitIfSellRight =
                maxProfitRecursive(prices, left, right - 1) + prices[right] * year;

        return Math.max(profitIfSellLeft, profitIfSellRight);
    }

    public static int maxProfitTopDown(int[] prices, int left, int right, int[][] memo) {
        int n = prices.length;
        int year = n - (right - left + 1) + 1;

        if (left == right) {
            return prices[left] * year;
        }

        if (memo[left][right] != 0) {
            return memo[left][right];
        }

        int profitIfSellLeft =
                maxProfitTopDown(prices, left + 1, right, memo) + prices[left] * year;
        int profitIfSellRight =
                maxProfitTopDown(prices, left, right - 1, memo) + prices[right] * year;

        int bestProfit = Math.max(profitIfSellLeft, profitIfSellRight);
        memo[left][right] = bestProfit;

        return bestProfit;
    }

    public static int maxProfitBottomUp(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int n = prices.length;
        int[][] dp = new int[n][n];

        for (int windowSize = 0; windowSize <= n - 1; windowSize++) {
            for (int left = 0; left <= n - windowSize - 1; left++) {
                int right = left + windowSize;
                int year = n - (right - left + 1) + 1;

                if (left == right) {
                    dp[left][right] = prices[left] * year;
                } else {
                    int profitIfSellLeft = dp[left + 1][right] + prices[left] * year;
                    int profitIfSellRight = dp[left][right - 1] + prices[right] * year;
                    dp[left][right] = Math.max(profitIfSellLeft, profitIfSellRight);
                }
            }
        }

        return dp[0][n - 1];
    }
}