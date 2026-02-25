package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class UniquePaths {

    private UniquePaths() {
    }

    public static int countUniquePaths(final int rowCount, final int columnCount) {
        if (rowCount > columnCount) {
            return countUniquePaths(columnCount, rowCount);
        }
        int[] pathsInColumn = new int[columnCount];
        Arrays.fill(pathsInColumn, 1);
        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 1; columnIndex < columnCount; columnIndex++) {
                pathsInColumn[columnIndex] =
                    Math.addExact(pathsInColumn[columnIndex], pathsInColumn[columnIndex - 1]);
            }
        }
        return pathsInColumn[columnCount - 1];
    }

    public static int countUniquePathsWithGrid(final int rowCount, final int columnCount) {
        int[][] pathsGrid = new int[rowCount][columnCount];
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            pathsGrid[rowIndex][0] = 1;
        }
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            pathsGrid[0][columnIndex] = 1;
        }
        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 1; columnIndex < columnCount; columnIndex++) {
                pathsGrid[rowIndex][columnIndex] =
                    Math.addExact(pathsGrid[rowIndex - 1][columnIndex], pathsGrid[rowIndex][columnIndex - 1]);
            }
        }
        return pathsGrid[rowCount - 1][columnCount - 1];
    }
}