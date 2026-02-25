package com.thealgorithms.matrix;

/**
 * Utility class for computing the inverse of a square matrix
 * using Gaussian elimination with partial pivoting.
 *
 * <p>For more details, see:
 * https://en.wikipedia.org/wiki/Invertible_matrix
 */
public final class InverseOfMatrix {

    private InverseOfMatrix() {
        // Prevent instantiation
    }

    /**
     * Computes the inverse of a square matrix using Gaussian elimination.
     *
     * @param a the matrix to invert (must be square)
     * @return the inverse of {@code a}
     */
    public static double[][] invert(double[][] a) {
        int n = a.length;
        double[][] inverse = new double[n][n];
        double[][] identity = new double[n][n];
        int[] pivotIndex = new int[n];

        // Build identity matrix
        for (int i = 0; i < n; ++i) {
            identity[i][i] = 1.0;
        }

        // Perform Gaussian elimination with partial pivoting
        gaussian(a, pivotIndex);

        // Forward substitution on the identity matrix using the
        // multipliers stored in the lower part of matrix a
        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = 0; k < n; ++k) {
                    identity[pivotIndex[j]][k] -= a[pivotIndex[j]][i] * identity[pivotIndex[i]][k];
                }
            }
        }

        // Backward substitution to compute the inverse columns
        for (int col = 0; col < n; ++col) {
            inverse[n - 1][col] =
                identity[pivotIndex[n - 1]][col] / a[pivotIndex[n - 1]][n - 1];

            for (int row = n - 2; row >= 0; --row) {
                double sum = identity[pivotIndex[row]][col];
                for (int k = row + 1; k < n; ++k) {
                    sum -= a[pivotIndex[row]][k] * inverse[k][col];
                }
                inverse[row][col] = sum / a[pivotIndex[row]][row];
            }
        }

        return inverse;
    }

    /**
     * Performs partial-pivoting Gaussian elimination on matrix {@code a}.
     * The pivot order is stored in {@code index}.
     *
     * <p>After this method:
     * <ul>
     *   <li>The upper triangle of {@code a} contains the U matrix.</li>
     *   <li>The strictly lower triangle of {@code a} contains the
     *       multipliers (L without the unit diagonal).</li>
     *   <li>{@code index} encodes the row permutations.</li>
     * </ul>
     *
     * @param a     the matrix to decompose (modified in place)
     * @param index array that will store the pivot order
     */
    private static void gaussian(double[][] a, int[] index) {
        int n = index.length;
        double[] scale = new double[n];

        // Initialize pivot index to the identity permutation
        for (int i = 0; i < n; ++i) {
            index[i] = i;
        }

        // Compute row scaling factors (largest absolute value in each row)
        for (int i = 0; i < n; ++i) {
            double max = 0.0;
            for (int j = 0; j < n; ++j) {
                double value = Math.abs(a[i][j]);
                if (value > max) {
                    max = value;
                }
            }
            scale[i] = max;
        }

        // Perform elimination with partial pivoting
        for (int j = 0; j < n - 1; ++j) {
            double maxRatio = 0.0;
            int pivotRow = j;

            // Select pivot row based on scaled largest coefficient
            for (int i = j; i < n; ++i) {
                double ratio = Math.abs(a[index[i]][j]) / scale[index[i]];
                if (ratio > maxRatio) {
                    maxRatio = ratio;
                    pivotRow = i;
                }
            }

            // Swap pivot row into position j
            int temp = index[j];
            index[j] = index[pivotRow];
            index[pivotRow] = temp;

            // Eliminate entries below the pivot
            for (int i = j + 1; i < n; ++i) {
                double multiplier = a[index[i]][j] / a[index[j]][j];

                // Store multiplier in the lower triangle
                a[index[i]][j] = multiplier;

                // Update remaining entries in the row
                for (int l = j + 1; l < n; ++l) {
                    a[index[i]][l] -= multiplier * a[index[j]][l];
                }
            }
        }
    }
}