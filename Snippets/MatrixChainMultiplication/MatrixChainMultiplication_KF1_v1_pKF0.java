package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Matrix Chain Multiplication dynamic programming implementation.
 */
public final class MatrixChainMultiplication {

    private MatrixChainMultiplication() {
    }

    /** DP table for minimal multiplication cost. */
    private static int[][] costTable;

    /** DP table for optimal split positions. */
    private static int[][] splitTable;

    /** Dimensions array. */
    private static int[] dimensions;

    /**
     * Computes the optimal matrix chain multiplication order.
     *
     * @param matrices list of matrices with their dimensions
     * @return result containing cost and split tables
     */
    public static Result computeOptimalOrder(ArrayList<MatrixDimension> matrices) {
        int n = matrices.size();
        costTable = new int[n + 1][n + 1];
        splitTable = new int[n + 1][n + 1];
        dimensions = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(costTable[i], -1);
            Arrays.fill(splitTable[i], -1);
        }

        for (int i = 0; i < dimensions.length; i++) {
            dimensions[i] = (i == 0)
                ? matrices.get(i).getRows()
                : matrices.get(i - 1).getCols();
        }

        fillDpTables(n);
        return new Result(costTable, splitTable);
    }

    /**
     * Fills the DP tables for matrix chain multiplication.
     *
     * @param n number of matrices
     */
    private static void fillDpTables(int n) {
        for (int i = 1; i <= n; i++) {
            costTable[i][i] = 0;
        }

        for (int chainLength = 2; chainLength <= n; chainLength++) {
            for (int i = 1; i <= n - chainLength + 1; i++) {
                int j = i + chainLength - 1;
                costTable[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int cost =
                        costTable[i][k]
                            + costTable[k + 1][j]
                            + dimensions[i - 1] * dimensions[k] * dimensions[j];

                    if (cost < costTable[i][j]) {
                        costTable[i][j] = cost;
                        splitTable[i][j] = k;
                    }
                }
            }
        }
    }

    /**
     * Result of matrix chain multiplication DP:
     * minimal costs and optimal split positions.
     */
    public static class Result {
        private final int[][] costTable;
        private final int[][] splitTable;

        public Result(int[][] costTable, int[][] splitTable) {
            this.costTable = costTable;
            this.splitTable = splitTable;
        }

        public int[][] getCostTable() {
            return costTable;
        }

        public int[][] getSplitTable() {
            return splitTable;
        }
    }

    /**
     * Represents matrix dimensions (rows x cols).
     */
    public static class MatrixDimension {
        private final int rows;
        private final int cols;
        private final int unused; // kept for compatibility with original structure

        public MatrixDimension(int rows, int cols, int unused) {
            this.rows = rows;
            this.cols = cols;
            this.unused = unused;
        }

        public int getRows() {
            return rows;
        }

        public int getCols() {
            return cols;
        }

        public int getUnused() {
            return unused;
        }
    }
}