package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Computes the optimal parenthesization of a chain of matrices using
 * dynamic programming, minimizing the number of scalar multiplications.
 */
public final class MatrixChainMultiplication {

    private MatrixChainMultiplication() {
        // Prevent instantiation
    }

    /**
     * cost[i][j] = minimum scalar multiplications to compute A_i..A_j (1-based).
     */
    private static int[][] cost;

    /**
     * split[i][j] = index k that gives the optimal split of A_i..A_j
     * into (A_i..A_k) * (A_{k+1}..A_j).
     */
    private static int[][] split;

    /**
     * Dimensions array p of length n + 1 for n matrices:
     * matrix i has dimensions p[i - 1] x p[i].
     */
    private static int[] dimensions;

    /**
     * Calculates the optimal order for multiplying a given list of matrices.
     *
     * @param matrices list of matrices to be multiplied
     * @return a Result containing the cost and split tables
     */
    public static Result calculateMatrixChainOrder(ArrayList<Matrix> matrices) {
        int size = matrices.size();
        cost = new int[size + 1][size + 1];
        split = new int[size + 1][size + 1];
        dimensions = new int[size + 1];

        for (int i = 0; i <= size; i++) {
            Arrays.fill(cost[i], -1);
            Arrays.fill(split[i], -1);
        }

        // Build dimensions array: matrix i has dimensions p[i - 1] x p[i]
        for (int i = 0; i < dimensions.length; i++) {
            dimensions[i] = (i == 0)
                ? matrices.get(i).getColumns()
                : matrices.get(i - 1).getRows();
        }

        computeMatrixChainOrder(size);
        return new Result(cost, split);
    }

    /**
     * Fills the DP tables {@code cost} and {@code split} for a chain of
     * {@code size} matrices.
     *
     * @param size number of matrices in the chain
     */
    private static void computeMatrixChainOrder(int size) {
        // Single matrix: no multiplication needed
        for (int i = 1; i <= size; i++) {
            cost[i][i] = 0;
        }

        // l = current chain length
        for (int l = 2; l <= size; l++) {
            for (int i = 1; i <= size - l + 1; i++) {
                int j = i + l - 1;
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
     * Holds the DP results: minimum costs and optimal split indices.
     */
    public static class Result {
        private final int[][] cost;
        private final int[][] split;

        /**
         * @param cost  DP table of minimum multiplication costs
         * @param split DP table of optimal split indices
         */
        public Result(int[][] cost, int[][] split) {
            this.cost = cost;
            this.split = split;
        }

        /** @return DP table of minimum multiplication costs */
        public int[][] getCost() {
            return cost;
        }

        /** @return DP table of optimal split indices */
        public int[][] getSplit() {
            return split;
        }
    }

    /**
     * Represents a matrix with its identifier and dimensions.
     */
    public static class Matrix {
        private final int id;
        private final int columns;
        private final int rows;

        /**
         * @param id      identifier for the matrix
         * @param columns number of columns
         * @param rows    number of rows
         */
        public Matrix(int id, int columns, int rows) {
            this.id = id;
            this.columns = columns;
            this.rows = rows;
        }

        /** @return identifier of the matrix */
        public int getId() {
            return id;
        }

        /** @return number of columns */
        public int getColumns() {
            return columns;
        }

        /** @return number of rows */
        public int getRows() {
            return rows;
        }
    }
}