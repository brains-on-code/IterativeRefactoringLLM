package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class UniquePaths {

    private UniquePaths() {
    }

    /**
     * Calculates the number of unique paths using a 1D dynamic programming array.
     * Time complexity O(rowCount * columnCount)
     * Space complexity O(min(rowCount, columnCount))
     *
     * @param rowCount    The number of rows in the grid.
     * @param columnCount The number of columns in the grid.
     * @return The number of unique paths.
     */
    public static int uniquePaths(final int rowCount, final int columnCount) {
        if (rowCount > columnCount) {
            return uniquePaths(columnCount, rowCount);
        }

        int[] pathsPerColumn = new int[columnCount];
        Arrays.fill(pathsPerColumn, 1);

        for (int currentRow = 1; currentRow < rowCount; currentRow++) {
            for (int currentColumn = 1; currentColumn < columnCount; currentColumn++) {
                pathsPerColumn[currentColumn] =
                    Math.addExact(pathsPerColumn[currentColumn], pathsPerColumn[currentColumn - 1]);
            }
        }

        return pathsPerColumn[columnCount - 1];
    }

    /**
     * Calculates the number of unique paths using a 2D dynamic programming array.
     * Time complexity O(rowCount * columnCount)
     * Space complexity O(rowCount * columnCount)
     *
     * @param rowCount    The number of rows in the grid.
     * @param columnCount The number of columns in the grid.
     * @return The number of unique paths.
     */
    public static int uniquePaths2(final int rowCount, final int columnCount) {
        int[][] pathCountsGrid = new int[rowCount][columnCount];

        for (int row = 0; row < rowCount; row++) {
            pathCountsGrid[row][0] = 1;
        }

        for (int column = 0; column < columnCount; column++) {
            pathCountsGrid[0][column] = 1;
        }

        for (int row = 1; row < rowCount; row++) {
            for (int column = 1; column < columnCount; column++) {
                pathCountsGrid[row][column] =
                    Math.addExact(pathCountsGrid[row - 1][column], pathCountsGrid[row][column - 1]);
            }
        }

        return pathCountsGrid[rowCount - 1][columnCount - 1];
    }
}