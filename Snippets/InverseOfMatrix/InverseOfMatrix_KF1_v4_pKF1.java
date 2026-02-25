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
        int dimension = matrix.length;
        double[][] inverseMatrix = new double[dimension][dimension];
        double[][] identityMatrix = new double[dimension][dimension];
        int[] pivotOrder = new int[dimension];

        // Initialize identity matrix
        for (int i = 0; i < dimension; ++i) {
            identityMatrix[i][i] = 1;
        }

        // Perform LU decomposition with partial pivoting
        performLuDecomposition(matrix, pivotOrder);

        // Forward and backward substitution to compute inverse
        for (int column = 0; column < dimension; ++column) {
            inverseMatrix[dimension - 1][column] =
                identityMatrix[pivotOrder[dimension - 1]][column] /
                matrix[pivotOrder[dimension - 1]][dimension - 1];

            for (int row = dimension - 2; row >= 0; --row) {
                inverseMatrix[row][column] = identityMatrix[pivotOrder[row]][column];
                for (int upperRow = row + 1; upperRow < dimension; ++upperRow) {
                    inverseMatrix[row][column] -=
                        matrix[pivotOrder[row]][upperRow] * inverseMatrix[upperRow][column];
                }
                inverseMatrix[row][column] /= matrix[pivotOrder[row]][row];
            }
        }

        return inverseMatrix;
    }

    /**
     * Performs LU decomposition with scaled partial pivoting.
     *
     * @param matrix    the matrix to decompose (modified in place)
     * @param pivotOrder the resulting pivot indices
     */
    private static void performLuDecomposition(double[][] matrix, int[] pivotOrder) {
        int dimension = pivotOrder.length;
        double[] rowScalingFactors = new double[dimension];

        // Initialize pivot order
        for (int i = 0; i < dimension; ++i) {
            pivotOrder[i] = i;
        }

        // Compute implicit scaling factors
        for (int row = 0; row < dimension; ++row) {
            double maxAbsoluteValueInRow = 0;
            for (int column = 0; column < dimension; ++column) {
                double absoluteValue = Math.abs(matrix[row][column]);
                if (absoluteValue > maxAbsoluteValueInRow) {
                    maxAbsoluteValueInRow = absoluteValue;
                }
            }
            rowScalingFactors[row] = maxAbsoluteValueInRow;
        }

        // Perform the decomposition
        for (int column = 0; column < dimension - 1; ++column) {
            double maxScaledPivot = 0;
            int pivotRowIndex = column;

            // Select pivot row
            for (int row = column; row < dimension; ++row) {
                double scaledPivot =
                    Math.abs(matrix[pivotOrder[row]][column]) /
                    rowScalingFactors[pivotOrder[row]];
                if (scaledPivot > maxScaledPivot) {
                    maxScaledPivot = scaledPivot;
                    pivotRowIndex = row;
                }
            }

            // Swap pivot indices
            int tempIndex = pivotOrder[column];
            pivotOrder[column] = pivotOrder[pivotRowIndex];
            pivotOrder[pivotRowIndex] = tempIndex;

            // Elimination
            for (int row = column + 1; row < dimension; ++row) {
                double eliminationFactor =
                    matrix[pivotOrder[row]][column] /
                    matrix[pivotOrder[column]][column];

                matrix[pivotOrder[row]][column] = eliminationFactor;

                for (int eliminationColumn = column + 1; eliminationColumn < dimension; ++eliminationColumn) {
                    matrix[pivotOrder[row]][eliminationColumn] -=
                        eliminationFactor * matrix[pivotOrder[column]][eliminationColumn];
                }
            }
        }
    }
}