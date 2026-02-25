package com.thealgorithms.matrix;

public final class InverseOfMatrix {

    private InverseOfMatrix() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the inverse of a square matrix using Gaussian elimination with
     * scaled partial pivoting.
     *
     * @param matrix the square matrix to invert
     * @return the inverse of the given matrix
     * @throws IllegalArgumentException if the matrix is null, empty, or not square
     */
    public static double[][] invert(double[][] matrix) {
        validateSquareMatrix(matrix);

        int n = matrix.length;
        double[][] inverse = new double[n][n];
        double[][] identity = createIdentityMatrix(n);
        int[] pivotIndex = new int[n];

        performGaussianElimination(matrix, pivotIndex);
        applyForwardSubstitution(matrix, identity, pivotIndex);
        applyBackwardSubstitution(matrix, identity, pivotIndex, inverse);

        return inverse;
    }

    private static double[][] createIdentityMatrix(int size) {
        double[][] identity = new double[size][size];
        for (int i = 0; i < size; ++i) {
            identity[i][i] = 1.0;
        }
        return identity;
    }

    /**
     * Solves L * Y = P * I, where L is the unit lower-triangular part of the
     * LU-decomposed matrix and P is the permutation defined by pivotIndex.
     */
    private static void applyForwardSubstitution(
        double[][] matrix,
        double[][] identity,
        int[] pivotIndex
    ) {
        int n = matrix.length;
        for (int i = 0; i < n - 1; ++i) {
            int pivotRowI = pivotIndex[i];
            for (int j = i + 1; j < n; ++j) {
                int pivotRowJ = pivotIndex[j];
                double factor = matrix[pivotRowJ][i];
                for (int k = 0; k < n; ++k) {
                    identity[pivotRowJ][k] -= factor * identity[pivotRowI][k];
                }
            }
        }
    }

    /**
     * Solves U * X = Y, where U is the upper-triangular part of the
     * LU-decomposed matrix, producing the inverse matrix.
     */
    private static void applyBackwardSubstitution(
        double[][] matrix,
        double[][] identity,
        int[] pivotIndex,
        double[][] inverse
    ) {
        int n = matrix.length;

        for (int col = 0; col < n; ++col) {
            int lastPivotRow = pivotIndex[n - 1];
            inverse[n - 1][col] =
                identity[lastPivotRow][col] / matrix[lastPivotRow][n - 1];

            for (int row = n - 2; row >= 0; --row) {
                int pivotRow = pivotIndex[row];
                double sum = identity[pivotRow][col];

                for (int k = row + 1; k < n; ++k) {
                    sum -= matrix[pivotRow][k] * inverse[k][col];
                }

                inverse[row][col] = sum / matrix[pivotRow][row];
            }
        }
    }

    /**
     * Performs LU decomposition with scaled partial pivoting.
     *
     * @param matrix     the matrix to decompose (in-place)
     * @param pivotIndex array that will contain the row permutation indices
     */
    private static void performGaussianElimination(double[][] matrix, int[] pivotIndex) {
        int n = pivotIndex.length;
        double[] scalingFactors = new double[n];

        initializePivotIndices(pivotIndex);
        computeScalingFactors(matrix, scalingFactors);

        for (int col = 0; col < n - 1; ++col) {
            int pivotRow = selectPivotRow(matrix, pivotIndex, scalingFactors, col);
            swapPivotIndices(pivotIndex, col, pivotRow);
            eliminateBelowPivot(matrix, pivotIndex, col);
        }
    }

    private static void initializePivotIndices(int[] pivotIndex) {
        for (int i = 0; i < pivotIndex.length; ++i) {
            pivotIndex[i] = i;
        }
    }

    /**
     * Computes scaling factors used for scaled partial pivoting.
     * Each factor is the maximum absolute value in the corresponding row.
     */
    private static void computeScalingFactors(double[][] matrix, double[] scalingFactors) {
        int n = matrix.length;
        for (int i = 0; i < n; ++i) {
            double maxInRow = 0.0;
            for (int j = 0; j < n; ++j) {
                double current = Math.abs(matrix[i][j]);
                if (current > maxInRow) {
                    maxInRow = current;
                }
            }
            scalingFactors[i] = maxInRow;
        }
    }

    /**
     * Selects the pivot row for the given column using scaled partial pivoting.
     */
    private static int selectPivotRow(
        double[][] matrix,
        int[] pivotIndex,
        double[] scalingFactors,
        int col
    ) {
        int n = pivotIndex.length;
        double maxRatio = 0.0;
        int pivotRow = col;

        for (int i = col; i < n; ++i) {
            int rowIndex = pivotIndex[i];
            double ratio = Math.abs(matrix[rowIndex][col]) / scalingFactors[rowIndex];
            if (ratio > maxRatio) {
                maxRatio = ratio;
                pivotRow = i;
            }
        }

        return pivotRow;
    }

    private static void swapPivotIndices(int[] pivotIndex, int i, int j) {
        int temp = pivotIndex[i];
        pivotIndex[i] = pivotIndex[j];
        pivotIndex[j] = temp;
    }

    /**
     * Eliminates entries below the pivot in the given column, storing
     * multipliers in the lower part of the matrix (LU decomposition).
     */
    private static void eliminateBelowPivot(double[][] matrix, int[] pivotIndex, int col) {
        int n = pivotIndex.length;

        for (int i = col + 1; i < n; ++i) {
            int rowI = pivotIndex[i];
            int rowPivot = pivotIndex[col];

            double factor = matrix[rowI][col] / matrix[rowPivot][col];
            matrix[rowI][col] = factor;

            for (int l = col + 1; l < n; ++l) {
                matrix[rowI][l] -= factor * matrix[rowPivot][l];
            }
        }
    }

    private static void validateSquareMatrix(double[][] matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Input matrix must not be null.");
        }

        int n = matrix.length;
        if (n == 0) {
            throw new IllegalArgumentException("Input matrix must not be empty.");
        }

        for (double[] row : matrix) {
            if (row == null || row.length != n) {
                throw new IllegalArgumentException("Input matrix must be square.");
            }
        }
    }
}