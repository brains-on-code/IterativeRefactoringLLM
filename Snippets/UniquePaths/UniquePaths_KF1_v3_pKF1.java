package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class GridUniquePaths {

    private GridUniquePaths() {
    }

    public static int countUniquePathsOptimized(final int rows, final int columns) {
        if (rows > columns) {
            return countUniquePathsOptimized(columns, rows);
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

    public static int countUniquePaths(final int rows, final int columns) {
        int[][] paths = new int[rows][columns];

        for (int row = 0; row < rows; row++) {
            paths[row][0] = 1;
        }

        for (int column = 0; column < columns; column++) {
            paths[0][column] = 1;
        }

        for (int row = 1; row < rows; row++) {
            for (int column = 1; column < columns; column++) {
                paths[row][column] =
                    Math.addExact(paths[row - 1][column], paths[row][column - 1]);
            }
        }

        return paths[rows - 1][columns - 1];
    }
}