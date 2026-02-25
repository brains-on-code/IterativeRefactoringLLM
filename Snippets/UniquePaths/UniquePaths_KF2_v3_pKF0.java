package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class UniquePaths {

    private UniquePaths() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the number of unique paths in an m x n grid using
     * a 1D dynamic programming array (space-optimized).
     *
     * @param rows number of rows
     * @param cols number of columns
     * @return number of unique paths from top-left to bottom-right
     */
    public static int uniquePaths(final int rows, final int cols) {
        if (!isValidGrid(rows, cols)) {
            return 0;
        }

        int minDimension = Math.min(rows, cols);
        int maxDimension = Math.max(rows, cols);

        int[] dp = new int[maxDimension];
        Arrays.fill(dp, 1);

        for (int row = 1; row < minDimension; row++) {
            for (int col = 1; col < maxDimension; col++) {
                dp[col] = Math.addExact(dp[col], dp[col - 1]);
            }
        }

        return dp[maxDimension - 1];
    }

    /**
     * Computes the number of unique paths in an m x n grid using
     * a 2D dynamic programming table.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @return number of unique paths from top-left to bottom-right
     */
    public static int uniquePaths2(final int rows, final int cols) {
        if (!isValidGrid(rows, cols)) {
            return 0;
        }

        int[][] dp = new int[rows][cols];

        // Initialize first row and first column
        for (int row = 0; row < rows; row++) {
            dp[row][0] = 1;
        }
        for (int col = 0; col < cols; col++) {
            dp[0][col] = 1;
        }

        // Fill the rest of the table
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                dp[row][col] = Math.addExact(dp[row - 1][col], dp[row][col - 1]);
            }
        }

        return dp[rows - 1][cols - 1];
    }

    private static boolean isValidGrid(final int rows, final int cols) {
        return rows > 0 && cols > 0;
    }
}