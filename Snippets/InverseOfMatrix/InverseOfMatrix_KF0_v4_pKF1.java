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
        for (int rowIndex = 0; rowIndex < dimension; ++rowIndex) {
            identityMatrix[rowIndex][rowIndex] = 1;
        }

        // Perform Gaussian elimination
        performGaussianElimination(matrix, pivotRowOrder);

        // Update identity matrix with the ratios stored during elimination
        for (int pivotIndex = 0; pivotIndex < dimension - 1; ++pivotIndex) {
            for (int rowIndex = pivotIndex + 1; rowIndex < dimension; ++rowIndex) {
                int currentRowIndex = pivotRowOrder[rowIndex];
                int pivotRowIndex = pivotRowOrder[pivotIndex];

                for (int columnIndex = 0; columnIndex < dimension; ++columnIndex) {
                    identityMatrix[currentRowIndex][columnIndex] -=
                        matrix[currentRowIndex][pivotIndex] * identityMatrix[pivotRowIndex][columnIndex];
                }
            }
        }

        // Perform backward substitution to find the inverse
        for (int columnIndex = 0; columnIndex < dimension; ++columnIndex) {
            int lastPivotRowIndex = pivotRowOrder[dimension - 1];
            inverseMatrix[dimension - 1][columnIndex] =
                identityMatrix[lastPivotRowIndex][columnIndex] / matrix[lastPivotRowIndex][dimension - 1];

            for (int rowIndex = dimension - 2; rowIndex >= 0; --rowIndex) {
                int currentRowIndex = pivotRowOrder[rowIndex];
                inverseMatrix[rowIndex][columnIndex] = identityMatrix[currentRowIndex][columnIndex];

                for (int upperRowIndex = rowIndex + 1; upperRowIndex < dimension; ++upperRowIndex) {
                    inverseMatrix[rowIndex][columnIndex] -=
                        matrix[currentRowIndex][upperRowIndex] * inverseMatrix[upperRowIndex][columnIndex];
                }

                inverseMatrix[rowIndex][columnIndex] /= matrix[currentRowIndex][rowIndex];
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
        for (int rowIndex = 0; rowIndex < dimension; ++rowIndex) {
            pivotRowOrder[rowIndex] = rowIndex;
        }

        // Compute the scaling factors for each row
        for (int rowIndex = 0; rowIndex < dimension; ++rowIndex) {
            double maxAbsoluteValueInRow = 0;
            for (int columnIndex = 0; columnIndex < dimension; ++columnIndex) {
                double absoluteValue = Math.abs(matrix[rowIndex][columnIndex]);
                if (absoluteValue > maxAbsoluteValueInRow) {
                    maxAbsoluteValueInRow = absoluteValue;
                }
            }
            rowScalingFactors[rowIndex] = maxAbsoluteValueInRow;
        }

        // Perform pivoting
        for (int columnIndex = 0; columnIndex < dimension - 1; ++columnIndex) {
            double maxPivotRatio = 0;
            int bestPivotRowIndex = columnIndex;

            for (int rowIndex = columnIndex; rowIndex < dimension; ++rowIndex) {
                int currentRowIndex = pivotRowOrder[rowIndex];
                double pivotRatio =
                    Math.abs(matrix[currentRowIndex][columnIndex]) / rowScalingFactors[currentRowIndex];

                if (pivotRatio > maxPivotRatio) {
                    maxPivotRatio = pivotRatio;
                    bestPivotRowIndex = rowIndex;
                }
            }

            // Swap pivot row indices
            int tempRowIndex = pivotRowOrder[columnIndex];
            pivotRowOrder[columnIndex] = pivotRowOrder[bestPivotRowIndex];
            pivotRowOrder[bestPivotRowIndex] = tempRowIndex;

            for (int rowIndex = columnIndex + 1; rowIndex < dimension; ++rowIndex) {
                int currentRowIndex = pivotRowOrder[rowIndex];
                int pivotRowIndex = pivotRowOrder[columnIndex];

                double eliminationFactor =
                    matrix[currentRowIndex][columnIndex] / matrix[pivotRowIndex][columnIndex];

                // Store elimination factor below the diagonal
                matrix[currentRowIndex][columnIndex] = eliminationFactor;

                // Update remaining elements in the row
                for (int k = columnIndex + 1; k < dimension; ++k) {
                    matrix[currentRowIndex][k] -= eliminationFactor * matrix[pivotRowIndex][k];
                }
            }
        }
    }
}