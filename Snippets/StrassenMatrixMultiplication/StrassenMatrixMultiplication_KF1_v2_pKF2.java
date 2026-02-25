package com.thealgorithms.divideandconquer;

/**
 * Implementation of Strassen's algorithm for matrix multiplication.
 * <p>
 * Assumes square matrices of size n x n where n is a power of 2.
 */
public class StrassenMatrixMultiplier {

    /**
     * Multiplies two square matrices using Strassen's algorithm.
     *
     * @param a first matrix
     * @param b second matrix
     * @return product matrix a × b
     */
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

        copySubmatrix(a, a11, 0, 0);
        copySubmatrix(a, a12, 0, newSize);
        copySubmatrix(a, a21, newSize, 0);
        copySubmatrix(a, a22, newSize, newSize);

        copySubmatrix(b, b11, 0, 0);
        copySubmatrix(b, b12, 0, newSize);
        copySubmatrix(b, b21, newSize, 0);
        copySubmatrix(b, b22, newSize, newSize);

        int[][] p1 = multiply(add(a11, a22), add(b11, b22));
        int[][] p2 = multiply(add(a21, a22), b11);
        int[][] p3 = multiply(a11, subtract(b12, b22));
        int[][] p4 = multiply(a22, subtract(b21, b11));
        int[][] p5 = multiply(add(a11, a12), b22);
        int[][] p6 = multiply(subtract(a21, a11), add(b11, b12));
        int[][] p7 = multiply(subtract(a12, a22), add(b21, b22));

        int[][] c11 = add(subtract(add(p1, p4), p5), p7);
        int[][] c12 = add(p3, p5);
        int[][] c21 = add(p2, p4);
        int[][] c22 = add(subtract(add(p1, p3), p2), p6);

        writeSubmatrix(c11, result, 0, 0);
        writeSubmatrix(c12, result, 0, newSize);
        writeSubmatrix(c21, result, newSize, 0);
        writeSubmatrix(c22, result, newSize, newSize);

        return result;
    }

    /**
     * Subtracts matrix b from matrix a (a - b).
     *
     * @param a first matrix
     * @param b second matrix
     * @return matrix a - b
     */
    public int[][] subtract(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }

        return result;
    }

    /**
     * Adds two matrices (a + b).
     *
     * @param a first matrix
     * @param b second matrix
     * @return matrix a + b
     */
    public int[][] add(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }

        return result;
    }

    /**
     * Copies a submatrix from source into destination.
     *
     * @param source      source matrix
     * @param destination destination matrix (submatrix)
     * @param rowOffset   starting row in source
     * @param colOffset   starting column in source
     */
    public void copySubmatrix(int[][] source, int[][] destination, int rowOffset, int colOffset) {
        for (int i = 0, srcRow = rowOffset; i < destination.length; i++, srcRow++) {
            for (int j = 0, srcCol = colOffset; j < destination.length; j++, srcCol++) {
                destination[i][j] = source[srcRow][srcCol];
            }
        }
    }

    /**
     * Writes a submatrix into the target matrix.
     *
     * @param source    source submatrix
     * @param target    target matrix
     * @param rowOffset starting row in target
     * @param colOffset starting column in target
     */
    public void writeSubmatrix(int[][] source, int[][] target, int rowOffset, int colOffset) {
        for (int i = 0, tgtRow = rowOffset; i < source.length; i++, tgtRow++) {
            for (int j = 0, tgtCol = colOffset; j < source.length; j++, tgtCol++) {
                target[tgtRow][tgtCol] = source[i][j];
            }
        }
    }
}