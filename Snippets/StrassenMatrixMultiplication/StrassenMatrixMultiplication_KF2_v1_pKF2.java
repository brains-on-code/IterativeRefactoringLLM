package com.thealgorithms.divideandconquer;

public class StrassenMatrixMultiplication {

    public int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        if (n == 1) {
            result[0][0] = a[0][0] * b[0][0];
            return result;
        }

        int newSize = n / 2;

        int[][] a11 = new int[newSize][newSize];
        int[][] a12 = new int[newSize][newSize];
        int[][] a21 = new int[newSize][newSize];
        int[][] a22 = new int[newSize][newSize];

        int[][] b11 = new int[newSize][newSize];
        int[][] b12 = new int[newSize][newSize];
        int[][] b21 = new int[newSize][newSize];
        int[][] b22 = new int[newSize][newSize];

        split(a, a11, 0, 0);
        split(a, a12, 0, newSize);
        split(a, a21, newSize, 0);
        split(a, a22, newSize, newSize);

        split(b, b11, 0, 0);
        split(b, b12, 0, newSize);
        split(b, b21, newSize, 0);
        split(b, b22, newSize, newSize);

        int[][] m1 = multiply(add(a11, a22), add(b11, b22));
        int[][] m2 = multiply(add(a21, a22), b11);
        int[][] m3 = multiply(a11, sub(b12, b22));
        int[][] m4 = multiply(a22, sub(b21, b11));
        int[][] m5 = multiply(add(a11, a12), b22);
        int[][] m6 = multiply(sub(a21, a11), add(b11, b12));
        int[][] m7 = multiply(sub(a12, a22), add(b21, b22));

        int[][] c11 = add(sub(add(m1, m4), m5), m7);
        int[][] c12 = add(m3, m5);
        int[][] c21 = add(m2, m4);
        int[][] c22 = add(sub(add(m1, m3), m2), m6);

        join(c11, result, 0, 0);
        join(c12, result, 0, newSize);
        join(c21, result, newSize, 0);
        join(c22, result, newSize, newSize);

        return result;
    }

    public int[][] sub(int[][] a, int[][] b) {
        int n = a.length;
        int[][] diff = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                diff[i][j] = a[i][j] - b[i][j];
            }
        }

        return diff;
    }

    public int[][] add(int[][] a, int[][] b) {
        int n = a.length;
        int[][] sum = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sum[i][j] = a[i][j] + b[i][j];
            }
        }

        return sum;
    }

    public void split(int[][] parent, int[][] child, int rowOffset, int colOffset) {
        for (int i = 0; i < child.length; i++) {
            for (int j = 0; j < child.length; j++) {
                child[i][j] = parent[i + rowOffset][j + colOffset];
            }
        }
    }

    public void join(int[][] child, int[][] parent, int rowOffset, int colOffset) {
        for (int i = 0; i < child.length; i++) {
            for (int j = 0; j < child.length; j++) {
                parent[i + rowOffset][j + colOffset] = child[i][j];
            }
        }
    }
}