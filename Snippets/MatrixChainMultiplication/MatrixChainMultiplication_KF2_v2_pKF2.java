package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

public final class MatrixChainMultiplication {

    private MatrixChainMultiplication() {
        // Prevent instantiation
    }

    /**
     * m[i][j] = minimum number of scalar multiplications needed
     * to compute the product A_i...A_j.
     */
    private static int[][] m;

    /**
     * s[i][j] = index k at which the optimal split occurs
     * for computing A_i...A_j.
     */
    private static int[][] s;

    /**
     * Dimensions array where matrix A_i has dimensions p[i - 1] x p[i].
     */
    private static int[] p;

    /**
     * Computes the optimal parenthesization of a chain of matrices.
     *
     * @param matrices list of matrices in the chain
     * @return a Result object containing the cost table (m) and split table (s)
     */
    public static Result calculateMatrixChainOrder(ArrayList<Matrix> matrices) {
        int size = matrices.size();
        m = new int[size + 1][size + 1];
        s = new int[size + 1][size + 1];
        p = new int[size + 1];

        // Initialize DP tables with sentinel values
        for (int i = 0; i <= size; i++) {
            Arrays.fill(m[i], -1);
            Arrays.fill(s[i], -1);
        }

        // Build dimensions array p so that matrix i has dimensions p[i - 1] x p[i]
        for (int i = 0; i < p.length; i++) {
            p[i] = (i == 0) ? matrices.get(i).col() : matrices.get(i - 1).row();
        }

        matrixChainOrder(size);
        return new Result(m, s);
    }

    /**
     * Fills the DP tables m and s using bottom-up matrix chain multiplication.
     *
     * @param size number of matrices in the chain
     */
    private static void matrixChainOrder(int size) {
        // Base case: cost is zero when multiplying one matrix
        for (int i = 1; i <= size; i++) {
            m[i][i] = 0;
        }

        // l = current chain length
        for (int l = 2; l <= size; l++) {
            for (int i = 1; i <= size - l + 1; i++) {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;

                // Try all possible split points k
                for (int k = i; k < j; k++) {
                    int cost =
                        m[i][k]
                            + m[k + 1][j]
                            + p[i - 1] * p[k] * p[j];

                    if (cost < m[i][j]) {
                        m[i][j] = cost;
                        s[i][j] = k;
                    }
                }
            }
        }
    }

    public static class Result {
        private final int[][] m;
        private final int[][] s;

        public Result(int[][] m, int[][] s) {
            this.m = m;
            this.s = s;
        }

        public int[][] getM() {
            return m;
        }

        public int[][] getS() {
            return s;
        }
    }

    public static class Matrix {
        private final int count;
        private final int col;
        private final int row;

        public Matrix(int count, int col, int row) {
            this.count = count;
            this.col = col;
            this.row = row;
        }

        public int count() {
            return count;
        }

        public int col() {
            return col;
        }

        public int row() {
            return row;
        }
    }
}