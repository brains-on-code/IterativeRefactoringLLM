package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Utility class for counting unique paths in a grid using dynamic programming.
 *
 * <p>Both methods compute the number of unique paths from the top-left corner
 * to the bottom-right corner of an {@code m x n} grid, moving only right or down.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns the number of unique paths in an {@code m x n} grid using a
     * 1D dynamic programming array (space-optimized).
     *
     * <p>Time complexity: O(m * n)<br>
     * Space complexity: O(n)
     *
     * @param rows number of rows (m)
     * @param cols number of columns (n)
     * @return number of unique paths from (0, 0) to (m - 1, n - 1)
     * @throws ArithmeticException if the result overflows an {@code int}
     */
    public static int method1(final int rows, final int cols) {
        if (rows <= 0 || cols <= 0) {
            return 0;
        }

        // Ensure cols is the larger dimension to keep the DP array as small as possible.
        if (rows > cols) {
            return method1(cols, rows);
        }

        int[] dp = new int[cols];
        Arrays.fill(dp, 1); // Only one way to reach any cell in the first row.

        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                // Number of paths to current cell = paths from top + paths from left.
                dp[c] = Math.addExact(dp[c], dp[c - 1]);
            }
        }
        return dp[cols - 1];
    }

    /**
     * Returns the number of unique paths in an {@code m x n} grid using a
     * 2D dynamic programming table.
     *
     * <p>Time complexity: O(m * n)<br>
     * Space complexity: O(m * n)
     *
     * @param rows number of rows (m)
     * @param cols number of columns (n)
     * @return number of unique paths from (0, 0) to (m - 1, n - 1)
     * @throws ArithmeticException if the result overflows an {@code int}
     */
    public static int method2(final int rows, final int cols) {
        if (rows <= 0 || cols <= 0) {
            return 0;
        }

        int[][] dp = new int[rows][cols];

        // Initialize first column: only one way to move down.
        for (int r = 0; r < rows; r++) {
            dp[r][0] = 1;
        }

        // Initialize first row: only one way to move right.
        for (int c = 0; c < cols; c++) {
            dp[0][c] = 1;
        }

        // Fill the DP table.
        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                // Number of paths to current cell = paths from top + paths from left.
                dp[r][c] = Math.addExact(dp[r - 1][c], dp[r][c - 1]);
            }
        }
        return dp[rows - 1][cols - 1];
    }
}