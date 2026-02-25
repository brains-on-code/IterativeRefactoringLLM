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
        double[][] identity = createIdentityMatrix(size);
        int[] pivotIndices = new int[size];

        luDecompose(matrix, pivotIndices);
        forwardSubstitution(matrix, identity, pivotIndices);
        backwardSubstitution(matrix, identity, pivotIndices, inverse);

        return inverse;
    }

    private static double[][] createIdentityMatrix(int size) {
        double[][] identity = new double[size][size];
        for (int i = 0; i < size; i++) {
            identity[i][i] = 1.0;
        }
        return identity;
    }

    private static void forwardSubstitution(double[][] luMatrix, double[][] rhs, int[] pivotIndices) {
        int size = luMatrix.length;

        // Solve L * Y = P * I (forward substitution)
        for (int col = 0; col < size - 1; col++) {
            int pivotColIndex = pivotIndices[col];
            for (int row = col + 1; row < size; row++) {
                int pivotRowIndex = pivotIndices[row];
                double multiplier = luMatrix[pivotRowIndex][col];
                for (int k = 0; k < size; k++) {
                    rhs[pivotRowIndex][k] -= multiplier * rhs[pivotColIndex][k];
                }
            }
        }
    }

    private static void backwardSubstitution(
        double[][] luMatrix,
        double[][] rhs,
        int[] pivotIndices,
        double[][] inverse
    ) {
        int size = luMatrix.length;

        // Solve U * X = Y (backward substitution)
        for (int col = 0; col < size; col++) {
            int lastPivotIndex = pivotIndices[size - 1];
            inverse[size - 1][col] =
                rhs[lastPivotIndex][col] / luMatrix[lastPivotIndex][size - 1];

            for (int row = size - 2; row >= 0; row--) {
                int pivotRowIndex = pivotIndices[row];
                double sum = rhs[pivotRowIndex][col];

                for (int k = row + 1; k < size; k++) {
                    sum -= luMatrix[pivotRowIndex][k] * inverse[k][col];
                }

                inverse[row][col] = sum / luMatrix[pivotRowIndex][row];
            }
        }
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

        initializePivotIndices(pivotIndices);
        computeScalingFactors(matrix, scalingFactors);
        performDecomposition(matrix, pivotIndices, scalingFactors);
    }

    private static void initializePivotIndices(int[] pivotIndices) {
        for (int i = 0; i < pivotIndices.length; i++) {
            pivotIndices[i] = i;
        }
    }

    private static void computeScalingFactors(double[][] matrix, double[] scalingFactors) {
        int size = scalingFactors.length;

        for (int i = 0; i < size; i++) {
            double maxAbs = 0.0;
            for (int j = 0; j < size; j++) {
                double value = Math.abs(matrix[i][j]);
                if (value > maxAbs) {
                    maxAbs = value;
                }
            }
            scalingFactors[i] = maxAbs;
        }
    }

    private static void performDecomposition(
        double[][] matrix,
        int[] pivotIndices,
        double[] scalingFactors
    ) {
        int size = pivotIndices.length;

        for (int col = 0; col < size - 1; col++) {
            int pivotRow = selectPivotRow(matrix, pivotIndices, scalingFactors, col);
            swapPivotIndices(pivotIndices, col, pivotRow);
            eliminateBelowPivot(matrix, pivotIndices, col);
        }
    }

    private static int selectPivotRow(
        double[][] matrix,
        int[] pivotIndices,
        double[] scalingFactors,
        int col
    ) {
        int size = pivotIndices.length;
        double maxRatio = 0.0;
        int pivotRow = col;

        for (int row = col; row < size; row++) {
            int pivotRowIndex = pivotIndices[row];
            double ratio =
                Math.abs(matrix[pivotRowIndex][col]) / scalingFactors[pivotRowIndex];
            if (ratio > maxRatio) {
                maxRatio = ratio;
                pivotRow = row;
            }
        }

        return pivotRow;
    }

    private static void swapPivotIndices(int[] pivotIndices, int i, int j) {
        int temp = pivotIndices[i];
        pivotIndices[i] = pivotIndices[j];
        pivotIndices[j] = temp;
    }

    private static void eliminateBelowPivot(
        double[][] matrix,
        int[] pivotIndices,
        int col
    ) {
        int size = pivotIndices.length;
        int pivotColIndex = pivotIndices[col];

        for (int row = col + 1; row < size; row++) {
            int pivotRowIndex = pivotIndices[row];
            double factor = matrix[pivotRowIndex][col] / matrix[pivotColIndex][col];
            matrix[pivotRowIndex][col] = factor;

            for (int k = col + 1; k < size; k++) {
                matrix[pivotRowIndex][k] -= factor * matrix[pivotColIndex][k];
            }
        }
    }
}