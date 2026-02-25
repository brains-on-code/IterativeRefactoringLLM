package com.thealgorithms.divideandconquer;

/**
 * Strassen's algorithm for matrix multiplication using divide and conquer.
 *
 * <p>Time Complexity: O(n^2.8074), better than the O(n^3) of standard matrix multiplication.
 * Space Complexity: O(n^2).
 *
 * <p>This implementation assumes both input matrices are square with size n × n,
 * where n is a power of 2.
 *
 * <p>References:
 * <ul>
 *   <li>https://www.tutorialspoint.com/design_and_analysis_of_algorithms/design_and_analysis_of_algorithms_strassens_matrix_multiplication.htm
 *   <li>https://www.geeksforgeeks.org/strassens-matrix-multiplication/
 * </ul>
 */
public class StrassenMatrixMultiplication {

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

        // Base case: 1×1 matrix multiplication
        if (n == 1) {
            result[0][0] = a[0][0] * b[0][0];
            return result;
        }

        int newSize = n / 2;

        // Partition A into 4 submatrices
        int[][] a11 = new int[newSize][newSize];
        int[][] a12 = new int[newSize][newSize];
        int[][] a21 = new int[newSize][newSize];
        int[][] a22 = new int[newSize][newSize];

        // Partition B into 4 submatrices
        int[][] b11 = new int[newSize][newSize];
        int[][] b12 = new int[newSize][newSize];
        int[][] b21 = new int[newSize][newSize];
        int[][] b22 = new int[newSize][newSize];

        // Split A into quadrants
        split(a, a11, 0, 0);
        split(a, a12, 0, newSize);
        split(a, a21, newSize, 0);
        split(a, a22, newSize, newSize);

        // Split B into quadrants
        split(b, b11, 0, 0);
        split(b, b12, 0, newSize);
        split(b, b21, newSize, 0);
        split(b, b22, newSize, newSize);

        // Strassen's 7 products
        int[][] m1 = multiply(add(a11, a22), add(b11, b22));
        int[][] m2 = multiply(add(a21, a22), b11);
        int[][] m3 = multiply(a11, sub(b12, b22));
        int[][] m4 = multiply(a22, sub(b21, b11));
        int[][] m5 = multiply(add(a11, a12), b22);
        int[][] m6 = multiply(sub(a21, a11), add(b11, b12));
        int[][] m7 = multiply(sub(a12, a22), add(b21, b22));

        // Combine intermediate products into result quadrants
        int[][] c11 = add(sub(add(m1, m4), m5), m7);
        int[][] c12 = add(m3, m5);
        int[][] c21 = add(m2, m4);
        int[][] c22 = add(sub(add(m1, m3), m2), m6);

        // Join quadrants into the final result matrix
        join(c11, result, 0, 0);
        join(c12, result, 0, newSize);
        join(c21, result, newSize, 0);
        join(c22, result, newSize, newSize);

        return result;
    }

    /** Returns the matrix difference a - b. */
    public int[][] sub(int[][] a, int[][] b) {
        int n = a.length;
        int[][] c = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = a[i][j] - b[i][j];
            }
        }

        return c;
    }

    /** Returns the matrix sum a + b. */
    public int[][] add(int[][] a, int[][] b) {
        int n = a.length;
        int[][] c = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
        }

        return c;
    }

    /**
     * Copies a submatrix from parent matrix {@code parent} into child matrix {@code child},
     * starting at (rowOffset, colOffset) in the parent.
     */
    public void split(int[][] parent, int[][] child, int rowOffset, int colOffset) {
        for (int iChild = 0, iParent = rowOffset; iChild < child.length; iChild++, iParent++) {
            for (int jChild = 0, jParent = colOffset; jChild < child.length; jChild++, jParent++) {
                child[iChild][jChild] = parent[iParent][jParent];
            }
        }
    }

    /**
     * Copies matrix {@code child} into a submatrix of {@code parent},
     * starting at (rowOffset, colOffset) in the parent.
     */
    public void join(int[][] child, int[][] parent, int rowOffset, int colOffset) {
        for (int iChild = 0, iParent = rowOffset; iChild < child.length; iChild++, iParent++) {
            for (int jChild = 0, jParent = colOffset; jChild < child.length; jChild++, jParent++) {
                parent[iParent][jParent] = child[iChild][jChild];
            }
        }
    }
}