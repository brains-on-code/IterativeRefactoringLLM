package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Computes the number of unique paths in an m x n grid when moving only
 * right or down from the top-left to the bottom-right corner.
 */
public final class UniquePaths {

    private UniquePaths() {
        // Prevent instantiation of utility class.
    }

    /**
     * Computes the number of unique paths using a 1D dynamic programming array
     * (space-optimized).
     *
     * dp[col] stores the number of ways to reach the current row's cell at column col.
     *
     * @param m number of rows
     * @param n number of columns
     * @return number of unique paths
     */
    public static int uniquePaths(final int m, final int n) {
        // Normalize so that m <= n to keep the DP array as small as possible.
        if (m > n) {
            return uniquePaths(n, m);
        }

        int[] dp = new int[n];
        // First row: only one way to reach each cell (all moves to the right).
        Arrays.fill(dp, 1);

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                // Current cell = ways from above (dp[col]) + ways from left (dp[col - 1]).
                dp[col] = Math.addExact(dp[col], dp[col - 1]);
            }
        }

        return dp[n - 1];
    }

    /**
     * Computes the number of unique paths using a 2D dynamic programming table.
     *
     * dp[row][col] stores the number of ways to reach cell (row, col).
     *
     * @param m number of rows
     * @param n number of columns
     * @return number of unique paths
     */
    public static int uniquePaths2(final int m, final int n) {
        int[][] dp = new int[m][n];

        // First column: only one way to move (all moves down).
        for (int row = 0; row < m; row++) {
            dp[row][0] = 1;
        }

        // First row: only one way to move (all moves right).
        for (int col = 0; col < n; col++) {
            dp[0][col] = 1;
        }

        // Remaining cells: sum of ways from the top and from the left.
        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[row][col] = Math.addExact(dp[row - 1][col], dp[row][col - 1]);
            }
        }

        return dp[m - 1][n - 1];
    }
}