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

        int m = rows;
        int n = cols;

        // Ensure m <= n to minimize the DP array size
        if (m > n) {
            int temp = m;
            m = n;
            n = temp;
        }

        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[col] = Math.addExact(dp[col], dp[col - 1]);
            }
        }

        return dp[n - 1];
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

        // Initialize first column
        for (int row = 0; row < rows; row++) {
            dp[row][0] = 1;
        }

        // Initialize first row
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

    private static boolean isValidGrid(int rows, int cols) {
        return rows > 0 && cols > 0;
    }
}