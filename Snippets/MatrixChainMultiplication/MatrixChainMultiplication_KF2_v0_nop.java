package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;


public final class MatrixChainMultiplication {
    private MatrixChainMultiplication() {
    }

    private static int[][] m;
    private static int[][] s;
    private static int[] p;


    public static Result calculateMatrixChainOrder(ArrayList<Matrix> matrices) {
        int size = matrices.size();
        m = new int[size + 1][size + 1];
        s = new int[size + 1][size + 1];
        p = new int[size + 1];

        for (int i = 0; i < size + 1; i++) {
            Arrays.fill(m[i], -1);
            Arrays.fill(s[i], -1);
        }

        for (int i = 0; i < p.length; i++) {
            p[i] = i == 0 ? matrices.get(i).col() : matrices.get(i - 1).row();
        }

        matrixChainOrder(size);
        return new Result(m, s);
    }


    private static void matrixChainOrder(int size) {
        for (int i = 1; i < size + 1; i++) {
            m[i][i] = 0;
        }

        for (int l = 2; l < size + 1; l++) {
            for (int i = 1; i < size - l + 2; i++) {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < m[i][j]) {
                        m[i][j] = q;
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
