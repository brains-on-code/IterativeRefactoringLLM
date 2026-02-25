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
     * @param leftIndex   Left index of the wine to consider.
     * @param rightIndex  Right index of the wine to consider.
     * @return Maximum profit obtainable by selling the wines.
     */
    public static int maxProfitRecursive(int[] prices, int leftIndex, int rightIndex) {
        int totalWineCount = prices.length;
        int remainingWineCount = rightIndex - leftIndex + 1;
        int currentYear = (totalWineCount - remainingWineCount) + 1;

        if (leftIndex == rightIndex) {
            return prices[leftIndex] * currentYear;
        }

        int profitIfSellLeft =
                maxProfitRecursive(prices, leftIndex + 1, rightIndex) + prices[leftIndex] * currentYear;
        int profitIfSellRight =
                maxProfitRecursive(prices, leftIndex, rightIndex - 1) + prices[rightIndex] * currentYear;

        return Math.max(profitIfSellLeft, profitIfSellRight);
    }

    /**
     * Calculate maximum profit using top-down dynamic programming with memoization.
     *
     * @param prices Array of wine prices.
     * @param leftIndex   Left index of the wine to consider.
     * @param rightIndex  Right index of the wine to consider.
     * @param memoTable   2D array to store results of subproblems.
     * @return Maximum profit obtainable by selling the wines.
     */
    public static int maxProfitTopDown(int[] prices, int leftIndex, int rightIndex, int[][] memoTable) {
        int totalWineCount = prices.length;
        int remainingWineCount = rightIndex - leftIndex + 1;
        int currentYear = (totalWineCount - remainingWineCount) + 1;

        if (leftIndex == rightIndex) {
            return prices[leftIndex] * currentYear;
        }

        if (memoTable[leftIndex][rightIndex] != 0) {
            return memoTable[leftIndex][rightIndex];
        }

        int profitIfSellLeft =
                maxProfitTopDown(prices, leftIndex + 1, rightIndex, memoTable) + prices[leftIndex] * currentYear;
        int profitIfSellRight =
                maxProfitTopDown(prices, leftIndex, rightIndex - 1, memoTable) + prices[rightIndex] * currentYear;

        int bestProfit = Math.max(profitIfSellLeft, profitIfSellRight);
        memoTable[leftIndex][rightIndex] = bestProfit;

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

        int totalWineCount = prices.length;
        int[][] profitTable = new int[totalWineCount][totalWineCount];

        for (int windowSize = 0; windowSize <= totalWineCount - 1; windowSize++) {
            for (int leftIndex = 0; leftIndex <= totalWineCount - windowSize - 1; leftIndex++) {
                int rightIndex = leftIndex + windowSize;
                int remainingWineCount = rightIndex - leftIndex + 1;
                int currentYear = (totalWineCount - remainingWineCount) + 1;

                if (leftIndex == rightIndex) {
                    profitTable[leftIndex][rightIndex] = prices[leftIndex] * currentYear;
                } else {
                    int profitIfSellLeft =
                            profitTable[leftIndex + 1][rightIndex] + prices[leftIndex] * currentYear;
                    int profitIfSellRight =
                            profitTable[leftIndex][rightIndex - 1] + prices[rightIndex] * currentYear;
                    profitTable[leftIndex][rightIndex] = Math.max(profitIfSellLeft, profitIfSellRight);
                }
            }
        }

        return profitTable[0][totalWineCount - 1];
    }
}