package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class UniquePaths {

    private UniquePaths() {
    }

    public static int countUniquePaths(final int rows, final int columns) {
        if (rows > columns) {
            return countUniquePaths(columns, rows);
        }

        int[] pathsInColumn = new int[columns];
        Arrays.fill(pathsInColumn, 1);

        for (int row = 1; row < rows; row++) {
            for (int column = 1; column < columns; column++) {
                pathsInColumn[column] =
                    Math.addExact(pathsInColumn[column], pathsInColumn[column - 1]);
            }
        }

        return pathsInColumn[columns - 1];
    }

    public static int countUniquePathsWithGrid(final int rows, final int columns) {
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