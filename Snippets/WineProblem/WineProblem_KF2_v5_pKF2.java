package com.thealgorithms.dynamicprogramming;

public final class WineProblem {

    private WineProblem() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the maximum profit obtainable from selling wines in the range
     * [start, end] using pure recursion (no memoization).
     *
     * @param prices array of wine prices
     * @param start  starting index of the current range
     * @param end    ending index of the current range
     */
    public static int maxProfitRecursive(int[] prices, int start, int end) {
        int year = currentYear(prices.length, start, end);

        if (start == end) {
            return prices[start] * year;
        }

        int sellStart =
            maxProfitRecursive(prices, start + 1, end) + prices[start] * year;
        int sellEnd =
            maxProfitRecursive(prices, start, end - 1) + prices[end] * year;

        return Math.max(sellStart, sellEnd);
    }

    /**
     * Returns the maximum profit obtainable from selling wines in the range
     * [start, end] using top-down dynamic programming with memoization.
     *
     * @param prices array of wine prices
     * @param start  starting index of the current range
     * @param end    ending index of the current range
     * @param memo   memo[start][end] stores the best profit for prices[start..end]
     */
    public static int maxProfitTopDown(int[] prices, int start, int end, int[][] memo) {
        int year = currentYear(prices.length, start, end);

        if (start == end) {
            return prices[start] * year;
        }

        if (memo[start][end] != 0) {
            return memo[start][end];
        }

        int sellStart =
            maxProfitTopDown(prices, start + 1, end, memo) + prices[start] * year;
        int sellEnd =
            maxProfitTopDown(prices, start, end - 1, memo) + prices[end] * year;

        memo[start][end] = Math.max(sellStart, sellEnd);
        return memo[start][end];
    }

    /**
     * Returns the maximum profit obtainable from selling all wines using
     * bottom-up dynamic programming.
     *
     * @param prices array of wine prices
     */
    public static int maxProfitBottomUp(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int n = prices.length;
        int[][] dp = new int[n][n];

        // slide is the window size offset: end = start + slide
        for (int slide = 0; slide < n; slide++) {
            for (int start = 0; start + slide < n; start++) {
                int end = start + slide;
                int year = currentYear(n, start, end);

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

    /**
     * Returns the current year based on the number of remaining bottles.
     *
     * @param totalBottles total number of bottles
     * @param start        starting index of the current range
     * @param end          ending index of the current range
     */
    private static int currentYear(int totalBottles, int start, int end) {
        int remainingBottles = end - start + 1;
        return totalBottles - remainingBottles + 1;
    }
}