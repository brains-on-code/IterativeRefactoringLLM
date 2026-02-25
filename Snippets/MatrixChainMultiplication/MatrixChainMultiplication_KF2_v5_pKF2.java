package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

public final class MatrixChainMultiplication {

    private MatrixChainMultiplication() {}

    /**
     * m[i][j] = minimum scalar multiplications needed to compute A_i ... A_j
     */
    private static int[][] m;

    /**
     * s[i][j] = index k where the optimal split occurs for A_i ... A_j
     */
    private static int[][] s;

    /**
     * Matrix A_i has dimensions p[i - 1] x p[i]
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

        initializeTables(size);
        buildDimensionsArray(matrices);
        fillDpTables(size);

        return new Result(m, s);
    }

    /**
     * Initializes the DP tables m and s with sentinel values.
     *
     * @param size number of matrices in the chain
     */
    private static void initializeTables(int size) {
        for (int i = 0; i <= size; i++) {
            Arrays.fill(m[i], -1);
            Arrays.fill(s[i], -1);
        }
    }

    /**
     * Builds the dimensions array p so that matrix A_i has dimensions p[i - 1] x p[i].
     *
     * @param matrices list of matrices in the chain
     */
    private static void buildDimensionsArray(ArrayList<Matrix> matrices) {
        for (int i = 0; i < p.length; i++) {
            p[i] = (i == 0) ? matrices.get(i).col() : matrices.get(i - 1).row();
        }
    }

    /**
     * Fills the DP tables m and s using the bottom-up matrix chain multiplication algorithm.
     *
     * @param size number of matrices in the chain
     */
    private static void fillDpTables(int size) {
        for (int i = 1; i <= size; i++) {
            m[i][i] = 0;
        }

        for (int chainLength = 2; chainLength <= size; chainLength++) {
            for (int i = 1; i <= size - chainLength + 1; i++) {
                int j = i + chainLength - 1;
                m[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int cost = m[i][k]
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