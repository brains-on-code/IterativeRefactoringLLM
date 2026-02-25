package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic programming helper that computes a minimal-cost table based on:
 * <ul>
 *     <li>a base cost matrix</li>
 *     <li>a transition cost matrix</li>
 * </ul>
 *
 * The result is stored in {@code dp}, where each cell depends on the
 * previous row and the transition costs.
 */
public class Class1 {

    /** Number of rows in the DP table. */
    private final int rows;

    /** Number of columns in the DP table. */
    private final int cols;

    /** Base cost matrix (same dimensions as {@code dp}). */
    private final int[][] baseCost;

    /** Transition cost matrix (dimensions: {@code cols x cols}). */
    private final int[][] transitionCost;

    /** DP table storing computed minimal costs. */
    private final int[][] dp;

    /**
     * Creates a new DP helper.
     *
     * @param rows           number of rows in the DP table
     * @param cols           number of columns in the DP table
     * @param baseCost       base cost matrix (must be {@code rows x cols})
     * @param transitionCost transition cost matrix (must be {@code cols x cols})
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
     * Computes the DP value for a single cell {@code (row, col)}.
     *
     * <p>Recurrence:
     * <ul>
     *     <li>First row: {@code dp[0][col] = baseCost[0][col]}</li>
     *     <li>Row {@code r > 0}:
     *     {@code dp[r][c] = baseCost[r][c] + min over k (dp[r - 1][k] + transitionCost[k][c])}</li>
     * </ul>
     *
     * @param row row index
     * @param col column index
     * @return computed DP value for {@code (row, col)}
     */
    private int computeCell(int row, int col) {
        if (row == 0) {
            return baseCost[row][col];
        }

        int minValue = Integer.MAX_VALUE;
        int base = baseCost[row][col];

        for (int k = 0; k < cols; k++) {
            int candidate = dp[row - 1][k] + transitionCost[k][col] + base;
            if (candidate < minValue) {
                minValue = candidate;
            }
        }

        return minValue;
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
     * Returns the computed DP value at {@code (row, col)}.
     *
     * @param row row index
     * @param col column index
     * @return DP value at {@code (row, col)}
     */
    public int method6(int row, int col) {
        return dp[row][col];
    }
}