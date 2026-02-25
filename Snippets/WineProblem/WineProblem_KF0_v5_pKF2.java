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
        int remainingWines = end - start + 1;
        int currentYear = n - remainingWines + 1;

        // Base case: only one wine left
        if (start == end) {
            return prices[start] * currentYear;
        }

        // Option 1: sell the leftmost wine
        int sellLeft = wpRecursion(prices, start + 1, end) + prices[start] * currentYear;
        // Option 2: sell the rightmost wine
        int sellRight = wpRecursion(prices, start, end - 1) + prices[end] * currentYear;

        return Math.max(sellLeft, sellRight);
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
        int remainingWines = end - start + 1;
        int currentYear = n - remainingWines + 1;

        // Base case: only one wine left
        if (start == end) {
            return prices[start] * currentYear;
        }

        // Return cached result if already computed
        if (dp[start][end] != 0) {
            return dp[start][end];
        }

        // Option 1: sell the leftmost wine
        int sellLeft = wptd(prices, start + 1, end, dp) + prices[start] * currentYear;
        // Option 2: sell the rightmost wine
        int sellRight = wptd(prices, start, end - 1, dp) + prices[end] * currentYear;

        dp[start][end] = Math.max(sellLeft, sellRight);
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

        // length is the size of the current subarray [start, end]
        for (int length = 1; length <= n; length++) {
            for (int start = 0; start + length - 1 < n; start++) {
                int end = start + length - 1;
                int remainingWines = end - start + 1;
                int currentYear = n - remainingWines + 1;

                // Base case: only one wine in this subarray
                if (start == end) {
                    dp[start][end] = prices[start] * currentYear;
                } else {
                    // Option 1: sell the leftmost wine
                    int sellLeft = dp[start + 1][end] + prices[start] * currentYear;
                    // Option 2: sell the rightmost wine
                    int sellRight = dp[start][end - 1] + prices[end] * currentYear;
                    dp[start][end] = Math.max(sellLeft, sellRight);
                }
            }
        }

        return dp[0][n - 1];
    }
}