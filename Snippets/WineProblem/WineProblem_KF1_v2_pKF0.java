package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic programming solutions for the "wine selling" problem:
 * Given an array of wine prices, each year you may sell either the leftmost
 * or rightmost bottle. The price of a bottle sold in year y is y * price.
 * The goal is to maximize total revenue.
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
     * @return maximum revenue obtainable from prices[left..right]
     */
    public static int method1(int[] prices, int left, int right) {
        int year = calculateYear(prices.length, left, right);

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
     * @param dp     memoization table; dp[i][j] stores result for prices[i..j]
     * @return maximum revenue obtainable from prices[left..right]
     */
    public static int method2(int[] prices, int left, int right, int[][] dp) {
        int year = calculateYear(prices.length, left, right);

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
     * @return maximum revenue obtainable from all wines
     */
    public static int method3(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int n = prices.length;
        int[][] dp = new int[n][n];

        for (int length = 0; length < n; length++) {
            for (int left = 0; left + length < n; left++) {
                int right = left + length;
                int year = calculateYear(n, left, right);

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

    private static int calculateYear(int totalWines, int left, int right) {
        int remaining = right - left + 1;
        return totalWines - remaining + 1;
    }
}