package com.thealgorithms.matrix;

/**
 * This class provides methods to compute the inverse of a square matrix
 * using Gaussian elimination. For more details, refer to:
 * https://en.wikipedia.org/wiki/Invertible_matrix
 */
public final class InverseOfMatrix {

    private InverseOfMatrix() {
    }

    public static double[][] invert(double[][] matrix) {
        int size = matrix.length;
        double[][] inverse = new double[size][size];
        double[][] identity = new double[size][size];
        int[] pivotIndices = new int[size];

        // Initialize the identity matrix
        for (int row = 0; row < size; ++row) {
            identity[row][row] = 1;
        }

        // Perform Gaussian elimination
        performGaussianElimination(matrix, pivotIndices);

        // Update identity matrix with the ratios stored during elimination
        for (int pivotRow = 0; pivotRow < size - 1; ++pivotRow) {
            for (int row = pivotRow + 1; row < size; ++row) {
                for (int col = 0; col < size; ++col) {
                    identity[pivotIndices[row]][col] -=
                        matrix[pivotIndices[row]][pivotRow] * identity[pivotIndices[pivotRow]][col];
                }
            }
        }

        // Perform backward substitution to find the inverse
        for (int col = 0; col < size; ++col) {
            inverse[size - 1][col] =
                identity[pivotIndices[size - 1]][col] / matrix[pivotIndices[size - 1]][size - 1];

            for (int row = size - 2; row >= 0; --row) {
                inverse[row][col] = identity[pivotIndices[row]][col];
                for (int k = row + 1; k < size; ++k) {
                    inverse[row][col] -= matrix[pivotIndices[row]][k] * inverse[k][col];
                }
                inverse[row][col] /= matrix[pivotIndices[row]][row];
            }
        }

        return inverse;
    }

    /**
     * Performs partial-pivoting Gaussian elimination.
     * The pivotIndices array stores the pivoting order.
     */
    private static void performGaussianElimination(double[][] matrix, int[] pivotIndices) {
        int size = pivotIndices.length;
        double[] scalingFactors = new double[size];

        // Initialize the pivot index array
        for (int i = 0; i < size; ++i) {
            pivotIndices[i] = i;
        }

        // Compute the scaling factors for each row
        for (int row = 0; row < size; ++row) {
            double maxAbsValueInRow = 0;
            for (int col = 0; col < size; ++col) {
                double absValue = Math.abs(matrix[row][col]);
                if (absValue > maxAbsValueInRow) {
                    maxAbsValueInRow = absValue;
                }
            }
            scalingFactors[row] = maxAbsValueInRow;
        }

        // Perform pivoting
        for (int col = 0; col < size - 1; ++col) {
            double maxPivotRatio = 0;
            int maxPivotRowIndex = col;

            for (int row = col; row < size; ++row) {
                double pivotRatio = Math.abs(matrix[pivotIndices[row]][col]) / scalingFactors[pivotIndices[row]];
                if (pivotRatio > maxPivotRatio) {
                    maxPivotRatio = pivotRatio;
                    maxPivotRowIndex = row;
                }
            }

            // Swap pivot indices
            int tempIndex = pivotIndices[col];
            pivotIndices[col] = pivotIndices[maxPivotRowIndex];
            pivotIndices[maxPivotRowIndex] = tempIndex;

            for (int row = col + 1; row < size; ++row) {
                double eliminationFactor =
                    matrix[pivotIndices[row]][col] / matrix[pivotIndices[col]][col];

                // Store elimination factor below the diagonal
                matrix[pivotIndices[row]][col] = eliminationFactor;

                // Update remaining elements in the row
                for (int k = col + 1; k < size; ++k) {
                    matrix[pivotIndices[row]][k] -= eliminationFactor * matrix[pivotIndices[col]][k];
                }
            }
        }
    }
}