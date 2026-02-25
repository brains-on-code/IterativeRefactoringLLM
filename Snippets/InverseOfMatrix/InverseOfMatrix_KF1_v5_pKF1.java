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
        double[][] identity = new double[size][size];
        int[] pivotIndices = new int[size];

        // Initialize identity matrix
        for (int i = 0; i < size; ++i) {
            identity[i][i] = 1.0;
        }

        // Perform LU decomposition with partial pivoting
        performLuDecomposition(matrix, pivotIndices);

        // Forward and backward substitution to compute inverse
        for (int col = 0; col < size; ++col) {
            inverse[size - 1][col] =
                identity[pivotIndices[size - 1]][col] /
                matrix[pivotIndices[size - 1]][size - 1];

            for (int row = size - 2; row >= 0; --row) {
                inverse[row][col] = identity[pivotIndices[row]][col];
                for (int upperRow = row + 1; upperRow < size; ++upperRow) {
                    inverse[row][col] -=
                        matrix[pivotIndices[row]][upperRow] * inverse[upperRow][col];
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
    private static void performLuDecomposition(double[][] matrix, int[] pivotIndices) {
        int size = pivotIndices.length;
        double[] rowScalingFactors = new double[size];

        // Initialize pivot indices
        for (int i = 0; i < size; ++i) {
            pivotIndices[i] = i;
        }

        // Compute implicit scaling factors
        for (int row = 0; row < size; ++row) {
            double maxAbsInRow = 0.0;
            for (int col = 0; col < size; ++col) {
                double absValue = Math.abs(matrix[row][col]);
                if (absValue > maxAbsInRow) {
                    maxAbsInRow = absValue;
                }
            }
            rowScalingFactors[row] = maxAbsInRow;
        }

        // Perform the decomposition
        for (int col = 0; col < size - 1; ++col) {
            double maxScaledPivot = 0.0;
            int pivotRow = col;

            // Select pivot row
            for (int row = col; row < size; ++row) {
                double scaledPivot =
                    Math.abs(matrix[pivotIndices[row]][col]) /
                    rowScalingFactors[pivotIndices[row]];
                if (scaledPivot > maxScaledPivot) {
                    maxScaledPivot = scaledPivot;
                    pivotRow = row;
                }
            }

            // Swap pivot indices
            int tempIndex = pivotIndices[col];
            pivotIndices[col] = pivotIndices[pivotRow];
            pivotIndices[pivotRow] = tempIndex;

            // Elimination
            for (int row = col + 1; row < size; ++row) {
                double eliminationFactor =
                    matrix[pivotIndices[row]][col] /
                    matrix[pivotIndices[col]][col];

                matrix[pivotIndices[row]][col] = eliminationFactor;

                for (int elimCol = col + 1; elimCol < size; ++elimCol) {
                    matrix[pivotIndices[row]][elimCol] -=
                        eliminationFactor * matrix[pivotIndices[col]][elimCol];
                }
            }
        }
    }
}