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

        // Ensure we always use the smaller dimension for the DP array
        if (m > n) {
            return uniquePaths(n, m);
        }

        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[col] = Math.addExact(dp[col], dp[col - 1]);
            }
        }

        return dp[n - 1];
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

        initializeFirstRow(dp, m);
        initializeFirstColumn(dp, n);

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

    private static void initializeFirstRow(final int[][] dp, final int m) {
        for (int row = 0; row < m; row++) {
            dp[row][0] = 1;
        }
    }

    private static void initializeFirstColumn(final int[][] dp, final int n) {
        for (int col = 0; col < n; col++) {
            dp[0][col] = 1;
        }
    }
}