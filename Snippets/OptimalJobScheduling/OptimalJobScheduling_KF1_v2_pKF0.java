package com.thealgorithms.dynamicprogramming;

public class Class1 {

    private final int rows;
    private final int cols;
    private final int[][] baseValues;
    private final int[][] transitionCosts;
    private final int[][] dp;

    public Class1(int rows, int cols, int[][] baseValues, int[][] transitionCosts) {
        this.rows = rows;
        this.cols = cols;
        this.baseValues = baseValues;
        this.transitionCosts = transitionCosts;
        this.dp = new int[rows][cols];
    }

    public void compute() {
        fillDpTable();
        printDpTable();
    }

    private void fillDpTable() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                dp[row][col] = computeCellValue(row, col);
            }
        }
    }

    private int computeCellValue(int row, int col) {
        if (row == 0) {
            return baseValues[row][col];
        }

        int minValue = Integer.MAX_VALUE;
        int baseValue = baseValues[row][col];

        for (int prevCol = 0; prevCol < cols; prevCol++) {
            int candidate =
                dp[row - 1][prevCol]
                    + transitionCosts[prevCol][col]
                    + baseValue;
            if (candidate < minValue) {
                minValue = candidate;
            }
        }

        return minValue;
    }

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

    public int getValue(int row, int col) {
        return dp[row][col];
    }
}