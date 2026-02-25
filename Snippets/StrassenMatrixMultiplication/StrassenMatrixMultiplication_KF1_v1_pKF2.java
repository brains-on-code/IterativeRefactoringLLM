package com.thealgorithms.divideandconquer;

/**
 * Implementation of Strassen's algorithm for matrix multiplication.
 * <p>
 * Assumes square matrices of size n x n where n is a power of 2.
 */
public class Class1 {

    /**
     * Multiplies two square matrices using Strassen's algorithm.
     *
     * @param a first matrix
     * @param b second matrix
     * @return product matrix a × b
     */
    public int[][] method1(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        if (n == 1) {
            result[0][0] = a[0][0] * b[0][0];
            return result;
        }

        int newSize = n / 2;

        // Submatrices of A
        int[][] a11 = new int[newSize][newSize];
        int[][] a12 = new int[newSize][newSize];
        int[][] a21 = new int[newSize][newSize];
        int[][] a22 = new int[newSize][newSize];

        // Submatrices of B
        int[][] b11 = new int[newSize][newSize];
        int[][] b12 = new int[newSize][newSize];
        int[][] b21 = new int[newSize][newSize];
        int[][] b22 = new int[newSize][newSize];

        // Split A into 4 submatrices
        method4(a, a11, 0, 0);
        method4(a, a12, 0, newSize);
        method4(a, a21, newSize, 0);
        method4(a, a22, newSize, newSize);

        // Split B into 4 submatrices
        method4(b, b11, 0, 0);
        method4(b, b12, 0, newSize);
        method4(b, b21, newSize, 0);
        method4(b, b22, newSize, newSize);

        // Strassen's 7 products:
        int[][] p1 = method1(method3(a11, a22), method3(b11, b22));      // p1 = (a11 + a22) * (b11 + b22)
        int[][] p2 = method1(method3(a21, a22), b11);                    // p2 = (a21 + a22) * b11
        int[][] p3 = method1(a11, method2(b12, b22));                    // p3 = a11 * (b12 - b22)
        int[][] p4 = method1(a22, method2(b21, b11));                    // p4 = a22 * (b21 - b11)
        int[][] p5 = method1(method3(a11, a12), b22);                    // p5 = (a11 + a12) * b22
        int[][] p6 = method1(method2(a21, a11), method3(b11, b12));      // p6 = (a21 - a11) * (b11 + b12)
        int[][] p7 = method1(method2(a12, a22), method3(b21, b22));      // p7 = (a12 - a22) * (b21 + b22)

        // Compute result submatrices:
        int[][] c11 = method3(method2(method3(p1, p4), p7), p5);         // c11 = p1 + p4 - p5 + p7
        int[][] c12 = method3(p3, p5);                                   // c12 = p3 + p5
        int[][] c21 = method3(p2, p4);                                   // c21 = p2 + p4
        int[][] c22 = method3(method2(method3(p1, p3), p2), p6);         // c22 = p1 + p3 - p2 + p6

        // Combine submatrices into the final result
        method5(c11, result, 0, 0);
        method5(c12, result, 0, newSize);
        method5(c21, result, newSize, 0);
        method5(c22, result, newSize, newSize);

        return result;
    }

    /**
     * Subtracts matrix b from matrix a (a - b).
     *
     * @param a first matrix
     * @param b second matrix
     * @return matrix a - b
     */
    public int[][] method2(int[][] a, int[][] b) {
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
    public int[][] method3(int[][] a, int[][] b) {
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
    public void method4(int[][] source, int[][] destination, int rowOffset, int colOffset) {
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
    public void method5(int[][] source, int[][] target, int rowOffset, int colOffset) {
        for (int i = 0, tgtRow = rowOffset; i < source.length; i++, tgtRow++) {
            for (int j = 0, tgtCol = colOffset; j < source.length; j++, tgtCol++) {
                target[tgtRow][tgtCol] = source[i][j];
            }
        }
    }
}