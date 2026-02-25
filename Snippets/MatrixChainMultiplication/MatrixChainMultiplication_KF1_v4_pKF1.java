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
    private static int[][] minimumCost;
    // DP table for optimal split positions
    private static int[][] optimalSplit;
    // Dimensions array
    private static int[] matrixDimensions;

    /**
     * Computes the optimal matrix chain multiplication order.
     *
     * @param matrices list of matrix dimension descriptors
     * @return result containing cost and split tables
     */
    public static Result computeOptimalOrder(ArrayList<MatrixDimension> matrices) {
        int matrixCount = matrices.size();
        minimumCost = new int[matrixCount + 1][matrixCount + 1];
        optimalSplit = new int[matrixCount + 1][matrixCount + 1];
        matrixDimensions = new int[matrixCount + 1];

        for (int row = 0; row <= matrixCount; row++) {
            Arrays.fill(minimumCost[row], -1);
            Arrays.fill(optimalSplit[row], -1);
        }

        for (int i = 0; i < matrixDimensions.length; i++) {
            matrixDimensions[i] =
                (i == 0) ? matrices.get(i).getRowCount() : matrices.get(i - 1).getColumnCount();
        }

        fillDynamicProgrammingTables(matrixCount);
        return new Result(minimumCost, optimalSplit);
    }

    /**
     * Fills DP tables for matrix chain multiplication.
     */
    private static void fillDynamicProgrammingTables(int matrixCount) {
        for (int i = 1; i <= matrixCount; i++) {
            minimumCost[i][i] = 0;
        }

        for (int chainLength = 2; chainLength <= matrixCount; chainLength++) {
            for (int startIndex = 1; startIndex <= matrixCount - chainLength + 1; startIndex++) {
                int endIndex = startIndex + chainLength - 1;
                minimumCost[startIndex][endIndex] = Integer.MAX_VALUE;

                for (int splitIndex = startIndex; splitIndex < endIndex; splitIndex++) {
                    int cost =
                        minimumCost[startIndex][splitIndex]
                            + minimumCost[splitIndex + 1][endIndex]
                            + matrixDimensions[startIndex - 1]
                                * matrixDimensions[splitIndex]
                                * matrixDimensions[endIndex];

                    if (cost < minimumCost[startIndex][endIndex]) {
                        minimumCost[startIndex][endIndex] = cost;
                        optimalSplit[startIndex][endIndex] = splitIndex;
                    }
                }
            }
        }
    }

    /**
     * Holds the DP results for matrix chain multiplication.
     */
    public static class Result {
        private final int[][] minimumCost;
        private final int[][] optimalSplit;

        public Result(int[][] minimumCost, int[][] optimalSplit) {
            this.minimumCost = minimumCost;
            this.optimalSplit = optimalSplit;
        }

        public int[][] getMinimumCost() {
            return minimumCost;
        }

        public int[][] getOptimalSplit() {
            return optimalSplit;
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