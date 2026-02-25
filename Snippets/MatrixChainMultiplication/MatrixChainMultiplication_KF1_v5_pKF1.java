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
    private static int[][] minMultiplicationCost;
    // DP table for optimal split positions
    private static int[][] optimalSplitIndex;
    // Dimensions array
    private static int[] dimensionSizes;

    /**
     * Computes the optimal matrix chain multiplication order.
     *
     * @param matrixDimensions list of matrix dimension descriptors
     * @return result containing cost and split tables
     */
    public static Result computeOptimalOrder(ArrayList<MatrixDimension> matrixDimensions) {
        int matrixCount = matrixDimensions.size();
        minMultiplicationCost = new int[matrixCount + 1][matrixCount + 1];
        optimalSplitIndex = new int[matrixCount + 1][matrixCount + 1];
        dimensionSizes = new int[matrixCount + 1];

        for (int row = 0; row <= matrixCount; row++) {
            Arrays.fill(minMultiplicationCost[row], -1);
            Arrays.fill(optimalSplitIndex[row], -1);
        }

        for (int i = 0; i < dimensionSizes.length; i++) {
            dimensionSizes[i] =
                (i == 0)
                    ? matrixDimensions.get(i).getRowCount()
                    : matrixDimensions.get(i - 1).getColumnCount();
        }

        fillDynamicProgrammingTables(matrixCount);
        return new Result(minMultiplicationCost, optimalSplitIndex);
    }

    /**
     * Fills DP tables for matrix chain multiplication.
     */
    private static void fillDynamicProgrammingTables(int matrixCount) {
        for (int i = 1; i <= matrixCount; i++) {
            minMultiplicationCost[i][i] = 0;
        }

        for (int chainLength = 2; chainLength <= matrixCount; chainLength++) {
            for (int startMatrixIndex = 1;
                    startMatrixIndex <= matrixCount - chainLength + 1;
                    startMatrixIndex++) {

                int endMatrixIndex = startMatrixIndex + chainLength - 1;
                minMultiplicationCost[startMatrixIndex][endMatrixIndex] = Integer.MAX_VALUE;

                for (int splitMatrixIndex = startMatrixIndex;
                        splitMatrixIndex < endMatrixIndex;
                        splitMatrixIndex++) {

                    int currentCost =
                        minMultiplicationCost[startMatrixIndex][splitMatrixIndex]
                            + minMultiplicationCost[splitMatrixIndex + 1][endMatrixIndex]
                            + dimensionSizes[startMatrixIndex - 1]
                                * dimensionSizes[splitMatrixIndex]
                                * dimensionSizes[endMatrixIndex];

                    if (currentCost < minMultiplicationCost[startMatrixIndex][endMatrixIndex]) {
                        minMultiplicationCost[startMatrixIndex][endMatrixIndex] = currentCost;
                        optimalSplitIndex[startMatrixIndex][endMatrixIndex] = splitMatrixIndex;
                    }
                }
            }
        }
    }

    /**
     * Holds the DP results for matrix chain multiplication.
     */
    public static class Result {
        private final int[][] minMultiplicationCost;
        private final int[][] optimalSplitIndex;

        public Result(int[][] minMultiplicationCost, int[][] optimalSplitIndex) {
            this.minMultiplicationCost = minMultiplicationCost;
            this.optimalSplitIndex = optimalSplitIndex;
        }

        public int[][] getMinMultiplicationCost() {
            return minMultiplicationCost;
        }

        public int[][] getOptimalSplitIndex() {
            return optimalSplitIndex;
        }
    }

    /**
     * Represents the dimensions of a matrix.
     */
    public static class MatrixDimension {
        private final int matrixIndex;
        private final int rowCount;
        private final int columnCount;

        public MatrixDimension(int matrixIndex, int rowCount, int columnCount) {
            this.matrixIndex = matrixIndex;
            this.rowCount = rowCount;
            this.columnCount = columnCount;
        }

        public int getMatrixIndex() {
            return matrixIndex;
        }

        public int getRowCount() {
            return rowCount;
        }

        public int getColumnCount() {
            return columnCount;
        }
    }
}