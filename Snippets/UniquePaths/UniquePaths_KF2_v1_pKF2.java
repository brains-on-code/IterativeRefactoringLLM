package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Provides methods to compute the number of unique paths in an m x n grid,
 * moving only right or down from the top-left to the bottom-right corner.
 */
public final class UniquePaths {

    private UniquePaths() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the number of unique paths using a 1D dynamic programming array
     * for space optimization.
     *
     * @param m number of rows
     * @param n number of columns
     * @return number of unique paths
     */
    public static int uniquePaths(final int m, final int n) {
        // Ensure m <= n to minimize the DP array size.
        if (m > n) {
            return uniquePaths(n, m);
        }

        // dp[j] represents the number of ways to reach cell in current row, column j.
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // First row has exactly one way to reach each cell.

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                // Number of ways to reach current cell is sum of ways from
                // the cell above (dp[col]) and the cell to the left (dp[col - 1]).
                dp[col] = Math.addExact(dp[col], dp[col - 1]);
            }
        }

        return dp[n - 1];
    }

    /**
     * Computes the number of unique paths using a 2D dynamic programming table.
     *
     * @param m number of rows
     * @param n number of columns
     * @return number of unique paths
     */
    public static int uniquePaths2(final int m, final int n) {
        int[][] dp = new int[m][n];

        // Initialize first column: only one way to move (all down).
        for (int row = 0; row < m; row++) {
            dp[row][0] = 1;
        }

        // Initialize first row: only one way to move (all right).
        for (int col = 0; col < n; col++) {
            dp[0][col] = 1;
        }

        // Fill the DP table: each cell is reachable from top or left neighbor.
        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[row][col] = Math.addExact(dp[row - 1][col], dp[row][col - 1]);
            }
        }

        return dp[m - 1][n - 1];
    }
}