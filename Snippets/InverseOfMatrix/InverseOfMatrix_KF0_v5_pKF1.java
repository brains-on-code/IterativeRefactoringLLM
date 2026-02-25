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
        int[] pivotOrder = new int[size];

        // Initialize the identity matrix
        for (int row = 0; row < size; ++row) {
            identity[row][row] = 1;
        }

        // Perform Gaussian elimination
        performGaussianElimination(matrix, pivotOrder);

        // Update identity matrix with the ratios stored during elimination
        for (int pivot = 0; pivot < size - 1; ++pivot) {
            for (int row = pivot + 1; row < size; ++row) {
                int currentRow = pivotOrder[row];
                int pivotRow = pivotOrder[pivot];

                for (int col = 0; col < size; ++col) {
                    identity[currentRow][col] -=
                        matrix[currentRow][pivot] * identity[pivotRow][col];
                }
            }
        }

        // Perform backward substitution to find the inverse
        for (int col = 0; col < size; ++col) {
            int lastPivotRow = pivotOrder[size - 1];
            inverse[size - 1][col] =
                identity[lastPivotRow][col] / matrix[lastPivotRow][size - 1];

            for (int row = size - 2; row >= 0; --row) {
                int currentRow = pivotOrder[row];
                inverse[row][col] = identity[currentRow][col];

                for (int upperRow = row + 1; upperRow < size; ++upperRow) {
                    inverse[row][col] -=
                        matrix[currentRow][upperRow] * inverse[upperRow][col];
                }

                inverse[row][col] /= matrix[currentRow][row];
            }
        }

        return inverse;
    }

    /**
     * Performs partial-pivoting Gaussian elimination.
     * The pivotOrder array stores the pivoting order.
     */
    private static void performGaussianElimination(double[][] matrix, int[] pivotOrder) {
        int size = pivotOrder.length;
        double[] rowScaleFactors = new double[size];

        // Initialize the pivot row order array
        for (int row = 0; row < size; ++row) {
            pivotOrder[row] = row;
        }

        // Compute the scaling factors for each row
        for (int row = 0; row < size; ++row) {
            double maxAbsInRow = 0;
            for (int col = 0; col < size; ++col) {
                double absValue = Math.abs(matrix[row][col]);
                if (absValue > maxAbsInRow) {
                    maxAbsInRow = absValue;
                }
            }
            rowScaleFactors[row] = maxAbsInRow;
        }

        // Perform pivoting
        for (int col = 0; col < size - 1; ++col) {
            double maxPivotRatio = 0;
            int bestPivotRow = col;

            for (int row = col; row < size; ++row) {
                int currentRow = pivotOrder[row];
                double pivotRatio =
                    Math.abs(matrix[currentRow][col]) / rowScaleFactors[currentRow];

                if (pivotRatio > maxPivotRatio) {
                    maxPivotRatio = pivotRatio;
                    bestPivotRow = row;
                }
            }

            // Swap pivot row indices
            int tempRow = pivotOrder[col];
            pivotOrder[col] = pivotOrder[bestPivotRow];
            pivotOrder[bestPivotRow] = tempRow;

            for (int row = col + 1; row < size; ++row) {
                int currentRow = pivotOrder[row];
                int pivotRow = pivotOrder[col];

                double eliminationFactor =
                    matrix[currentRow][col] / matrix[pivotRow][col];

                // Store elimination factor below the diagonal
                matrix[currentRow][col] = eliminationFactor;

                // Update remaining elements in the row
                for (int k = col + 1; k < size; ++k) {
                    matrix[currentRow][k] -= eliminationFactor * matrix[pivotRow][k];
                }
            }
        }
    }
}