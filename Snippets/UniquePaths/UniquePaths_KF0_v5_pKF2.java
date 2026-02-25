package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Computes the number of unique paths in an {@code m x n} grid where a robot
 * starts at the top-left corner and can move only right or down.
 */
public final class UniquePaths {

    private UniquePaths() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the number of unique paths using a 1D dynamic programming array.
     * Time complexity: O(m * n)
     * Space complexity: O(min(m, n))
     *
     * @param m number of rows
     * @param n number of columns
     * @return number of unique paths
     */
    public static int uniquePaths(final int m, final int n) {
        // Always treat the first argument as the smaller dimension to reduce space usage
        if (m > n) {
            return uniquePaths(n, m);
        }

        // dp[col] = number of ways to reach column `col` in the current row
        int[] dp = new int[n];

        // First row: only one way to reach each cell (move right only)
        Arrays.fill(dp, 1);

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                // Current cell = paths from above (dp[col]) + paths from left (dp[col - 1])
                dp[col] = Math.addExact(dp[col], dp[col - 1]);
            }
        }
        return dp[n - 1];
    }

    /**
     * Computes the number of unique paths using a 2D dynamic programming table.
     * Time complexity: O(m * n)
     * Space complexity: O(m * n)
     *
     * @param m number of rows
     * @param n number of columns
     * @return number of unique paths
     */
    public static int uniquePaths2(final int m, final int n) {
        // dp[row][col] = number of ways to reach cell (row, col)
        int[][] dp = new int[m][n];

        // First column: only one way to move (down only)
        for (int row = 0; row < m; row++) {
            dp[row][0] = 1;
        }

        // First row: only one way to move (right only)
        for (int col = 0; col < n; col++) {
            dp[0][col] = 1;
        }

        // Remaining cells: from above or from left
        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[row][col] = Math.addExact(dp[row - 1][col], dp[row][col - 1]);
            }
        }
        return dp[m - 1][n - 1];
    }
}