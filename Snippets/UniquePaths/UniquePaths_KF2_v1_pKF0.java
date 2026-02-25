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
     * @param m number of rows
     * @param n number of columns
     * @return number of unique paths from top-left to bottom-right
     */
    public static int uniquePaths(final int m, final int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }

        // Ensure m <= n to minimize the DP array size
        if (m > n) {
            return uniquePaths(n, m);
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
     * @param m number of rows
     * @param n number of columns
     * @return number of unique paths from top-left to bottom-right
     */
    public static int uniquePaths2(final int m, final int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }

        int[][] dp = new int[m][n];

        // Initialize first column
        for (int row = 0; row < m; row++) {
            dp[row][0] = 1;
        }

        // Initialize first row
        for (int col = 0; col < n; col++) {
            dp[0][col] = 1;
        }

        // Fill the rest of the table
        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[row][col] = Math.addExact(dp[row - 1][col], dp[row][col - 1]);
            }
        }

        return dp[m - 1][n - 1];
    }
}