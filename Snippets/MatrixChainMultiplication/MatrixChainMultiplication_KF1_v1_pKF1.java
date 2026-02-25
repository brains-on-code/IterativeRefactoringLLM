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
    private static int[][] minCost;
    // DP table for optimal split positions
    private static int[][] splitIndex;
    // Dimensions array
    private static int[] dimensions;

    /**
     * Computes the optimal matrix chain multiplication order.
     *
     * @param matrices list of matrix dimension descriptors
     * @return result containing cost and split tables
     */
    public static Result computeOptimalOrder(ArrayList<MatrixDimension> matrices) {
        int matrixCount = matrices.size();
        minCost = new int[matrixCount + 1][matrixCount + 1];
        splitIndex = new int[matrixCount + 1][matrixCount + 1];
        dimensions = new int[matrixCount + 1];

        for (int i = 0; i < matrixCount + 1; i++) {
            Arrays.fill(minCost[i], -1);
            Arrays.fill(splitIndex[i], -1);
        }

        for (int i = 0; i < dimensions.length; i++) {
            dimensions[i] = (i == 0) ? matrices.get(i).getRows() : matrices.get(i - 1).getCols();
        }

        fillDpTables(matrixCount);
        return new Result(minCost, splitIndex);
    }

    /**
     * Fills DP tables for matrix chain multiplication.
     */
    private static void fillDpTables(int matrixCount) {
        for (int i = 1; i < matrixCount + 1; i++) {
            minCost[i][i] = 0;
        }

        for (int chainLength = 2; chainLength < matrixCount + 1; chainLength++) {
            for (int start = 1; start < matrixCount - chainLength + 2; start++) {
                int end = start + chainLength - 1;
                minCost[start][end] = Integer.MAX_VALUE;

                for (int k = start; k < end; k++) {
                    int cost =
                        minCost[start][k]
                            + minCost[k + 1][end]
                            + dimensions[start - 1] * dimensions[k] * dimensions[end];

                    if (cost < minCost[start][end]) {
                        minCost[start][end] = cost;
                        splitIndex[start][end] = k;
                    }
                }
            }
        }
    }

    /**
     * Holds the DP results for matrix chain multiplication.
     */
    public static class Result {
        private final int[][] minCost;
        private final int[][] splitIndex;

        public Result(int[][] minCost, int[][] splitIndex) {
            this.minCost = minCost;
            this.splitIndex = splitIndex;
        }

        public int[][] getMinCostTable() {
            return minCost;
        }

        public int[][] getSplitIndexTable() {
            return splitIndex;
        }
    }

    /**
     * Represents the dimensions of a matrix.
     */
    public static class MatrixDimension {
        private final int index;
        private final int rows;
        private final int cols;

        public MatrixDimension(int index, int rows, int cols) {
            this.index = index;
            this.rows = rows;
            this.cols = cols;
        }

        public int getIndex() {
            return index;
        }

        public int getRows() {
            return rows;
        }

        public int getCols() {
            return cols;
        }
    }
}