package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class UniquePaths {

    private UniquePaths() {
    }

    /**
     * Calculates the number of unique paths using a 1D dynamic programming array.
     * Time complexity O(rows * columns)
     * Space complexity O(min(rows, columns))
     *
     * @param rows    The number of rows in the grid.
     * @param columns The number of columns in the grid.
     * @return The number of unique paths.
     */
    public static int uniquePaths(final int rows, final int columns) {
        if (rows > columns) {
            return uniquePaths(columns, rows);
        }

        int[] paths = new int[columns];
        Arrays.fill(paths, 1);

        for (int row = 1; row < rows; row++) {
            for (int column = 1; column < columns; column++) {
                paths[column] = Math.addExact(paths[column], paths[column - 1]);
            }
        }

        return paths[columns - 1];
    }

    /**
     * Calculates the number of unique paths using a 2D dynamic programming array.
     * Time complexity O(rows * columns)
     * Space complexity O(rows * columns)
     *
     * @param rows    The number of rows in the grid.
     * @param columns The number of columns in the grid.
     * @return The number of unique paths.
     */
    public static int uniquePaths2(final int rows, final int columns) {
        int[][] pathCounts = new int[rows][columns];

        for (int row = 0; row < rows; row++) {
            pathCounts[row][0] = 1;
        }

        for (int column = 0; column < columns; column++) {
            pathCounts[0][column] = 1;
        }

        for (int row = 1; row < rows; row++) {
            for (int column = 1; column < columns; column++) {
                pathCounts[row][column] =
                    Math.addExact(pathCounts[row - 1][column], pathCounts[row][column - 1]);
            }
        }

        return pathCounts[rows - 1][columns - 1];
    }
}