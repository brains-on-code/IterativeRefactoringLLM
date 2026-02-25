package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class GridUniquePaths {

    private GridUniquePaths() {
    }

    public static int countUniquePathsOptimized(final int rowCount, final int columnCount) {
        if (rowCount > columnCount) {
            return countUniquePathsOptimized(columnCount, rowCount);
        }

        int[] pathCounts = new int[columnCount];
        Arrays.fill(pathCounts, 1);

        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 1; columnIndex < columnCount; columnIndex++) {
                pathCounts[columnIndex] =
                    Math.addExact(pathCounts[columnIndex], pathCounts[columnIndex - 1]);
            }
        }

        return pathCounts[columnCount - 1];
    }

    public static int countUniquePaths(final int rowCount, final int columnCount) {
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
                    Math.addExact(pathCounts[rowIndex - 1][columnIndex],
                                  pathCounts[rowIndex][columnIndex - 1]);
            }
        }

        return pathCounts[rowCount - 1][columnCount - 1];
    }
}