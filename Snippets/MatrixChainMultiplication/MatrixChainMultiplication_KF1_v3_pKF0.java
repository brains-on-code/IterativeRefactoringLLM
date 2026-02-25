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

        for (int index = 0; index <= matrixCount; index++) {
            dimensions[index] = (index == 0)
                ? matrices.get(index).getRows()
                : matrices.get(index - 1).getCols();
        }

        return dimensions;
    }

    private static void initializeTables(int[][] costTable, int[][] splitTable) {
        for (int row = 0; row < costTable.length; row++) {
            Arrays.fill(costTable[row], -1);
            Arrays.fill(splitTable[row], -1);
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
        for (int index = 1; index <= matrixCount; index++) {
            costTable[index][index] = 0;
        }

        for (int chainLength = 2; chainLength <= matrixCount; chainLength++) {
            for (int startIndex = 1; startIndex <= matrixCount - chainLength + 1; startIndex++) {
                int endIndex = startIndex + chainLength - 1;
                costTable[startIndex][endIndex] = Integer.MAX_VALUE;

                for (int splitIndex = startIndex; splitIndex < endIndex; splitIndex++) {
                    int cost =
                        costTable[startIndex][splitIndex]
                            + costTable[splitIndex + 1][endIndex]
                            + dimensions[startIndex - 1] * dimensions[splitIndex] * dimensions[endIndex];

                    if (cost < costTable[startIndex][endIndex]) {
                        costTable[startIndex][endIndex] = cost;
                        splitTable[startIndex][endIndex] = splitIndex;
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