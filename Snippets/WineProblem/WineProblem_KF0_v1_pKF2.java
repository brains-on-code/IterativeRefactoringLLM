package com.thealgorithms.dynamicprogramming;

/**
 * Solves the classic "Wine Selling" problem.
 *
 * Given N wines in a row, each with a certain price, you can sell exactly one wine per year.
 * In any year, you may sell either the leftmost or the rightmost wine. The selling price of a wine
 * is its original price multiplied by the current year number (starting from 1).
 *
 * The goal is to maximize the total profit.
 *
 * This class provides three approaches:
 * 1. Pure recursion
 * 2. Top-down DP with memoization
 * 3. Bottom-up DP with tabulation
 */
public final class WineProblem {

    private WineProblem() {
        // Utility class; prevent instantiation
    }

    /**
     * Recursive solution (no memoization).
     *
     * @param prices array of wine prices
     * @param start  current left index
     * @param end    current right index
     * @return maximum profit obtainable from wines in range [start, end]
     */
    public static int wpRecursion(int[] prices, int start, int end) {
        int n = prices.length;
        int remaining = end - start + 1;
        int year = n - remaining + 1;

        if (start == end) {
            return prices[start] * year;
        }

        int sellStart = wpRecursion(prices, start + 1, end) + prices[start] * year;
        int sellEnd = wpRecursion(prices, start, end - 1) + prices[end] * year;

        return Math.max(sellStart, sellEnd);
    }

    /**
     * Top-down DP with memoization.
     *
     * @param prices price array
     * @param start  current left index
     * @param end    current right index
     * @param dp     memoization table; dp[i][j] stores max profit for range [i, j]
     * @return maximum profit obtainable from wines in range [start, end]
     */
    public static int wptd(int[] prices, int start, int end, int[][] dp) {
        int n = prices.length;
        int remaining = end - start + 1;
        int year = n - remaining + 1;

        if (start == end) {
            return prices[start] * year;
        }

        if (dp[start][end] != 0) {
            return dp[start][end];
        }

        int sellStart = wptd(prices, start + 1, end, dp) + prices[start] * year;
        int sellEnd = wptd(prices, start, end - 1, dp) + prices[end] * year;

        dp[start][end] = Math.max(sellStart, sellEnd);
        return dp[start][end];
    }

    /**
     * Bottom-up DP with tabulation.
     *
     * @param prices array of wine prices
     * @return maximum profit obtainable from selling all wines
     * @throws IllegalArgumentException if prices is null or empty
     */
    public static int wpbu(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int n = prices.length;
        int[][] dp = new int[n][n];

        for (int length = 1; length <= n; length++) {
            for (int start = 0; start + length - 1 < n; start++) {
                int end = start + length - 1;
                int remaining = end - start + 1;
                int year = n - remaining + 1;

                if (start == end) {
                    dp[start][end] = prices[start] * year;
                } else {
                    int sellStart = dp[start + 1][end] + prices[start] * year;
                    int sellEnd = dp[start][end - 1] + prices[end] * year;
                    dp[start][end] = Math.max(sellStart, sellEnd);
                }
            }
        }

        return dp[0][n - 1];
    }
}