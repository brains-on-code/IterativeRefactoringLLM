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
        validateDimensions(rows, columns);

        // Ensure rows <= columns to minimize the DP array size
        if (rows > columns) {
            return countUniquePaths1D(columns, rows);
        }

        int[] dp = new int[columns];
        Arrays.fill(dp, 1);

        for (int row = 1; row < rows; row++) {
            updateRow(dp, columns);
        }

        return dp[columns - 1];
    }

    private static void updateRow(final int[] dp, final int columns) {
        for (int col = 1; col < columns; col++) {
            dp[col] = Math.addExact(dp[col], dp[col - 1]);
        }
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
        validateDimensions(rows, columns);

        int[][] dp = new int[rows][columns];
        initializeFirstRowAndColumn(dp, rows, columns);
        fillDpTable(dp, rows, columns);

        return dp[rows - 1][columns - 1];
    }

    private static void initializeFirstRowAndColumn(final int[][] dp, final int rows, final int columns) {
        for (int row = 0; row < rows; row++) {
            dp[row][0] = 1;
        }
        for (int col = 0; col < columns; col++) {
            dp[0][col] = 1;
        }
    }

    private static void fillDpTable(final int[][] dp, final int rows, final int columns) {
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < columns; col++) {
                dp[row][col] = Math.addExact(dp[row - 1][col], dp[row][col - 1]);
            }
        }
    }

    private static void validateDimensions(final int rows, final int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("rows and columns must be positive");
        }
    }
}