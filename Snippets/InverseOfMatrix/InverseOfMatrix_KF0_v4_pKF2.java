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
        // Utility class; prevent instantiation
    }

    /**
     * Computes the inverse of a square matrix using Gaussian elimination.
     *
     * @param matrix the matrix to invert (must be square)
     * @return the inverse of {@code matrix}
     */
    public static double[][] invert(double[][] matrix) {
        int n = matrix.length;
        double[][] inverse = new double[n][n];
        double[][] identity = new double[n][n];
        int[] pivotIndex = new int[n];

        buildIdentityMatrix(identity);
        performGaussianElimination(matrix, pivotIndex);
        forwardSubstitution(matrix, identity, pivotIndex);
        backwardSubstitution(matrix, identity, pivotIndex, inverse);

        return inverse;
    }

    /**
     * Builds an identity matrix in-place.
     *
     * @param identity the matrix to transform into an identity matrix
     */
    private static void buildIdentityMatrix(double[][] identity) {
        int n = identity.length;
        for (int i = 0; i < n; ++i) {
            identity[i][i] = 1.0;
        }
    }

    /**
     * Performs forward substitution on the right-hand side (identity matrix)
     * using the LU-decomposed matrix and pivot indices.
     *
     * @param lu         the LU-decomposed matrix
     * @param rhs        the right-hand side matrix (modified in place)
     * @param pivotIndex the pivot indices from Gaussian elimination
     */
    private static void forwardSubstitution(double[][] lu, double[][] rhs, int[] pivotIndex) {
        int n = lu.length;
        for (int i = 0; i < n - 1; ++i) {
            int pivotRow = pivotIndex[i];
            for (int j = i + 1; j < n; ++j) {
                int currentRow = pivotIndex[j];
                double factor = lu[currentRow][i];
                for (int k = 0; k < n; ++k) {
                    rhs[currentRow][k] -= factor * rhs[pivotRow][k];
                }
            }
        }
    }

    /**
     * Performs backward substitution to compute the inverse matrix.
     *
     * @param lu         the LU-decomposed matrix
     * @param rhs        the right-hand side matrix after forward substitution
     * @param pivotIndex the pivot indices from Gaussian elimination
     * @param inverse    the resulting inverse matrix (output)
     */
    private static void backwardSubstitution(
        double[][] lu,
        double[][] rhs,
        int[] pivotIndex,
        double[][] inverse
    ) {
        int n = lu.length;

        for (int col = 0; col < n; ++col) {
            int lastPivotRow = pivotIndex[n - 1];
            inverse[n - 1][col] = rhs[lastPivotRow][col] / lu[lastPivotRow][n - 1];

            for (int row = n - 2; row >= 0; --row) {
                int pivotRow = pivotIndex[row];
                double sum = rhs[pivotRow][col];

                for (int k = row + 1; k < n; ++k) {
                    sum -= lu[pivotRow][k] * inverse[k][col];
                }

                inverse[row][col] = sum / lu[pivotRow][row];
            }
        }
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
    private static void performGaussianElimination(double[][] a, int[] index) {
        int n = index.length;
        double[] scale = new double[n];

        initializePivotIndex(index);
        computeRowScales(a, scale);

        for (int column = 0; column < n - 1; ++column) {
            int pivotRow = selectPivotRow(a, index, scale, column);
            swapPivotIndex(index, column, pivotRow);
            eliminateBelowPivot(a, index, column);
        }
    }

    /**
     * Initializes the pivot index array to the identity permutation.
     *
     * @param index the pivot index array
     */
    private static void initializePivotIndex(int[] index) {
        for (int i = 0; i < index.length; ++i) {
            index[i] = i;
        }
    }

    /**
     * Computes the scaling factors for each row, used for scaled partial pivoting.
     *
     * @param a     the matrix to analyze
     * @param scale the array to store the maximum absolute value per row
     */
    private static void computeRowScales(double[][] a, double[] scale) {
        int n = a.length;
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
    }

    /**
     * Selects the pivot row for a given column using scaled partial pivoting.
     *
     * @param a      the matrix being decomposed
     * @param index  the current pivot index array
     * @param scale  the row scaling factors
     * @param column the current column
     * @return the index in {@code index} of the chosen pivot row
     */
    private static int selectPivotRow(double[][] a, int[] index, double[] scale, int column) {
        int n = index.length;
        double maxRatio = 0.0;
        int pivotRow = column;

        for (int i = column; i < n; ++i) {
            double ratio = Math.abs(a[index[i]][column]) / scale[index[i]];
            if (ratio > maxRatio) {
                maxRatio = ratio;
                pivotRow = i;
            }
        }

        return pivotRow;
    }

    /**
     * Swaps two entries in the pivot index array.
     *
     * @param index  the pivot index array
     * @param first  the first position to swap
     * @param second the second position to swap
     */
    private static void swapPivotIndex(int[] index, int first, int second) {
        int temp = index[first];
        index[first] = index[second];
        index[second] = temp;
    }

    /**
     * Eliminates entries below the pivot in the given column.
     *
     * @param a           the matrix being decomposed (LU stored in place)
     * @param index       the pivot index array
     * @param pivotColumn the current pivot column
     */
    private static void eliminateBelowPivot(double[][] a, int[] index, int pivotColumn) {
        int n = index.length;

        for (int i = pivotColumn + 1; i < n; ++i) {
            int row = index[i];
            int pivotRow = index[pivotColumn];

            double multiplier = a[row][pivotColumn] / a[pivotRow][pivotColumn];
            a[row][pivotColumn] = multiplier;

            for (int col = pivotColumn + 1; col < n; ++col) {
                a[row][col] -= multiplier * a[pivotRow][col];
            }
        }
    }
}