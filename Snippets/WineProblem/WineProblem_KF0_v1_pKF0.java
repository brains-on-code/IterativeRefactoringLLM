package com.thealgorithms.dynamicprogramming;

/**
 * The WineProblem class provides a solution to the wine selling problem.
 * Given a collection of N wines with different prices, the objective is to maximize profit by selling
 * one wine each year, considering the constraint that only the leftmost or rightmost wine can be sold
 * at any given time.
 *
 * The price of the ith wine is pi, and the selling price increases by a factor of the year in which
 * it is sold. This class implements three approaches to solve the problem:
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
        int year = getYear(prices.length, start, end);

        if (start == end) {
            return prices[start] * year;
        }

        int sellStart = wpRecursion(prices, start + 1, end) + prices[start] * year;
        int sellEnd = wpRecursion(prices, start, end - 1) + prices[end] * year;

        return Math.max(sellStart, sellEnd);
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
        int year = getYear(prices.length, start, end);

        if (start == end) {
            return prices[start] * year;
        }

        if (memo[start][end] != 0) {
            return memo[start][end];
        }

        int sellStart = wptd(prices, start + 1, end, memo) + prices[start] * year;
        int sellEnd = wptd(prices, start, end - 1, memo) + prices[end] * year;

        int result = Math.max(sellStart, sellEnd);
        memo[start][end] = result;

        return result;
    }

    /**
     * Calculate maximum profit using bottom-up dynamic programming with tabulation.
     *
     * @param prices Array of wine prices.
     * @throws IllegalArgumentException if the input array is null or empty.
     * @return Maximum profit obtainable by selling the wines.
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
                int year = getYear(n, start, end);

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

    private static int getYear(int totalWines, int start, int end) {
        int remainingWines = end - start + 1;
        return totalWines - remainingWines + 1;
    }
}