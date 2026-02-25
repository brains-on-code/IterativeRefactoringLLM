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
    private static int[][] minMultiplicationCostTable;
    // DP table for optimal split positions
    private static int[][] optimalSplitTable;
    // Dimensions array
    private static int[] matrixDimensions;

    /**
     * Computes the optimal matrix chain multiplication order.
     *
     * @param matrixDimensionsList list of matrix dimension descriptors
     * @return result containing cost and split tables
     */
    public static Result computeOptimalOrder(ArrayList<MatrixDimension> matrixDimensionsList) {
        int matrixCount = matrixDimensionsList.size();
        minMultiplicationCostTable = new int[matrixCount + 1][matrixCount + 1];
        optimalSplitTable = new int[matrixCount + 1][matrixCount + 1];
        matrixDimensions = new int[matrixCount + 1];

        for (int i = 0; i < matrixCount + 1; i++) {
            Arrays.fill(minMultiplicationCostTable[i], -1);
            Arrays.fill(optimalSplitTable[i], -1);
        }

        for (int i = 0; i < matrixDimensions.length; i++) {
            matrixDimensions[i] =
                (i == 0) ? matrixDimensionsList.get(i).getRowCount() : matrixDimensionsList.get(i - 1).getColumnCount();
        }

        fillDynamicProgrammingTables(matrixCount);
        return new Result(minMultiplicationCostTable, optimalSplitTable);
    }

    /**
     * Fills DP tables for matrix chain multiplication.
     */
    private static void fillDynamicProgrammingTables(int matrixCount) {
        for (int i = 1; i < matrixCount + 1; i++) {
            minMultiplicationCostTable[i][i] = 0;
        }

        for (int chainLength = 2; chainLength < matrixCount + 1; chainLength++) {
            for (int startIndex = 1; startIndex < matrixCount - chainLength + 2; startIndex++) {
                int endIndex = startIndex + chainLength - 1;
                minMultiplicationCostTable[startIndex][endIndex] = Integer.MAX_VALUE;

                for (int splitPosition = startIndex; splitPosition < endIndex; splitPosition++) {
                    int multiplicationCost =
                        minMultiplicationCostTable[startIndex][splitPosition]
                            + minMultiplicationCostTable[splitPosition + 1][endIndex]
                            + matrixDimensions[startIndex - 1]
                                * matrixDimensions[splitPosition]
                                * matrixDimensions[endIndex];

                    if (multiplicationCost < minMultiplicationCostTable[startIndex][endIndex]) {
                        minMultiplicationCostTable[startIndex][endIndex] = multiplicationCost;
                        optimalSplitTable[startIndex][endIndex] = splitPosition;
                    }
                }
            }
        }
    }

    /**
     * Holds the DP results for matrix chain multiplication.
     */
    public static class Result {
        private final int[][] minMultiplicationCostTable;
        private final int[][] optimalSplitTable;

        public Result(int[][] minMultiplicationCostTable, int[][] optimalSplitTable) {
            this.minMultiplicationCostTable = minMultiplicationCostTable;
            this.optimalSplitTable = optimalSplitTable;
        }

        public int[][] getMinMultiplicationCostTable() {
            return minMultiplicationCostTable;
        }

        public int[][] getOptimalSplitTable() {
            return optimalSplitTable;
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