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
     * @param left   Left index of the wine to consider.
     * @param right  Right index of the wine to consider.
     * @return Maximum profit obtainable by selling the wines.
     */
    public static int maxProfitRecursive(int[] prices, int left, int right) {
        int totalWines = prices.length;
        int remainingWines = right - left + 1;
        int year = (totalWines - remainingWines) + 1;

        if (left == right) {
            return prices[left] * year;
        }

        int profitSellingLeft =
                maxProfitRecursive(prices, left + 1, right) + prices[left] * year;
        int profitSellingRight =
                maxProfitRecursive(prices, left, right - 1) + prices[right] * year;

        return Math.max(profitSellingLeft, profitSellingRight);
    }

    /**
     * Calculate maximum profit using top-down dynamic programming with memoization.
     *
     * @param prices Array of wine prices.
     * @param left   Left index of the wine to consider.
     * @param right  Right index of the wine to consider.
     * @param memo   2D array to store results of subproblems.
     * @return Maximum profit obtainable by selling the wines.
     */
    public static int maxProfitTopDown(int[] prices, int left, int right, int[][] memo) {
        int totalWines = prices.length;
        int remainingWines = right - left + 1;
        int year = (totalWines - remainingWines) + 1;

        if (left == right) {
            return prices[left] * year;
        }

        if (memo[left][right] != 0) {
            return memo[left][right];
        }

        int profitSellingLeft =
                maxProfitTopDown(prices, left + 1, right, memo) + prices[left] * year;
        int profitSellingRight =
                maxProfitTopDown(prices, left, right - 1, memo) + prices[right] * year;

        int bestProfit = Math.max(profitSellingLeft, profitSellingRight);
        memo[left][right] = bestProfit;

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
            for (int left = 0; left <= totalWines - windowSize - 1; left++) {
                int right = left + windowSize;
                int remainingWines = right - left + 1;
                int year = (totalWines - remainingWines) + 1;

                if (left == right) {
                    dp[left][right] = prices[left] * year;
                } else {
                    int profitSellingLeft =
                            dp[left + 1][right] + prices[left] * year;
                    int profitSellingRight =
                            dp[left][right - 1] + prices[right] * year;
                    dp[left][right] = Math.max(profitSellingLeft, profitSellingRight);
                }
            }
        }

        return dp[0][totalWines - 1];
    }
}