package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Utility class for computing the number of unique paths in an m x n grid
 * when moving only right or down from the top-left to the bottom-right corner.
 */
public final class UniquePaths {

    private UniquePaths() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the number of unique paths using a space-optimized 1D
     * dynamic programming approach.
     *
     * dp[col] represents the number of ways to reach the current row's cell
     * at column {@code col}.
     *
     * @param m number of rows
     * @param n number of columns
     * @return number of unique paths
     */
    public static int uniquePaths(final int m, final int n) {
        // Ensure m <= n so the DP array is as small as possible.
        if (m > n) {
            return uniquePaths(n, m);
        }

        int[] dp = new int[n];
        // Base case for the first row: only one way to reach each cell (move right).
        Arrays.fill(dp, 1);

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                // Ways to current cell = from above (dp[col]) + from left (dp[col - 1]).
                dp[col] = Math.addExact(dp[col], dp[col - 1]);
            }
        }

        return dp[n - 1];
    }

    /**
     * Computes the number of unique paths using a 2D dynamic programming table.
     *
     * dp[row][col] represents the number of ways to reach cell (row, col).
     *
     * @param m number of rows
     * @param n number of columns
     * @return number of unique paths
     */
    public static int uniquePaths2(final int m, final int n) {
        int[][] dp = new int[m][n];

        // Base case for the first column: only one way (move down).
        for (int row = 0; row < m; row++) {
            dp[row][0] = 1;
        }

        // Base case for the first row: only one way (move right).
        for (int col = 0; col < n; col++) {
            dp[0][col] = 1;
        }

        // For each remaining cell, sum ways from the top and from the left.
        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[row][col] = Math.addExact(dp[row - 1][col], dp[row][col - 1]);
            }
        }

        return dp[m - 1][n - 1];
    }
}