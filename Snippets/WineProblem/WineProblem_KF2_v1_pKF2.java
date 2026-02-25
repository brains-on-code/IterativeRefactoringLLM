package com.thealgorithms.dynamicprogramming;

public final class WineProblem {

    private WineProblem() {
        // Utility class; prevent instantiation
    }

    /**
     * Pure recursive solution (no memoization).
     *
     * @param prices array of wine prices
     * @param start  starting index of current range
     * @param end    ending index of current range
     * @return maximum profit obtainable from prices[start..end]
     */
    public static int maxProfitRecursive(int[] prices, int start, int end) {
        int n = prices.length;
        int remainingBottles = end - start + 1;
        int year = n - remainingBottles + 1;

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
     * Top-down dynamic programming with memoization.
     *
     * @param prices array of wine prices
     * @param start  starting index of current range
     * @param end    ending index of current range
     * @param memo   memo[start][end] stores the best profit for prices[start..end]
     * @return maximum profit obtainable from prices[start..end]
     */
    public static int maxProfitTopDown(int[] prices, int start, int end, int[][] memo) {
        int n = prices.length;
        int remainingBottles = end - start + 1;
        int year = n - remainingBottles + 1;

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

        int best = Math.max(sellStart, sellEnd);
        memo[start][end] = best;

        return best;
    }

    /**
     * Bottom-up dynamic programming solution.
     *
     * @param prices array of wine prices
     * @return maximum profit obtainable from all wines
     */
    public static int maxProfitBottomUp(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int n = prices.length;
        int[][] dp = new int[n][n];

        // slide represents the length-1 of the current window [start..end]
        for (int slide = 0; slide < n; slide++) {
            for (int start = 0; start + slide < n; start++) {
                int end = start + slide;
                int remainingBottles = end - start + 1;
                int year = n - remainingBottles + 1;

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