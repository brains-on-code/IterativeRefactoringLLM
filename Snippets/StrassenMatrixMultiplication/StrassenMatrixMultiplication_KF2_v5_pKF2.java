package com.thealgorithms.divideandconquer;

public class StrassenMatrixMultiplication {

    public int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        if (n == 1) {
            result[0][0] = a[0][0] * b[0][0];
            return result;
        }

        int half = n / 2;

        int[][] a11 = new int[half][half];
        int[][] a12 = new int[half][half];
        int[][] a21 = new int[half][half];
        int[][] a22 = new int[half][half];

        int[][] b11 = new int[half][half];
        int[][] b12 = new int[half][half];
        int[][] b21 = new int[half][half];
        int[][] b22 = new int[half][half];

        split(a, a11, 0, 0);
        split(a, a12, 0, half);
        split(a, a21, half, 0);
        split(a, a22, half, half);

        split(b, b11, 0, 0);
        split(b, b12, 0, half);
        split(b, b21, half, 0);
        split(b, b22, half, half);

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
        join(c12, result, 0, half);
        join(c21, result, half, 0);
        join(c22, result, half, half);

        return result;
    }

    public int[][] subtract(int[][] a, int[][] b) {
        int n = a.length;
        int[][] diff = new int[n][n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                diff[row][col] = a[row][col] - b[row][col];
            }
        }

        return diff;
    }

    public int[][] add(int[][] a, int[][] b) {
        int n = a.length;
        int[][] sum = new int[n][n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                sum[row][col] = a[row][col] + b[row][col];
            }
        }

        return sum;
    }

    public void split(int[][] source, int[][] target, int rowOffset, int colOffset) {
        int size = target.length;
        for (int row = 0; row < size; row++) {
            System.arraycopy(source[row + rowOffset], colOffset, target[row], 0, size);
        }
    }

    public void join(int[][] source, int[][] target, int rowOffset, int colOffset) {
        int size = source.length;
        for (int row = 0; row < size; row++) {
            System.arraycopy(source[row], 0, target[row + rowOffset], colOffset, size);
        }
    }
}