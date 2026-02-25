package com.thealgorithms.dynamicprogramming;

public final class WineProfitCalculator {

    private WineProfitCalculator() {
    }

    public static int maxWineProfitRecursive(int[] prices, int left, int right) {
        int n = prices.length;
        int year = n - (right - left + 1) + 1;

        if (left == right) {
            return prices[left] * year;
        }

        int profitSellingLeft =
            maxWineProfitRecursive(prices, left + 1, right) + prices[left] * year;
        int profitSellingRight =
            maxWineProfitRecursive(prices, left, right - 1) + prices[right] * year;

        return Math.max(profitSellingLeft, profitSellingRight);
    }

    public static int maxWineProfitMemoized(int[] prices, int left, int right, int[][] memo) {
        int n = prices.length;
        int year = n - (right - left + 1) + 1;

        if (left == right) {
            return prices[left] * year;
        }

        if (memo[left][right] != 0) {
            return memo[left][right];
        }

        int profitSellingLeft =
            maxWineProfitMemoized(prices, left + 1, right, memo) + prices[left] * year;
        int profitSellingRight =
            maxWineProfitMemoized(prices, left, right - 1, memo) + prices[right] * year;

        int bestProfit = Math.max(profitSellingLeft, profitSellingRight);
        memo[left][right] = bestProfit;

        return bestProfit;
    }

    public static int maxWineProfitBottomUp(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int n = prices.length;
        int[][] dp = new int[n][n];

        for (int length = 0; length <= n - 1; length++) {
            for (int left = 0; left <= n - length - 1; left++) {
                int right = left + length;
                int year = n - (right - left + 1) + 1;

                if (left == right) {
                    dp[left][right] = prices[left] * year;
                } else {
                    int profitSellingLeft = dp[left + 1][right] + prices[left] * year;
                    int profitSellingRight = dp[left][right - 1] + prices[right] * year;
                    dp[left][right] = Math.max(profitSellingLeft, profitSellingRight);
                }
            }
        }

        return dp[0][n - 1];
    }
}