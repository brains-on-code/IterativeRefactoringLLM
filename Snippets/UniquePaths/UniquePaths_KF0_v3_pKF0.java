package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Author: Siddhant Swarup Mallick
 * Github: https://github.com/siddhant2002
 *
 * Problem Description:
 * A robot is located at the top-left corner of an m x n grid.
 * The robot can only move either down or right at any point in time.
 * The robot is trying to reach the bottom-right corner of the grid.
 * This class provides methods to calculate how many possible unique paths there are.
 */
public final class UniquePaths {

    private UniquePaths() {
        // Utility class; prevent instantiation
    }

    /**
     * Calculates the number of unique paths using a 1D dynamic programming array.
     * Time complexity: O(m * n)
     * Space complexity: O(min(m, n))
     *
     * @param m the number of rows in the grid
     * @param n the number of columns in the grid
     * @return the number of unique paths
     */
    public static int uniquePaths(final int m, final int n) {
        if (!isValidGrid(m, n)) {
            return 0;
        }

        int rows = m;
        int cols = n;

        // Use the smaller dimension for the DP array to optimize space
        if (rows > cols) {
            int temp = rows;
            rows = cols;
            cols = temp;
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

    /**
     * Calculates the number of unique paths using a 2D dynamic programming array.
     * Time complexity: O(m * n)
     * Space complexity: O(m * n)
     *
     * @param m the number of rows in the grid
     * @param n the number of columns in the grid
     * @return the number of unique paths
     */
    public static int uniquePaths2(final int m, final int n) {
        if (!isValidGrid(m, n)) {
            return 0;
        }

        int[][] dp = new int[m][n];

        initializeFirstColumn(dp, m);
        initializeFirstRow(dp, n);

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[row][col] = Math.addExact(dp[row - 1][col], dp[row][col - 1]);
            }
        }

        return dp[m - 1][n - 1];
    }

    private static boolean isValidGrid(final int m, final int n) {
        return m > 0 && n > 0;
    }

    private static void initializeFirstColumn(final int[][] dp, final int rows) {
        for (int row = 0; row < rows; row++) {
            dp[row][0] = 1;
        }
    }

    private static void initializeFirstRow(final int[][] dp, final int cols) {
        for (int col = 0; col < cols; col++) {
            dp[0][col] = 1;
        }
    }
}