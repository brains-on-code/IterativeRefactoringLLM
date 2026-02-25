package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class GridUniquePaths {

    private GridUniquePaths() {
    }

    public static int countUniquePathsOptimized(final int rows, final int cols) {
        if (rows > cols) {
            return countUniquePathsOptimized(cols, rows);
        }
        int[] dp = new int[cols];
        Arrays.fill(dp, 1);
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                dp[col] = Math.addExact(dp[col], dp[col - 1]);
            }
        }
        return dp[cols - 1];
    }

    public static int countUniquePaths(final int rows, final int cols) {
        int[][] dp = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            dp[row][0] = 1;
        }
        for (int col = 0; col < cols; col++) {
            dp[0][col] = 1;
        }
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                dp[row][col] = Math.addExact(dp[row - 1][col], dp[row][col - 1]);
            }
        }
        return dp[rows - 1][cols - 1];
    }
}