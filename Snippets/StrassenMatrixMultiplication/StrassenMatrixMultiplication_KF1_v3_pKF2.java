package com.thealgorithms.divideandconquer;

/**
 * Strassen's algorithm for multiplying two square matrices.
 * <p>
 * Assumes matrices are n × n where n is a power of 2.
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

        int half = n / 2;

        int[][] a11 = new int[half][half];
        int[][] a12 = new int[half][half];
        int[][] a21 = new int[half][half];
        int[][] a22 = new int[half][half];

        int[][] b11 = new int[half][half];
        int[][] b12 = new int[half][half];
        int[][] b21 = new int[half][half];
        int[][] b22 = new int[half][half];

        copySubmatrix(a, a11, 0, 0);
        copySubmatrix(a, a12, 0, half);
        copySubmatrix(a, a21, half, 0);
        copySubmatrix(a, a22, half, half);

        copySubmatrix(b, b11, 0, 0);
        copySubmatrix(b, b12, 0, half);
        copySubmatrix(b, b21, half, 0);
        copySubmatrix(b, b22, half, half);

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
        writeSubmatrix(c12, result, 0, half);
        writeSubmatrix(c21, result, half, 0);
        writeSubmatrix(c22, result, half, half);

        return result;
    }

    /**
     * Returns a - b.
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
     * Returns a + b.
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
     * Copies a submatrix from {@code source} into {@code destination},
     * starting at ({@code rowOffset}, {@code colOffset}) in {@code source}.
     */
    public void copySubmatrix(int[][] source, int[][] destination, int rowOffset, int colOffset) {
        for (int i = 0, srcRow = rowOffset; i < destination.length; i++, srcRow++) {
            for (int j = 0, srcCol = colOffset; j < destination.length; j++, srcCol++) {
                destination[i][j] = source[srcRow][srcCol];
            }
        }
    }

    /**
     * Writes {@code source} into {@code target},
     * starting at ({@code rowOffset}, {@code colOffset}) in {@code target}.
     */
    public void writeSubmatrix(int[][] source, int[][] target, int rowOffset, int colOffset) {
        for (int i = 0, tgtRow = rowOffset; i < source.length; i++, tgtRow++) {
            for (int j = 0, tgtCol = colOffset; j < source.length; j++, tgtCol++) {
                target[tgtRow][tgtCol] = source[i][j];
            }
        }
    }
}