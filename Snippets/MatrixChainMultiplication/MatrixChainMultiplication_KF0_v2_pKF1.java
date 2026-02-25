package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The MatrixChainMultiplication class provides functionality to compute the
 * optimal way to multiply a sequence of matrices. The optimal multiplication
 * order is determined using dynamic programming, which minimizes the total
 * number of scalar multiplications required.
 */
public final class MatrixChainMultiplication {

    private MatrixChainMultiplication() {}

    // Tables to store minimum multiplication costs and split points
    private static int[][] minMultiplicationCost;
    private static int[][] optimalSplitIndex;
    private static int[] matrixDimensions;

    /**
     * Calculates the optimal order for multiplying a given list of matrices.
     *
     * @param matrices an ArrayList of Matrix objects representing the matrices
     *                 to be multiplied.
     * @return a Result object containing the matrices of minimum costs and
     *         optimal splits.
     */
    public static Result calculateMatrixChainOrder(ArrayList<Matrix> matrices) {
        int numberOfMatrices = matrices.size();
        minMultiplicationCost = new int[numberOfMatrices + 1][numberOfMatrices + 1];
        optimalSplitIndex = new int[numberOfMatrices + 1][numberOfMatrices + 1];
        matrixDimensions = new int[numberOfMatrices + 1];

        for (int i = 0; i <= numberOfMatrices; i++) {
            Arrays.fill(minMultiplicationCost[i], -1);
            Arrays.fill(optimalSplitIndex[i], -1);
        }

        for (int index = 0; index < matrixDimensions.length; index++) {
            matrixDimensions[index] =
                index == 0 ? matrices.get(index).getColumnCount() : matrices.get(index - 1).getRowCount();
        }

        computeMatrixChainOrder(numberOfMatrices);
        return new Result(minMultiplicationCost, optimalSplitIndex);
    }

    /**
     * A helper method that computes the minimum cost of multiplying
     * the matrices using dynamic programming.
     *
     * @param numberOfMatrices the number of matrices in the multiplication sequence.
     */
    private static void computeMatrixChainOrder(int numberOfMatrices) {
        for (int matrixIndex = 1; matrixIndex <= numberOfMatrices; matrixIndex++) {
            minMultiplicationCost[matrixIndex][matrixIndex] = 0;
        }

        for (int chainLength = 2; chainLength <= numberOfMatrices; chainLength++) {
            for (int startMatrixIndex = 1; startMatrixIndex <= numberOfMatrices - chainLength + 1; startMatrixIndex++) {
                int endMatrixIndex = startMatrixIndex + chainLength - 1;
                minMultiplicationCost[startMatrixIndex][endMatrixIndex] = Integer.MAX_VALUE;

                for (int splitIndex = startMatrixIndex; splitIndex < endMatrixIndex; splitIndex++) {
                    int currentCost =
                        minMultiplicationCost[startMatrixIndex][splitIndex]
                            + minMultiplicationCost[splitIndex + 1][endMatrixIndex]
                            + matrixDimensions[startMatrixIndex - 1]
                                * matrixDimensions[splitIndex]
                                * matrixDimensions[endMatrixIndex];

                    if (currentCost < minMultiplicationCost[startMatrixIndex][endMatrixIndex]) {
                        minMultiplicationCost[startMatrixIndex][endMatrixIndex] = currentCost;
                        optimalSplitIndex[startMatrixIndex][endMatrixIndex] = splitIndex;
                    }
                }
            }
        }
    }

    /**
     * The Result class holds the results of the matrix chain multiplication
     * calculation, including the matrix of minimum costs and split points.
     */
    public static class Result {
        private final int[][] minCostTable;
        private final int[][] splitTable;

        /**
         * Constructs a Result object with the specified matrices of minimum
         * costs and split points.
         *
         * @param minCostTable the matrix of minimum multiplication costs.
         * @param splitTable   the matrix of optimal split points.
         */
        public Result(int[][] minCostTable, int[][] splitTable) {
            this.minCostTable = minCostTable;
            this.splitTable = splitTable;
        }

        /**
         * Returns the matrix of minimum multiplication costs.
         *
         * @return the matrix of minimum multiplication costs.
         */
        public int[][] getMinCostTable() {
            return minCostTable;
        }

        /**
         * Returns the matrix of optimal split points.
         *
         * @return the matrix of optimal split points.
         */
        public int[][] getSplitTable() {
            return splitTable;
        }
    }

    /**
     * The Matrix class represents a matrix with its dimensions and identifier.
     */
    public static class Matrix {
        private final int id;
        private final int columnCount;
        private final int rowCount;

        /**
         * Constructs a Matrix object with the specified identifier, number of columns,
         * and number of rows.
         *
         * @param id          the identifier for the matrix.
         * @param columnCount the number of columns in the matrix.
         * @param rowCount    the number of rows in the matrix.
         */
        public Matrix(int id, int columnCount, int rowCount) {
            this.id = id;
            this.columnCount = columnCount;
            this.rowCount = rowCount;
        }

        /**
         * Returns the identifier of the matrix.
         *
         * @return the identifier of the matrix.
         */
        public int getId() {
            return id;
        }

        /**
         * Returns the number of columns in the matrix.
         *
         * @return the number of columns in the matrix.
         */
        public int getColumnCount() {
            return columnCount;
        }

        /**
         * Returns the number of rows in the matrix.
         *
         * @return the number of rows in the matrix.
         */
        public int getRowCount() {
            return rowCount;
        }
    }
}