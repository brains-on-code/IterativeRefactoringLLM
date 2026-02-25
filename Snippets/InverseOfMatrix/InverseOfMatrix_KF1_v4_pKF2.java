package com.thealgorithms.matrix;

/**
 * Utility class for matrix operations.
 *
 * <p>Currently provides a method to compute the inverse of a square matrix
 * using LU decomposition with partial pivoting.</p>
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation of utility class.
    }

    /**
     * Computes the inverse of a square matrix using LU decomposition with
     * partial pivoting.
     *
     * @param matrix the square matrix to invert
     * @return the inverse of {@code matrix}
     * @throws IllegalArgumentException if {@code matrix} is not square or is empty
     */
    public static double[][] method1(double[][] matrix) {
        int n = matrix.length;

        if (n == 0 || matrix[0].length != n) {
            throw new IllegalArgumentException("Matrix must be non-empty and square.");
        }

        double[][] inverse = new double[n][n];
        double[][] identity = new double[n][n];
        int[] pivotIndices = new int[n];

        // Build identity matrix.
        for (int i = 0; i < n; ++i) {
            identity[i][i] = 1.0;
        }

        // Compute in-place LU decomposition with partial pivoting: PA = LU.
        method2(matrix, pivotIndices);

        // Solve A * inverse = I column by column using the LU factors.
        for (int col = 0; col < n; ++col) {
            // Initialize last row for back substitution.
            inverse[n - 1][col] =
                identity[pivotIndices[n - 1]][col] / matrix[pivotIndices[n - 1]][n - 1];

            // Backward substitution for remaining rows.
            for (int row = n - 2; row >= 0; --row) {
                double sum = identity[pivotIndices[row]][col];
                for (int k = row + 1; k < n; ++k) {
                    sum -= matrix[pivotIndices[row]][k] * inverse[k][col];
                }
                inverse[row][col] = sum / matrix[pivotIndices[row]][row];
            }
        }

        return inverse;
    }

    /**
     * Performs in-place LU decomposition with partial pivoting on the given
     * matrix.
     *
     * <p>The decomposition is stored in {@code matrix} such that the strictly
     * lower-triangular part contains the multipliers (L without the unit
     * diagonal) and the upper-triangular part contains U.</p>
     *
     * <p>The pivot indices are stored in {@code pivotIndices}, representing the
     * row permutations applied during the decomposition.</p>
     *
     * @param matrix       the matrix to decompose (modified in place)
     * @param pivotIndices the array to store pivot row indices
     */
    private static void method2(double[][] matrix, int[] pivotIndices) {
        int n = pivotIndices.length;
        double[] scalingFactors = new double[n];

        // Initialize pivot indices to the identity permutation.
        for (int i = 0; i < n; ++i) {
            pivotIndices[i] = i;
        }

        // Compute row scaling factors: maximum absolute value in each row.
        for (int i = 0; i < n; ++i) {
            double maxAbs = 0.0;
            for (int j = 0; j < n; ++j) {
                double value = Math.abs(matrix[i][j]);
                if (value > maxAbs) {
                    maxAbs = value;
                }
            }
            scalingFactors[i] = maxAbs;
        }

        // LU decomposition with scaled partial pivoting.
        for (int col = 0; col < n - 1; ++col) {
            double maxRatio = 0.0;
            int pivotRow = col;

            // Select pivot row based on largest scaled element in this column.
            for (int row = col; row < n; ++row) {
                double ratio =
                    Math.abs(matrix[pivotIndices[row]][col]) / scalingFactors[pivotIndices[row]];
                if (ratio > maxRatio) {
                    maxRatio = ratio;
                    pivotRow = row;
                }
            }

            // Swap pivot indices to reflect chosen pivot row.
            int temp = pivotIndices[col];
            pivotIndices[col] = pivotIndices[pivotRow];
            pivotIndices[pivotRow] = temp;

            // Perform elimination below the pivot.
            for (int row = col + 1; row < n; ++row) {
                double multiplier =
                    matrix[pivotIndices[row]][col] / matrix[pivotIndices[col]][col];

                // Store multiplier in L (below diagonal).
                matrix[pivotIndices[row]][col] = multiplier;

                // Update remaining entries in the current row.
                for (int k = col + 1; k < n; ++k) {
                    matrix[pivotIndices[row]][k] -= multiplier * matrix[pivotIndices[col]][k];
                }
            }
        }
    }
}