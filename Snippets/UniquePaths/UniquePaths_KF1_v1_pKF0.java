package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Utility class for counting unique paths in a grid using dynamic programming.
 */
public final class UniquePaths {

    private UniquePaths() {
        // Prevent instantiation
    }

    /**
     * Computes the number of unique paths in a grid using a 1D DP array.
     * Movement is allowed only to the right or down.
     *
     * @param rows    number of rows in the grid
     * @param columns number of columns in the grid
     * @return number of unique paths from top-left to bottom-right
     */
    public static int countUniquePaths1D(final int rows, final int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("rows and columns must be positive");
        }

        // Ensure rows <= columns to minimize the DP array size
        if (rows > columns) {
            return countUniquePaths1D(columns, rows);
        }

        int[] dp = new int[columns];
        Arrays.fill(dp, 1);

        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < columns; c++) {
                dp[c] = Math.addExact(dp[c], dp[c - 1]);
            }
        }

        return dp[columns - 1];
    }

    /**
     * Computes the number of unique paths in a grid using a 2D DP array.
     * Movement is allowed only to the right or down.
     *
     * @param rows    number of rows in the grid
     * @param columns number of columns in the grid
     * @return number of unique paths from top-left to bottom-right
     */
    public static int countUniquePaths2D(final int rows, final int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("rows and columns must be positive");
        }

        int[][] dp = new int[rows][columns];

        for (int r = 0; r < rows; r++) {
            dp[r][0] = 1;
        }
        for (int c = 0; c < columns; c++) {
            dp[0][c] = 1;
        }

        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < columns; c++) {
                dp[r][c] = Math.addExact(dp[r - 1][c], dp[r][c - 1]);
            }
        }

        return dp[rows - 1][columns - 1];
    }
}