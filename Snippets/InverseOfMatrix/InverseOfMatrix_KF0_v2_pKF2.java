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
     * @param a the matrix to invert (must be square)
     * @return the inverse of {@code a}
     */
    public static double[][] invert(double[][] a) {
        int n = a.length;
        double[][] inverse = new double[n][n];
        double[][] identity = new double[n][n];
        int[] pivotIndex = new int[n];

        buildIdentityMatrix(identity);

        performGaussianElimination(a, pivotIndex);

        forwardSubstitution(a, identity, pivotIndex);
        backwardSubstitution(a, identity, pivotIndex, inverse);

        return inverse;
    }

    private static void buildIdentityMatrix(double[][] identity) {
        int n = identity.length;
        for (int i = 0; i < n; ++i) {
            identity[i][i] = 1.0;
        }
    }

    private static void forwardSubstitution(double[][] a, double[][] identity, int[] pivotIndex) {
        int n = a.length;
        for (int i = 0; i < n - 1; ++i) {
            int pivotI = pivotIndex[i];
            for (int j = i + 1; j < n; ++j) {
                int pivotJ = pivotIndex[j];
                double factor = a[pivotJ][i];
                for (int k = 0; k < n; ++k) {
                    identity[pivotJ][k] -= factor * identity[pivotI][k];
                }
            }
        }
    }

    private static void backwardSubstitution(
        double[][] a,
        double[][] identity,
        int[] pivotIndex,
        double[][] inverse
    ) {
        int n = a.length;

        for (int col = 0; col < n; ++col) {
            int lastPivot = pivotIndex[n - 1];
            inverse[n - 1][col] = identity[lastPivot][col] / a[lastPivot][n - 1];

            for (int row = n - 2; row >= 0; --row) {
                int pivotRow = pivotIndex[row];
                double sum = identity[pivotRow][col];

                for (int k = row + 1; k < n; ++k) {
                    sum -= a[pivotRow][k] * inverse[k][col];
                }

                inverse[row][col] = sum / a[pivotRow][row];
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

        for (int j = 0; j < n - 1; ++j) {
            int pivotRow = selectPivotRow(a, index, scale, j);
            swapPivotIndex(index, j, pivotRow);
            eliminateBelowPivot(a, index, j);
        }
    }

    private static void initializePivotIndex(int[] index) {
        for (int i = 0; i < index.length; ++i) {
            index[i] = i;
        }
    }

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

    private static void swapPivotIndex(int[] index, int j, int pivotRow) {
        int temp = index[j];
        index[j] = index[pivotRow];
        index[pivotRow] = temp;
    }

    private static void eliminateBelowPivot(double[][] a, int[] index, int pivotColumn) {
        int n = index.length;

        for (int i = pivotColumn + 1; i < n; ++i) {
            int rowI = index[i];
            int rowPivot = index[pivotColumn];

            double multiplier = a[rowI][pivotColumn] / a[rowPivot][pivotColumn];
            a[rowI][pivotColumn] = multiplier;

            for (int col = pivotColumn + 1; col < n; ++col) {
                a[rowI][col] -= multiplier * a[rowPivot][col];
            }
        }
    }
}