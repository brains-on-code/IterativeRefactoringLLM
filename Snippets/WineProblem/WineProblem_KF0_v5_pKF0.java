package com.thealgorithms.dynamicprogramming;

/**
 * The WineProblem class provides solutions to the wine selling problem.
 * Given N wines with different prices, the objective is to maximize profit by selling
 * one wine each year, where only the leftmost or rightmost wine can be sold at any time.
 *
 * The price of the ith wine is pi, and the selling price is multiplied by the year in which
 * it is sold. This class implements three approaches:
 *
 * 1. Recursion
 * 2. Top-Down Dynamic Programming (Memoization)
 * 3. Bottom-Up Dynamic Programming (Tabulation)
 */
public final class WineProblem {

    private WineProblem() {
        // Utility class; prevent instantiation
    }

    /**
     * Calculate maximum profit using recursion.
     *
     * @param prices Array of wine prices.
     * @param start  Start index of the wine to consider.
     * @param end    End index of the wine to consider.
     * @return Maximum profit obtainable by selling the wines.
     */
    public static int wpRecursion(int[] prices, int start, int end) {
        int year = calculateYear(prices.length, start, end);

        if (start == end) {
            return prices[start] * year;
        }

        int profitIfSellStart =
            prices[start] * year + wpRecursion(prices, start + 1, end);
        int profitIfSellEnd =
            prices[end] * year + wpRecursion(prices, start, end - 1);

        return Math.max(profitIfSellStart, profitIfSellEnd);
    }

    /**
     * Calculate maximum profit using top-down dynamic programming with memoization.
     *
     * @param prices Array of wine prices.
     * @param start  Start index of the wine to consider.
     * @param end    End index of the wine to consider.
     * @param memo   2D array to store results of subproblems.
     * @return Maximum profit obtainable by selling the wines.
     */
    public static int wptd(int[] prices, int start, int end, int[][] memo) {
        int year = calculateYear(prices.length, start, end);

        if (start == end) {
            return prices[start] * year;
        }

        if (memo[start][end] != 0) {
            return memo[start][end];
        }

        int profitIfSellStart =
            prices[start] * year + wptd(prices, start + 1, end, memo);
        int profitIfSellEnd =
            prices[end] * year + wptd(prices, start, end - 1, memo);

        memo[start][end] = Math.max(profitIfSellStart, profitIfSellEnd);
        return memo[start][end];
    }

    /**
     * Calculate maximum profit using bottom-up dynamic programming with tabulation.
     *
     * @param prices Array of wine prices.
     * @return Maximum profit obtainable by selling the wines.
     * @throws IllegalArgumentException if the input array is null or empty.
     */
    public static int wpbu(int[] prices) {
        validatePrices(prices);

        int n = prices.length;
        int[][] dp = new int[n][n];

        for (int length = 1; length <= n; length++) {
            for (int start = 0; start + length - 1 < n; start++) {
                int end = start + length - 1;
                int year = calculateYear(n, start, end);

                if (start == end) {
                    dp[start][end] = prices[start] * year;
                    continue;
                }

                int profitIfSellStart = prices[start] * year + dp[start + 1][end];
                int profitIfSellEnd = prices[end] * year + dp[start][end - 1];

                dp[start][end] = Math.max(profitIfSellStart, profitIfSellEnd);
            }
        }

        return dp[0][n - 1];
    }

    private static int calculateYear(int totalWines, int start, int end) {
        int remainingWines = end - start + 1;
        return totalWines - remainingWines + 1;
    }

    private static void validatePrices(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
    }
}