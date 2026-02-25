package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Computes the number of unique paths in an m x n grid where a robot
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
        // Ensure the first argument is the smaller dimension to minimize space
        if (m > n) {
            return uniquePaths(n, m);
        }

        int[] dp = new int[n];
        Arrays.fill(dp, 1); // First row: only one way to move (all rights)

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                // Paths to current cell = paths from top + paths from left
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
        int[][] dp = new int[m][n];

        // First column: only one way to move (all downs)
        for (int row = 0; row < m; row++) {
            dp[row][0] = 1;
        }

        // First row: only one way to move (all rights)
        for (int col = 0; col < n; col++) {
            dp[0][col] = 1;
        }

        // Internal cells: sum of paths from top and left neighbors
        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[row][col] = Math.addExact(dp[row - 1][col], dp[row][col - 1]);
            }
        }
        return dp[m - 1][n - 1];
    }
}