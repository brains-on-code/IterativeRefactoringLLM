package com.thealgorithms.dynamicprogramming;

/**
 * Wine Selling Problem (Dynamic Programming).
 *
 * Given an array of wine prices, each year you can sell either the leftmost or
 * rightmost bottle. The price of a bottle in year y is: price * y.
 * The goal is to maximize total profit.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Pure recursive solution (no memoization).
     *
     * @param prices array of wine prices
     * @param left   current left index
     * @param right  current right index
     * @return maximum profit obtainable from prices[left..right]
     */
    public static int method1(int[] prices, int left, int right) {
        int n = prices.length;
        int year = computeYear(left, right, n);

        if (left == right) {
            return prices[left] * year;
        }

        int sellLeft = method1(prices, left + 1, right) + prices[left] * year;
        int sellRight = method1(prices, left, right - 1) + prices[right] * year;

        return Math.max(sellLeft, sellRight);
    }

    /**
     * Top-down dynamic programming with memoization.
     *
     * @param prices array of wine prices
     * @param left   current left index
     * @param right  current right index
     * @param dp     memoization table; dp[i][j] stores max profit for prices[i..j]
     * @return maximum profit obtainable from prices[left..right]
     */
    public static int method2(int[] prices, int left, int right, int[][] dp) {
        int n = prices.length;
        int year = computeYear(left, right, n);

        if (left == right) {
            return prices[left] * year;
        }

        if (dp[left][right] != 0) {
            return dp[left][right];
        }

        int sellLeft = method2(prices, left + 1, right, dp) + prices[left] * year;
        int sellRight = method2(prices, left, right - 1, dp) + prices[right] * year;

        dp[left][right] = Math.max(sellLeft, sellRight);
        return dp[left][right];
    }

    /**
     * Bottom-up dynamic programming solution.
     *
     * @param prices array of wine prices
     * @return maximum profit obtainable from selling all wines
     * @throws IllegalArgumentException if prices is null or empty
     */
    public static int method3(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int n = prices.length;
        int[][] dp = new int[n][n];

        for (int gap = 0; gap < n; gap++) {
            for (int left = 0; left + gap < n; left++) {
                int right = left + gap;
                int year = computeYear(left, right, n);

                if (left == right) {
                    dp[left][right] = prices[left] * year;
                } else {
                    int sellLeft = dp[left + 1][right] + prices[left] * year;
                    int sellRight = dp[left][right - 1] + prices[right] * year;
                    dp[left][right] = Math.max(sellLeft, sellRight);
                }
            }
        }

        return dp[0][n - 1];
    }

    /**
     * Computes the current year based on the remaining interval [left, right].
     *
     * @param left  current left index
     * @param right current right index
     * @param n     total number of wines
     * @return current year number
     */
    private static int computeYear(int left, int right, int n) {
        int remaining = right - left + 1;
        return n - remaining + 1;
    }
}