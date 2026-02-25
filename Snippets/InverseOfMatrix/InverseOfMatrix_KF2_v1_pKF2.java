package com.thealgorithms.matrix;

public final class InverseOfMatrix {

    private InverseOfMatrix() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the inverse of a square matrix using Gaussian elimination with
     * partial pivoting.
     *
     * @param matrix the square matrix to invert
     * @return the inverse of the given matrix
     * @throws IllegalArgumentException if the matrix is null or not square
     */
    public static double[][] invert(double[][] matrix) {
        validateSquareMatrix(matrix);

        int n = matrix.length;
        double[][] inverse = new double[n][n];
        double[][] identity = new double[n][n];
        int[] pivotIndex = new int[n];

        // Initialize identity matrix.
        for (int i = 0; i < n; ++i) {
            identity[i][i] = 1.0;
        }

        // Perform Gaussian elimination with partial pivoting.
        gaussianElimination(matrix, pivotIndex);

        // Forward substitution to transform identity according to elimination.
        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = 0; k < n; ++k) {
                    identity[pivotIndex[j]][k] -= matrix[pivotIndex[j]][i] * identity[pivotIndex[i]][k];
                }
            }
        }

        // Backward substitution to compute the inverse.
        for (int i = 0; i < n; ++i) {
            inverse[n - 1][i] =
                identity[pivotIndex[n - 1]][i] / matrix[pivotIndex[n - 1]][n - 1];

            for (int j = n - 2; j >= 0; --j) {
                inverse[j][i] = identity[pivotIndex[j]][i];

                for (int k = j + 1; k < n; ++k) {
                    inverse[j][i] -= matrix[pivotIndex[j]][k] * inverse[k][i];
                }

                inverse[j][i] /= matrix[pivotIndex[j]][j];
            }
        }

        return inverse;
    }

    /**
     * Performs Gaussian elimination with scaled partial pivoting.
     *
     * @param matrix the matrix to transform (in-place)
     * @param pivotIndex array that will contain the row permutation indices
     */
    private static void gaussianElimination(double[][] matrix, int[] pivotIndex) {
        int n = pivotIndex.length;
        double[] scalingFactors = new double[n];

        // Initialize pivot indices to the identity permutation.
        for (int i = 0; i < n; ++i) {
            pivotIndex[i] = i;
        }

        // Compute scaling factors: the largest absolute value in each row.
        for (int i = 0; i < n; ++i) {
            double maxInRow = 0.0;
            for (int j = 0; j < n; ++j) {
                double current = Math.abs(matrix[i][j]);
                if (current > maxInRow) {
                    maxInRow = current;
                }
            }
            scalingFactors[i] = maxInRow;
        }

        // Perform elimination with scaled partial pivoting.
        for (int j = 0; j < n - 1; ++j) {
            double maxRatio = 0.0;
            int pivotRow = j;

            // Select pivot row based on maximum ratio of |a_ij| / scalingFactor_i.
            for (int i = j; i < n; ++i) {
                double ratio = Math.abs(matrix[pivotIndex[i]][j]) / scalingFactors[pivotIndex[i]];
                if (ratio > maxRatio) {
                    maxRatio = ratio;
                    pivotRow = i;
                }
            }

            // Swap pivot indices.
            int temp = pivotIndex[j];
            pivotIndex[j] = pivotRow;
            pivotIndex[pivotRow] = temp;

            // Eliminate entries below the pivot.
            for (int i = j + 1; i < n; ++i) {
                double factor = matrix[pivotIndex[i]][j] / matrix[pivotIndex[j]][j];
                matrix[pivotIndex[i]][j] = factor;

                for (int l = j + 1; l < n; ++l) {
                    matrix[pivotIndex[i]][l] -= factor * matrix[pivotIndex[j]][l];
                }
            }
        }
    }

    private static void validateSquareMatrix(double[][] matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Input matrix must not be null.");
        }
        int n = matrix.length;
        if (n == 0) {
            throw new IllegalArgumentException("Input matrix must not be empty.");
        }
        for (double[] row : matrix) {
            if (row == null || row.length != n) {
                throw new IllegalArgumentException("Input matrix must be square.");
            }
        }
    }
}