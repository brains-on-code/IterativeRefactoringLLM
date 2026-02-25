package com.thealgorithms.matrix;

/**
 * Performs matrix inversion using LU decomposition with partial pivoting.
 */
public final class MatrixInversion {

    private MatrixInversion() {
    }

    /**
     * Computes the inverse of a square matrix.
     *
     * @param matrix the input square matrix
     * @return the inverse of the input matrix
     */
    public static double[][] invert(double[][] matrix) {
        int size = matrix.length;
        double[][] inverse = new double[size][size];
        double[][] identityTransformed = new double[size][size];
        int[] pivotIndices = new int[size];

        // Initialize identity matrix
        for (int i = 0; i < size; ++i) {
            identityTransformed[i][i] = 1;
        }

        // Perform LU decomposition with partial pivoting
        luDecomposition(matrix, pivotIndices);

        // Forward and backward substitution to compute inverse
        for (int col = 0; col < size; ++col) {
            inverse[size - 1][col] =
                identityTransformed[pivotIndices[size - 1]][col] /
                matrix[pivotIndices[size - 1]][size - 1];

            for (int row = size - 2; row >= 0; --row) {
                inverse[row][col] = identityTransformed[pivotIndices[row]][col];
                for (int k = row + 1; k < size; ++k) {
                    inverse[row][col] -=
                        matrix[pivotIndices[row]][k] * inverse[k][col];
                }
                inverse[row][col] /= matrix[pivotIndices[row]][row];
            }
        }

        return inverse;
    }

    /**
     * Performs LU decomposition with scaled partial pivoting.
     *
     * @param matrix       the matrix to decompose (modified in place)
     * @param pivotIndices the resulting pivot indices
     */
    private static void luDecomposition(double[][] matrix, int[] pivotIndices) {
        int size = pivotIndices.length;
        double[] scalingFactors = new double[size];

        // Initialize pivot indices
        for (int i = 0; i < size; ++i) {
            pivotIndices[i] = i;
        }

        // Compute implicit scaling factors
        for (int i = 0; i < size; ++i) {
            double maxRowAbs = 0;
            for (int j = 0; j < size; ++j) {
                double currentAbs = Math.abs(matrix[i][j]);
                if (currentAbs > maxRowAbs) {
                    maxRowAbs = currentAbs;
                }
            }
            scalingFactors[i] = maxRowAbs;
        }

        // Perform the decomposition
        for (int col = 0; col < size - 1; ++col) {
            double maxRatio = 0;
            int pivotRow = col;

            // Select pivot row
            for (int row = col; row < size; ++row) {
                double ratio =
                    Math.abs(matrix[pivotIndices[row]][col]) /
                    scalingFactors[pivotIndices[row]];
                if (ratio > maxRatio) {
                    maxRatio = ratio;
                    pivotRow = row;
                }
            }

            // Swap pivot indices
            int temp = pivotIndices[col];
            pivotIndices[col] = pivotIndices[pivotRow];
            pivotIndices[pivotRow] = temp;

            // Elimination
            for (int row = col + 1; row < size; ++row) {
                double factor =
                    matrix[pivotIndices[row]][col] /
                    matrix[pivotIndices[col]][col];

                matrix[pivotIndices[row]][col] = factor;

                for (int k = col + 1; k < size; ++k) {
                    matrix[pivotIndices[row]][k] -=
                        factor * matrix[pivotIndices[col]][k];
                }
            }
        }
    }
}