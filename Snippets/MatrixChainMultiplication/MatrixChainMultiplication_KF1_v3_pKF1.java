package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Matrix Chain Multiplication utility.
 */
public final class MatrixChainMultiplication {

    private MatrixChainMultiplication() {
    }

    // DP table for minimum multiplication cost
    private static int[][] minCostTable;
    // DP table for optimal split positions
    private static int[][] splitTable;
    // Dimensions array
    private static int[] dimensions;

    /**
     * Computes the optimal matrix chain multiplication order.
     *
     * @param matrixDimensions list of matrix dimension descriptors
     * @return result containing cost and split tables
     */
    public static Result computeOptimalOrder(ArrayList<MatrixDimension> matrixDimensions) {
        int matrixCount = matrixDimensions.size();
        minCostTable = new int[matrixCount + 1][matrixCount + 1];
        splitTable = new int[matrixCount + 1][matrixCount + 1];
        dimensions = new int[matrixCount + 1];

        for (int i = 0; i <= matrixCount; i++) {
            Arrays.fill(minCostTable[i], -1);
            Arrays.fill(splitTable[i], -1);
        }

        for (int i = 0; i < dimensions.length; i++) {
            dimensions[i] =
                (i == 0) ? matrixDimensions.get(i).getRowCount() : matrixDimensions.get(i - 1).getColumnCount();
        }

        fillDynamicProgrammingTables(matrixCount);
        return new Result(minCostTable, splitTable);
    }

    /**
     * Fills DP tables for matrix chain multiplication.
     */
    private static void fillDynamicProgrammingTables(int matrixCount) {
        for (int i = 1; i <= matrixCount; i++) {
            minCostTable[i][i] = 0;
        }

        for (int chainLength = 2; chainLength <= matrixCount; chainLength++) {
            for (int start = 1; start <= matrixCount - chainLength + 1; start++) {
                int end = start + chainLength - 1;
                minCostTable[start][end] = Integer.MAX_VALUE;

                for (int split = start; split < end; split++) {
                    int cost =
                        minCostTable[start][split]
                            + minCostTable[split + 1][end]
                            + dimensions[start - 1]
                                * dimensions[split]
                                * dimensions[end];

                    if (cost < minCostTable[start][end]) {
                        minCostTable[start][end] = cost;
                        splitTable[start][end] = split;
                    }
                }
            }
        }
    }

    /**
     * Holds the DP results for matrix chain multiplication.
     */
    public static class Result {
        private final int[][] minCostTable;
        private final int[][] splitTable;

        public Result(int[][] minCostTable, int[][] splitTable) {
            this.minCostTable = minCostTable;
            this.splitTable = splitTable;
        }

        public int[][] getMinCostTable() {
            return minCostTable;
        }

        public int[][] getSplitTable() {
            return splitTable;
        }
    }

    /**
     * Represents the dimensions of a matrix.
     */
    public static class MatrixDimension {
        private final int index;
        private final int rowCount;
        private final int columnCount;

        public MatrixDimension(int index, int rowCount, int columnCount) {
            this.index = index;
            this.rowCount = rowCount;
            this.columnCount = columnCount;
        }

        public int getIndex() {
            return index;
        }

        public int getRowCount() {
            return rowCount;
        }

        public int getColumnCount() {
            return columnCount;
        }
    }
}