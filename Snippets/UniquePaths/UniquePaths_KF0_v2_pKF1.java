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

        int[] pathsForColumn = new int[columnCount];
        Arrays.fill(pathsForColumn, 1);

        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 1; columnIndex < columnCount; columnIndex++) {
                pathsForColumn[columnIndex] =
                    Math.addExact(pathsForColumn[columnIndex], pathsForColumn[columnIndex - 1]);
            }
        }

        return pathsForColumn[columnCount - 1];
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
        int[][] pathCounts = new int[rowCount][columnCount];

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            pathCounts[rowIndex][0] = 1;
        }

        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            pathCounts[0][columnIndex] = 1;
        }

        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 1; columnIndex < columnCount; columnIndex++) {
                pathCounts[rowIndex][columnIndex] =
                    Math.addExact(pathCounts[rowIndex - 1][columnIndex], pathCounts[rowIndex][columnIndex - 1]);
            }
        }

        return pathCounts[rowCount - 1][columnCount - 1];
    }
}