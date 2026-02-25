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
 * 1. **Recursion**: A straightforward recursive method that computes the maximum profit.
 *    - Time Complexity: O(2^N)
 *    - Space Complexity: O(N) due to recursive calls.
 *
 * 2. **Top-Down Dynamic Programming (Memoization)**: This approach caches the results of subproblems
 *    to avoid redundant computations.
 *    - Time Complexity: O(N^2)
 *    - Space Complexity: O(N^2) for the storage of results and O(N) for recursion stack.
 *
 * 3. **Bottom-Up Dynamic Programming (Tabulation)**: This method builds a table iteratively to
 *    compute the maximum profit for all possible subproblems.
 *    - Time Complexity: O(N^2)
 *    - Space Complexity: O(N^2) for the table.
 */
public final class WineProblem {

    private WineProblem() {
    }

    /**
     * Calculate maximum profit using recursion.
     *
     * @param prices Array of wine prices.
     * @param startIndex  Start index of the wine to consider.
     * @param endIndex    End index of the wine to consider.
     * @return Maximum profit obtainable by selling the wines.
     */
    public static int maxProfitRecursive(int[] prices, int startIndex, int endIndex) {
        int totalWines = prices.length;
        int remainingWines = endIndex - startIndex + 1;
        int currentYear = (totalWines - remainingWines) + 1;

        if (startIndex == endIndex) {
            return prices[startIndex] * currentYear;
        }

        int sellStart = maxProfitRecursive(prices, startIndex + 1, endIndex) + prices[startIndex] * currentYear;
        int sellEnd = maxProfitRecursive(prices, startIndex, endIndex - 1) + prices[endIndex] * currentYear;

        return Math.max(sellStart, sellEnd);
    }

    /**
     * Calculate maximum profit using top-down dynamic programming with memoization.
     *
     * @param prices  Array of wine prices.
     * @param startIndex   Start index of the wine to consider.
     * @param endIndex     End index of the wine to consider.
     * @param memo 2D array to store results of subproblems.
     * @return Maximum profit obtainable by selling the wines.
     */
    public static int maxProfitTopDown(int[] prices, int startIndex, int endIndex, int[][] memo) {
        int totalWines = prices.length;
        int remainingWines = endIndex - startIndex + 1;
        int currentYear = (totalWines - remainingWines) + 1;

        if (startIndex == endIndex) {
            return prices[startIndex] * currentYear;
        }

        if (memo[startIndex][endIndex] != 0) {
            return memo[startIndex][endIndex];
        }

        int sellStart = maxProfitTopDown(prices, startIndex + 1, endIndex, memo) + prices[startIndex] * currentYear;
        int sellEnd = maxProfitTopDown(prices, startIndex, endIndex - 1, memo) + prices[endIndex] * currentYear;

        int bestProfit = Math.max(sellStart, sellEnd);
        memo[startIndex][endIndex] = bestProfit;

        return bestProfit;
    }

    /**
     * Calculate maximum profit using bottom-up dynamic programming with tabulation.
     *
     * @param prices Array of wine prices.
     * @throws IllegalArgumentException if the input array is null or empty.
     * @return Maximum profit obtainable by selling the wines.
     */
    public static int maxProfitBottomUp(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        int totalWines = prices.length;
        int[][] dp = new int[totalWines][totalWines];

        for (int windowSize = 0; windowSize <= totalWines - 1; windowSize++) {
            for (int startIndex = 0; startIndex <= totalWines - windowSize - 1; startIndex++) {
                int endIndex = startIndex + windowSize;
                int remainingWines = endIndex - startIndex + 1;
                int currentYear = (totalWines - remainingWines) + 1;

                if (startIndex == endIndex) {
                    dp[startIndex][endIndex] = prices[startIndex] * currentYear;
                } else {
                    int sellStart = dp[startIndex + 1][endIndex] + prices[startIndex] * currentYear;
                    int sellEnd = dp[startIndex][endIndex - 1] + prices[endIndex] * currentYear;
                    dp[startIndex][endIndex] = Math.max(sellStart, sellEnd);
                }
            }
        }

        return dp[0][totalWines - 1];
    }
}