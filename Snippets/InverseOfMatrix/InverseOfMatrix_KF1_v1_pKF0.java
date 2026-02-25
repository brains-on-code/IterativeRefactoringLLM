package com.thealgorithms.matrix;

/**
 * Utility class for matrix operations.
 *
 * This class provides a method to compute the inverse of a square matrix
 * using LU decomposition with scaled partial pivoting.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the inverse of a square matrix using LU decomposition.
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
            identityTransformed[i][i] = 1.0;
        }

        // Perform LU decomposition with pivoting
        luDecompose(matrix, pivotIndices);

        // Forward substitution to solve L * Y = P * I
        for (int col = 0; col < size - 1; ++col) {
            for (int row = col + 1; row < size; ++row) {
                for (int k = 0; k < size; ++k) {
                    identityTransformed[pivotIndices[row]][k] -=
                        matrix[pivotIndices[row]][col] * identityTransformed[pivotIndices[col]][k];
                }
            }
        }

        // Backward substitution to solve U * X = Y
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
     * Decomposes the given matrix in-place into L and U such that P * A = L * U,
     * where P is the permutation matrix represented by pivotIndices.
     *
     * @param matrix       the matrix to decompose (modified in-place)
     * @param pivotIndices the pivot indices representing row permutations
     */
    private static void luDecompose(double[][] matrix, int[] pivotIndices) {
        int size = pivotIndices.length;
        double[] scalingFactors = new double[size];

        // Initialize pivot indices
        for (int i = 0; i < size; ++i) {
            pivotIndices[i] = i;
        }

        // Compute scaling factors for each row
        for (int i = 0; i < size; ++i) {
            double maxAbs = 0.0;
            for (int j = 0; j < size; ++j) {
                double value = Math.abs(matrix[i][j]);
                if (value > maxAbs) {
                    maxAbs = value;
                }
            }
            scalingFactors[i] = maxAbs;
        }

        // Perform the decomposition
        for (int col = 0; col < size - 1; ++col) {
            double maxRatio = 0.0;
            int pivotRow = col;

            // Select pivot row using scaled partial pivoting
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

            // Eliminate entries below the pivot
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