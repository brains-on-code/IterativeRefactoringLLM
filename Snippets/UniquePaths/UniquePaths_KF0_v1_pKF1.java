package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class UniquePaths {

    private UniquePaths() {
    }

    /**
     * Calculates the number of unique paths using a 1D dynamic programming array.
     * Time complexity O(rows * cols)
     * Space complexity O(min(rows, cols))
     *
     * @param rows The number of rows in the grid.
     * @param cols The number of columns in the grid.
     * @return The number of unique paths.
     */
    public static int uniquePaths(final int rows, final int cols) {
        if (rows > cols) {
            return uniquePaths(cols, rows);
        }

        int[] pathsPerColumn = new int[cols];
        Arrays.fill(pathsPerColumn, 1);

        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                pathsPerColumn[col] = Math.addExact(pathsPerColumn[col], pathsPerColumn[col - 1]);
            }
        }

        return pathsPerColumn[cols - 1];
    }

    /**
     * Calculates the number of unique paths using a 2D dynamic programming array.
     * Time complexity O(rows * cols)
     * Space complexity O(rows * cols)
     *
     * @param rows The number of rows in the grid.
     * @param cols The number of columns in the grid.
     * @return The number of unique paths.
     */
    public static int uniquePaths2(final int rows, final int cols) {
        int[][] pathsGrid = new int[rows][cols];

        for (int row = 0; row < rows; row++) {
            pathsGrid[row][0] = 1;
        }

        for (int col = 0; col < cols; col++) {
            pathsGrid[0][col] = 1;
        }

        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                pathsGrid[row][col] = Math.addExact(pathsGrid[row - 1][col], pathsGrid[row][col - 1]);
            }
        }

        return pathsGrid[rows - 1][cols - 1];
    }
}