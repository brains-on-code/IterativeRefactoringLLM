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
        int[][] m3 = multiply(a11, subtract(b12, b22));
        int[][] m4 = multiply(a22, subtract(b21, b11));
        int[][] m5 = multiply(add(a11, a12), b22);
        int[][] m6 = multiply(subtract(a21, a11), add(b11, b12));
        int[][] m7 = multiply(subtract(a12, a22), add(b21, b22));

        int[][] c11 = add(subtract(add(m1, m4), m5), m7);
        int[][] c12 = add(m3, m5);
        int[][] c21 = add(m2, m4);
        int[][] c22 = add(subtract(add(m1, m3), m2), m6);

        join(c11, result, 0, 0);
        join(c12, result, 0, newSize);
        join(c21, result, newSize, 0);
        join(c22, result, newSize, newSize);

        return result;
    }

    public int[][] subtract(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                result[row][col] = a[row][col] - b[row][col];
            }
        }

        return result;
    }

    public int[][] add(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                result[row][col] = a[row][col] + b[row][col];
            }
        }

        return result;
    }

    public void split(int[][] parent, int[][] child, int rowOffset, int colOffset) {
        for (int rowChild = 0, rowParent = rowOffset; rowChild < child.length; rowChild++, rowParent++) {
            for (int colChild = 0, colParent = colOffset; colChild < child.length; colChild++, colParent++) {
                child[rowChild][colChild] = parent[rowParent][colParent];
            }
        }
    }

    public void join(int[][] child, int[][] parent, int rowOffset, int colOffset) {
        for (int rowChild = 0, rowParent = rowOffset; rowChild < child.length; rowChild++, rowParent++) {
            for (int colChild = 0, colParent = colOffset; colChild < child.length; colChild++, colParent++) {
                parent[rowParent][colParent] = child[rowChild][colChild];
            }
        }
    }
}