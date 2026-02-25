package com.thealgorithms.dynamicprogramming;

public final class WineProblem {

    private WineProblem() {
        // Utility class; prevent instantiation
    }

    public static int wpRecursion(int[] prices, int startIndex, int endIndex) {
        int year = calculateYear(prices.length, startIndex, endIndex);

        if (isSingleBottle(startIndex, endIndex)) {
            return prices[startIndex] * year;
        }

        int sellStart = wpRecursion(prices, startIndex + 1, endIndex) + prices[startIndex] * year;
        int sellEnd = wpRecursion(prices, startIndex, endIndex - 1) + prices[endIndex] * year;

        return Math.max(sellStart, sellEnd);
    }

    public static int wptd(int[] prices, int startIndex, int endIndex, int[][] memo) {
        int year = calculateYear(prices.length, startIndex, endIndex);

        if (isSingleBottle(startIndex, endIndex)) {
            return prices[startIndex] * year;
        }

        if (memo[startIndex][endIndex] != 0) {
            return memo[startIndex][endIndex];
        }

        int sellStart =
                wptd(prices, startIndex + 1, endIndex, memo) + prices[startIndex] * year;
        int sellEnd =
                wptd(prices, startIndex, endIndex - 1, memo) + prices[endIndex] * year;

        memo[startIndex][endIndex] = Math.max(sellStart, sellEnd);
        return memo[startIndex][endIndex];
    }

    public static int wpbu(int[] prices) {
        validatePrices(prices);

        int n = prices.length;
        int[][] dp = new int[n][n];

        for (int length = 1; length <= n; length++) {
            for (int startIndex = 0; startIndex + length - 1 < n; startIndex++) {
                int endIndex = startIndex + length - 1;
                int year = calculateYear(n, startIndex, endIndex);

                if (isSingleBottle(startIndex, endIndex)) {
                    dp[startIndex][endIndex] = prices[startIndex] * year;
                } else {
                    int sellStart =
                            dp[startIndex + 1][endIndex] + prices[startIndex] * year;
                    int sellEnd =
                            dp[startIndex][endIndex - 1] + prices[endIndex] * year;

                    dp[startIndex][endIndex] = Math.max(sellStart, sellEnd);
                }
            }
        }

        return dp[0][n - 1];
    }

    private static int calculateYear(int totalWines, int startIndex, int endIndex) {
        int remaining = endIndex - startIndex + 1;
        return totalWines - remaining + 1;
    }

    private static boolean isSingleBottle(int startIndex, int endIndex) {
        return startIndex == endIndex;
    }

    private static void validatePrices(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
    }
}