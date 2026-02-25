package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic programming example that computes a cost table based on:
 *  - a base cost matrix
 *  - a transition cost matrix
 *
 * The result is stored in {@code dp}, where each cell depends on the
 * previous row and the transition costs.
 */
public class Class1 {

    /** Number of rows. */
    private final int rows;

    /** Number of columns. */
    private final int cols;

    /** Base cost matrix. */
    private final int[][] baseCost;

    /** Transition cost matrix. */
    private final int[][] transitionCost;

    /** DP table storing computed minimal costs. */
    private final int[][] dp;

    /**
     * Constructs the DP helper.
     *
     * @param rows           number of rows
     * @param cols           number of columns
     * @param baseCost       base cost matrix (same dimensions as dp)
     * @param transitionCost transition cost matrix (cols x cols)
     */
    public Class1(int rows, int cols, int[][] baseCost, int[][] transitionCost) {
        this.rows = rows;
        this.cols = cols;
        this.baseCost = baseCost;
        this.transitionCost = transitionCost;
        this.dp = new int[rows][cols];
    }

    /**
     * Runs the full DP computation and prints the resulting table.
     */
    public void method1() {
        computeDpTable();
        printDpTable();
    }

    /**
     * Fills the DP table row by row.
     */
    private void computeDpTable() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                dp[row][col] = computeCell(row, col);
            }
        }
    }

    /**
     * Computes the DP value for a single cell (row, col).
     *
     * For the first row, the value is just the base cost.
     * For subsequent rows, the value is:
     *   baseCost[row][col] + min over k of (dp[row - 1][k] + transitionCost[k][col])
     *
     * @param row row index
     * @param col column index
     * @return computed DP value for (row, col)
     */
    private int computeCell(int row, int col) {
        if (row == 0) {
            return baseCost[row][col];
        } else {
            int[] candidates = new int[cols];
            for (int k = 0; k < cols; k++) {
                candidates[k] = dp[row - 1][k] + transitionCost[k][col] + baseCost[row][col];
            }
            return minInArray(candidates);
        }
    }

    /**
     * Returns the minimum value in the given array.
     *
     * @param values array of integers
     * @return minimum value in {@code values}
     */
    private int minInArray(int[] values) {
        int minIndex = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] < values[minIndex]) {
                minIndex = i;
            }
        }
        return values[minIndex];
    }

    /**
     * Prints the DP table to standard output.
     */
    private void printDpTable() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(dp[row][col]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Returns the computed DP value at (row, col).
     *
     * @param row row index
     * @param col column index
     * @return DP value at (row, col)
     */
    public int method6(int row, int col) {
        return dp[row][col];
    }
}