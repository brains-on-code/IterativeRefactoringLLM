package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Utility class for solving the Matrix Chain Multiplication problem
 * using dynamic programming.
 *
 * Given a chain of matrices, this class computes:
 * - The minimum number of scalar multiplications needed to multiply the chain.
 * - The optimal parenthesization (split points) of the matrix chain.
 */
public final class MatrixChainMultiplication {

    private MatrixChainMultiplication() {
        // Utility class; prevent instantiation.
    }

    /** DP table for minimal multiplication costs. */
    private static int[][] costTable;

    /** DP table for optimal split points. */
    private static int[][] splitTable;

    /** Dimensions array derived from the matrix chain. */
    private static int[] dimensions;

    /**
     * Computes the optimal multiplication order for a chain of matrices.
     *
     * @param matrices list of matrices with their dimensions
     * @return result object containing cost and split tables
     */
    public static Result computeOptimalOrder(ArrayList<MatrixDimension> matrices) {
        int n = matrices.size();
        costTable = new int[n + 1][n + 1];
        splitTable = new int[n + 1][n + 1];
        dimensions = new int[n + 1];

        // Initialize DP tables with sentinel values.
        for (int i = 0; i <= n; i++) {
            Arrays.fill(costTable[i], -1);
            Arrays.fill(splitTable[i], -1);
        }

        // Build the dimensions array:
        // For matrices A1 (p0 x p1), A2 (p1 x p2), ..., An (p{n-1} x pn),
        // dimensions = [p0, p1, ..., pn].
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
     * @param n number of matrices in the chain
     */
    private static void fillDpTables(int n) {
        // Cost of multiplying one matrix is zero.
        for (int i = 1; i <= n; i++) {
            costTable[i][i] = 0;
        }

        // chainLength is the length of the matrix chain being considered.
        for (int chainLength = 2; chainLength <= n; chainLength++) {
            for (int i = 1; i <= n - chainLength + 1; i++) {
                int j = i + chainLength - 1;
                costTable[i][j] = Integer.MAX_VALUE;

                // Try all possible split points k between i and j.
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
     * Result of the matrix chain multiplication DP:
     * - costTable[i][j]: minimal cost to multiply matrices i..j
     * - splitTable[i][j]: index k where the optimal split occurs for i..j
     */
    public static class Result {
        private final int[][] costTable;
        private final int[][] splitTable;

        public Result(int[][] costTable, int[][] splitTable) {
            this.costTable = costTable;
            this.splitTable = splitTable;
        }

        /** Returns the DP table of minimal multiplication costs. */
        public int[][] getCostTable() {
            return costTable;
        }

        /** Returns the DP table of optimal split points. */
        public int[][] getSplitTable() {
            return splitTable;
        }
    }

    /**
     * Represents the dimensions of a matrix.
     * A matrix has 'rows' x 'cols' dimensions.
     */
    public static class MatrixDimension {
        private final int rows;
        private final int cols;

        public MatrixDimension(int rows, int cols, int ignored) {
            this.rows = rows;
            this.cols = cols;
        }

        /** Returns the number of rows of the matrix. */
        public int getRows() {
            return rows;
        }

        /** Returns the number of columns of the matrix. */
        public int getCols() {
            return cols;
        }
    }
}