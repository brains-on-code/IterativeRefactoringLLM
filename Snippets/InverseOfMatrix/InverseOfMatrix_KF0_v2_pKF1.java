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
        int dimension = matrix.length;
        double[][] inverseMatrix = new double[dimension][dimension];
        double[][] identityMatrix = new double[dimension][dimension];
        int[] pivotRowOrder = new int[dimension];

        // Initialize the identity matrix
        for (int row = 0; row < dimension; ++row) {
            identityMatrix[row][row] = 1;
        }

        // Perform Gaussian elimination
        performGaussianElimination(matrix, pivotRowOrder);

        // Update identity matrix with the ratios stored during elimination
        for (int pivotRow = 0; pivotRow < dimension - 1; ++pivotRow) {
            for (int row = pivotRow + 1; row < dimension; ++row) {
                int currentRowIndex = pivotRowOrder[row];
                int pivotRowIndex = pivotRowOrder[pivotRow];

                for (int col = 0; col < dimension; ++col) {
                    identityMatrix[currentRowIndex][col] -=
                        matrix[currentRowIndex][pivotRow] * identityMatrix[pivotRowIndex][col];
                }
            }
        }

        // Perform backward substitution to find the inverse
        for (int col = 0; col < dimension; ++col) {
            int lastPivotRowIndex = pivotRowOrder[dimension - 1];
            inverseMatrix[dimension - 1][col] =
                identityMatrix[lastPivotRowIndex][col] / matrix[lastPivotRowIndex][dimension - 1];

            for (int row = dimension - 2; row >= 0; --row) {
                int currentRowIndex = pivotRowOrder[row];
                inverseMatrix[row][col] = identityMatrix[currentRowIndex][col];

                for (int upperRow = row + 1; upperRow < dimension; ++upperRow) {
                    inverseMatrix[row][col] -=
                        matrix[currentRowIndex][upperRow] * inverseMatrix[upperRow][col];
                }

                inverseMatrix[row][col] /= matrix[currentRowIndex][row];
            }
        }

        return inverseMatrix;
    }

    /**
     * Performs partial-pivoting Gaussian elimination.
     * The pivotRowOrder array stores the pivoting order.
     */
    private static void performGaussianElimination(double[][] matrix, int[] pivotRowOrder) {
        int dimension = pivotRowOrder.length;
        double[] rowScalingFactors = new double[dimension];

        // Initialize the pivot row order array
        for (int row = 0; row < dimension; ++row) {
            pivotRowOrder[row] = row;
        }

        // Compute the scaling factors for each row
        for (int row = 0; row < dimension; ++row) {
            double maxAbsoluteValueInRow = 0;
            for (int col = 0; col < dimension; ++col) {
                double absoluteValue = Math.abs(matrix[row][col]);
                if (absoluteValue > maxAbsoluteValueInRow) {
                    maxAbsoluteValueInRow = absoluteValue;
                }
            }
            rowScalingFactors[row] = maxAbsoluteValueInRow;
        }

        // Perform pivoting
        for (int col = 0; col < dimension - 1; ++col) {
            double maxPivotRatio = 0;
            int pivotRowWithMaxRatio = col;

            for (int row = col; row < dimension; ++row) {
                int currentRowIndex = pivotRowOrder[row];
                double pivotRatio =
                    Math.abs(matrix[currentRowIndex][col]) / rowScalingFactors[currentRowIndex];

                if (pivotRatio > maxPivotRatio) {
                    maxPivotRatio = pivotRatio;
                    pivotRowWithMaxRatio = row;
                }
            }

            // Swap pivot row indices
            int tempRowIndex = pivotRowOrder[col];
            pivotRowOrder[col] = pivotRowOrder[pivotRowWithMaxRatio];
            pivotRowOrder[pivotRowWithMaxRatio] = tempRowIndex;

            for (int row = col + 1; row < dimension; ++row) {
                int currentRowIndex = pivotRowOrder[row];
                int pivotRowIndex = pivotRowOrder[col];

                double eliminationFactor =
                    matrix[currentRowIndex][col] / matrix[pivotRowIndex][col];

                // Store elimination factor below the diagonal
                matrix[currentRowIndex][col] = eliminationFactor;

                // Update remaining elements in the row
                for (int k = col + 1; k < dimension; ++k) {
                    matrix[currentRowIndex][k] -= eliminationFactor * matrix[pivotRowIndex][k];
                }
            }
        }
    }
}