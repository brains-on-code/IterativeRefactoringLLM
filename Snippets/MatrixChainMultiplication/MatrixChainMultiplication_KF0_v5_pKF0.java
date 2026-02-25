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

    private MatrixChainMultiplication() {
        // Utility class; prevent instantiation
    }

    /**
     * Calculates the optimal order for multiplying a given list of matrices.
     *
     * @param matrices an ArrayList of Matrix objects representing the matrices
     *                 to be multiplied.
     * @return a Result object containing the matrices of minimum costs and
     *         optimal splits.
     */
    public static Result calculateMatrixChainOrder(ArrayList<Matrix> matrices) {
        int matrixCount = matrices.size();
        int[][] cost = new int[matrixCount + 1][matrixCount + 1];
        int[][] split = new int[matrixCount + 1][matrixCount + 1];
        int[] dimensions = buildDimensionsArray(matrices);

        initializeMatrices(cost, split);
        computeMatrixChainOrder(matrixCount, cost, split, dimensions);

        return new Result(cost, split);
    }

    private static int[] buildDimensionsArray(ArrayList<Matrix> matrices) {
        int size = matrices.size();
        int[] dimensions = new int[size + 1];

        // For a chain A1 (p0 x p1), A2 (p1 x p2), ..., An (p{n-1} x pn),
        // dimensions array is [p0, p1, ..., pn].
        for (int i = 0; i <= size; i++) {
            dimensions[i] = (i == 0)
                ? matrices.get(i).getColumns()
                : matrices.get(i - 1).getRows();
        }

        return dimensions;
    }

    private static void initializeMatrices(int[][] cost, int[][] split) {
        for (int i = 0; i < cost.length; i++) {
            Arrays.fill(cost[i], -1);
            Arrays.fill(split[i], -1);
        }
    }

    /**
     * Computes the minimum cost of multiplying the matrices using dynamic programming.
     *
     * @param size       the number of matrices in the multiplication sequence.
     * @param cost       the matrix to store minimum multiplication costs.
     * @param split      the matrix to store optimal split points.
     * @param dimensions the array of matrix dimensions.
     */
    private static void computeMatrixChainOrder(
        int size,
        int[][] cost,
        int[][] split,
        int[] dimensions
    ) {
        // Cost is zero when multiplying one matrix
        for (int i = 1; i <= size; i++) {
            cost[i][i] = 0;
        }

        // chainLength is the length of the matrix chain
        for (int chainLength = 2; chainLength <= size; chainLength++) {
            for (int i = 1; i <= size - chainLength + 1; i++) {
                int j = i + chainLength - 1;
                cost[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int currentCost =
                        cost[i][k]
                            + cost[k + 1][j]
                            + dimensions[i - 1] * dimensions[k] * dimensions[j];

                    if (currentCost < cost[i][j]) {
                        cost[i][j] = currentCost;
                        split[i][j] = k;
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
        private final int[][] cost;
        private final int[][] split;

        /**
         * Constructs a Result object with the specified matrices of minimum
         * costs and split points.
         *
         * @param cost  the matrix of minimum multiplication costs.
         * @param split the matrix of optimal split points.
         */
        public Result(int[][] cost, int[][] split) {
            this.cost = cost;
            this.split = split;
        }

        /**
         * Returns the matrix of minimum multiplication costs.
         *
         * @return the matrix of minimum multiplication costs.
         */
        public int[][] getCost() {
            return cost;
        }

        /**
         * Returns the matrix of optimal split points.
         *
         * @return the matrix of optimal split points.
         */
        public int[][] getSplit() {
            return split;
        }
    }

    /**
     * The Matrix class represents a matrix with its dimensions and identifier.
     */
    public static class Matrix {
        private final int id;
        private final int columns;
        private final int rows;

        /**
         * Constructs a Matrix object with the specified id, number of columns,
         * and number of rows.
         *
         * @param id      the identifier for the matrix.
         * @param columns the number of columns in the matrix.
         * @param rows    the number of rows in the matrix.
         */
        public Matrix(int id, int columns, int rows) {
            this.id = id;
            this.columns = columns;
            this.rows = rows;
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
        public int getColumns() {
            return columns;
        }

        /**
         * Returns the number of rows in the matrix.
         *
         * @return the number of rows in the matrix.
         */
        public int getRows() {
            return rows;
        }
    }
}