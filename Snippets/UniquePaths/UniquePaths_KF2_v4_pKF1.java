package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class UniquePaths {

    private UniquePaths() {
    }

    public static int countUniquePaths(final int rowCount, final int columnCount) {
        if (rowCount > columnCount) {
            return countUniquePaths(columnCount, rowCount);
        }

        int[] pathsPerColumn = new int[columnCount];
        Arrays.fill(pathsPerColumn, 1);

        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 1; columnIndex < columnCount; columnIndex++) {
                pathsPerColumn[columnIndex] =
                    Math.addExact(pathsPerColumn[columnIndex], pathsPerColumn[columnIndex - 1]);
            }
        }

        return pathsPerColumn[columnCount - 1];
    }

    public static int countUniquePathsWithGrid(final int rowCount, final int columnCount) {
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