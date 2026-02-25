package com.thealgorithms.divideandconquer;

// Java Program to Implement Strassen Algorithm for Matrix Multiplication

/*
 * Uses the divide and conquer approach to multiply two matrices.
 * Time Complexity: O(n^2.8074) better than the O(n^3) of the standard matrix multiplication
 * algorithm. Space Complexity: O(n^2)
 *
 * This Matrix multiplication can be performed only on square matrices
 * where n is a power of 2. Order of both of the matrices are n × n.
 *
 * Reference:
 * https://www.tutorialspoint.com/design_and_analysis_of_algorithms/design_and_analysis_of_algorithms_strassens_matrix_multiplication.htm#:~:text=Strassen's%20Matrix%20multiplication%20can%20be,matrices%20are%20n%20%C3%97%20n.
 * https://www.geeksforgeeks.org/strassens-matrix-multiplication/
 */

public class StrassenMatrixMultiplication {

    // Function to multiply matrices using Strassen's algorithm
    public int[][] multiply(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;

        int[][] result = new int[size][size];

        if (size == 1) {
            result[0][0] = matrixA[0][0] * matrixB[0][0];
        } else {
            int newSize = size / 2;

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

            // Split matrix A into 4 parts
            split(matrixA, a11, 0, 0);
            split(matrixA, a12, 0, newSize);
            split(matrixA, a21, newSize, 0);
            split(matrixA, a22, newSize, newSize);

            // Split matrix B into 4 parts
            split(matrixB, b11, 0, 0);
            split(matrixB, b12, 0, newSize);
            split(matrixB, b21, newSize, 0);
            split(matrixB, b22, newSize, newSize);

            // Strassen's 7 products
            int[][] p1 = multiply(add(a11, a22), add(b11, b22));
            int[][] p2 = multiply(add(a21, a22), b11);
            int[][] p3 = multiply(a11, subtract(b12, b22));
            int[][] p4 = multiply(a22, subtract(b21, b11));
            int[][] p5 = multiply(add(a11, a12), b22);
            int[][] p6 = multiply(subtract(a21, a11), add(b11, b12));
            int[][] p7 = multiply(subtract(a12, a22), add(b21, b22));

            // Result submatrices
            int[][] c11 = add(subtract(add(p1, p4), p5), p7);
            int[][] c12 = add(p3, p5);
            int[][] c21 = add(p2, p4);
            int[][] c22 = add(subtract(add(p1, p3), p2), p6);

            // Join result submatrices into the final result matrix
            join(c11, result, 0, 0);
            join(c12, result, 0, newSize);
            join(c21, result, newSize, 0);
            join(c22, result, newSize, newSize);
        }

        return result;
    }

    // Function to subtract two matrices
    public int[][] subtract(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] result = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                result[row][col] = matrixA[row][col] - matrixB[row][col];
            }
        }

        return result;
    }

    // Function to add two matrices
    public int[][] add(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] result = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                result[row][col] = matrixA[row][col] + matrixB[row][col];
            }
        }

        return result;
    }

    // Function to split parent matrix into child matrix
    public void split(int[][] parent, int[][] child, int rowOffset, int colOffset) {
        for (int childRow = 0, parentRow = rowOffset; childRow < child.length; childRow++, parentRow++) {
            for (int childCol = 0, parentCol = colOffset; childCol < child.length; childCol++, parentCol++) {
                child[childRow][childCol] = parent[parentRow][parentCol];
            }
        }
    }

    // Function to join child matrix into parent matrix
    public void join(int[][] child, int[][] parent, int rowOffset, int colOffset) {
        for (int childRow = 0, parentRow = rowOffset; childRow < child.length; childRow++, parentRow++) {
            for (int childCol = 0, parentCol = colOffset; childCol < child.length; childCol++, parentCol++) {
                parent[parentRow][parentCol] = child[childRow][childCol];
            }
        }
    }
}