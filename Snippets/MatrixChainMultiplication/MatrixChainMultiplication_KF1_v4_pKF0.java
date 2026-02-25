package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Matrix Chain Multiplication dynamic programming implementation.
 */
public final class MatrixChainMultiplication {

    private MatrixChainMultiplication() {
        // Utility class
    }

    /**
     * Computes the optimal matrix chain multiplication order.
     *
     * @param matrices list of matrices with their dimensions
     * @return result containing cost and split tables
     */
    public static Result computeOptimalOrder(ArrayList<MatrixDimension> matrices) {
        int matrixCount = matrices.size();
        int[][] costTable = new int[matrixCount + 1][matrixCount + 1];
        int[][] splitTable = new int[matrixCount + 1][matrixCount + 1];
        int[] dimensions = buildDimensionsArray(matrices);

        initializeTables(costTable, splitTable);
        fillDpTables(matrixCount, costTable, splitTable, dimensions);

        return new Result(costTable, splitTable);
    }

    private static int[] buildDimensionsArray(ArrayList<MatrixDimension> matrices) {
        int matrixCount = matrices.size();
        int[] dimensions = new int[matrixCount + 1];

        for (int i = 0; i <= matrixCount; i++) {
            dimensions[i] = (i == 0)
                ? matrices.get(i).getRows()
                : matrices.get(i - 1).getCols();
        }

        return dimensions;
    }

    private static void initializeTables(int[][] costTable, int[][] splitTable) {
        for (int i = 0; i < costTable.length; i++) {
            Arrays.fill(costTable[i], -1);
            Arrays.fill(splitTable[i], -1);
        }
    }

    /**
     * Fills the DP tables for matrix chain multiplication.
     *
     * @param matrixCount number of matrices
     */
    private static void fillDpTables(
        int matrixCount,
        int[][] costTable,
        int[][] splitTable,
        int[] dimensions
    ) {
        for (int i = 1; i <= matrixCount; i++) {
            costTable[i][i] = 0;
        }

        for (int chainLength = 2; chainLength <= matrixCount; chainLength++) {
            for (int start = 1; start <= matrixCount - chainLength + 1; start++) {
                int end = start + chainLength - 1;
                costTable[start][end] = Integer.MAX_VALUE;

                for (int split = start; split < end; split++) {
                    int cost =
                        costTable[start][split]
                            + costTable[split + 1][end]
                            + dimensions[start - 1] * dimensions[split] * dimensions[end];

                    if (cost < costTable[start][end]) {
                        costTable[start][end] = cost;
                        splitTable[start][end] = split;
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