package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class GridUniquePaths {

    private GridUniquePaths() {
    }

    public static int countUniquePathsOptimized(final int rowCount, final int columnCount) {
        if (rowCount > columnCount) {
            return countUniquePathsOptimized(columnCount, rowCount);
        }

        int[] pathsInCurrentRow = new int[columnCount];
        Arrays.fill(pathsInCurrentRow, 1);

        for (int currentRow = 1; currentRow < rowCount; currentRow++) {
            for (int currentColumn = 1; currentColumn < columnCount; currentColumn++) {
                pathsInCurrentRow[currentColumn] =
                    Math.addExact(pathsInCurrentRow[currentColumn], pathsInCurrentRow[currentColumn - 1]);
            }
        }

        return pathsInCurrentRow[columnCount - 1];
    }

    public static int countUniquePaths(final int rowCount, final int columnCount) {
        int[][] pathCountsGrid = new int[rowCount][columnCount];

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            pathCountsGrid[rowIndex][0] = 1;
        }

        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            pathCountsGrid[0][columnIndex] = 1;
        }

        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 1; columnIndex < columnCount; columnIndex++) {
                pathCountsGrid[rowIndex][columnIndex] =
                    Math.addExact(pathCountsGrid[rowIndex - 1][columnIndex], pathCountsGrid[rowIndex][columnIndex - 1]);
            }
        }

        return pathCountsGrid[rowCount - 1][columnCount - 1];
    }
}