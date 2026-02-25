package com.thealgorithms.divideandconquer;

public class StrassenMatrixMultiplication {

    public int[][] multiply(int[][] a, int[][] b) {
        int size = a.length;
        int[][] result = new int[size][size];

        if (size == 1) {
            result[0][0] = a[0][0] * b[0][0];
            return result;
        }

        int halfSize = size / 2;

        int[][] a11 = new int[halfSize][halfSize];
        int[][] a12 = new int[halfSize][halfSize];
        int[][] a21 = new int[halfSize][halfSize];
        int[][] a22 = new int[halfSize][halfSize];

        int[][] b11 = new int[halfSize][halfSize];
        int[][] b12 = new int[halfSize][halfSize];
        int[][] b21 = new int[halfSize][halfSize];
        int[][] b22 = new int[halfSize][halfSize];

        split(a, a11, 0, 0);
        split(a, a12, 0, halfSize);
        split(a, a21, halfSize, 0);
        split(a, a22, halfSize, halfSize);

        split(b, b11, 0, 0);
        split(b, b12, 0, halfSize);
        split(b, b21, halfSize, 0);
        split(b, b22, halfSize, halfSize);

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
        join(c12, result, 0, halfSize);
        join(c21, result, halfSize, 0);
        join(c22, result, halfSize, halfSize);

        return result;
    }

    public int[][] subtract(int[][] a, int[][] b) {
        int size = a.length;
        int[][] diff = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                diff[row][col] = a[row][col] - b[row][col];
            }
        }

        return diff;
    }

    public int[][] add(int[][] a, int[][] b) {
        int size = a.length;
        int[][] sum = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                sum[row][col] = a[row][col] + b[row][col];
            }
        }

        return sum;
    }

    public void split(int[][] source, int[][] destination, int rowOffset, int colOffset) {
        int size = destination.length;
        for (int row = 0; row < size; row++) {
            System.arraycopy(source[rowOffset + row], colOffset, destination[row], 0, size);
        }
    }

    public void join(int[][] source, int[][] destination, int rowOffset, int colOffset) {
        int size = source.length;
        for (int row = 0; row < size; row++) {
            System.arraycopy(source[row], 0, destination[rowOffset + row], colOffset, size);
        }
    }
}