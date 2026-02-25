package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Computes the number of unique paths in an {@code m x n} grid where a robot
 * starts at the top-left corner and can move only right or down.
 */
public final class UniquePaths {

    private UniquePaths() {
        // Prevent instantiation of utility class
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
        // Ensure the first argument is the smaller dimension to minimize space usage
        if (m > n) {
            return uniquePaths(n, m);
        }

        // dp[col] represents the number of ways to reach the current cell in the current row
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // Base case: first row has exactly one path to each cell

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                // Number of paths to current cell = paths from above (dp[col])
                // + paths from left (dp[col - 1])
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
        // dp[row][col] represents the number of ways to reach cell (row, col)
        int[][] dp = new int[m][n];

        // Initialize first column: only one way to move (all downward moves)
        for (int row = 0; row < m; row++) {
            dp[row][0] = 1;
        }

        // Initialize first row: only one way to move (all rightward moves)
        for (int col = 0; col < n; col++) {
            dp[0][col] = 1;
        }

        // Fill in the rest of the table:
        // each cell is reachable from the cell above or the cell to the left
        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[row][col] = Math.addExact(dp[row - 1][col], dp[row][col - 1]);
            }
        }
        return dp[m - 1][n - 1];
    }
}