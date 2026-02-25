package com.thealgorithms.matrix;

/**
 * This class provides methods to compute the inverse of a square matrix
 * using Gaussian elimination. For more details, refer to:
 * https://en.wikipedia.org/wiki/Invertible_matrix
 */
public final class InverseOfMatrix {

    private InverseOfMatrix() {
        // Utility class; prevent instantiation
    }

    public static double[][] invert(double[][] matrix) {
        int size = matrix.length;
        double[][] inverse = new double[size][size];
        double[][] identity = new double[size][size];
        int[] pivotIndex = new int[size];

        initializeIdentityMatrix(identity);
        performGaussianElimination(matrix, pivotIndex);
        applyEliminationToIdentity(matrix, identity, pivotIndex);
        performBackwardSubstitution(matrix, identity, inverse, pivotIndex);

        return inverse;
    }

    private static void initializeIdentityMatrix(double[][] identity) {
        int size = identity.length;
        for (int i = 0; i < size; i++) {
            identity[i][i] = 1.0;
        }
    }

    private static void applyEliminationToIdentity(double[][] matrix, double[][] identity, int[] pivotIndex) {
        int size = matrix.length;
        for (int pivotCol = 0; pivotCol < size - 1; pivotCol++) {
            int pivotRowIndex = pivotIndex[pivotCol];
            for (int row = pivotCol + 1; row < size; row++) {
                int currentRowIndex = pivotIndex[row];
                double factor = matrix[currentRowIndex][pivotCol];
                for (int col = 0; col < size; col++) {
                    identity[currentRowIndex][col] -= factor * identity[pivotRowIndex][col];
                }
            }
        }
    }

    private static void performBackwardSubstitution(
        double[][] matrix,
        double[][] identity,
        double[][] inverse,
        int[] pivotIndex
    ) {
        int size = matrix.length;

        for (int col = 0; col < size; col++) {
            int lastPivotRowIndex = pivotIndex[size - 1];
            inverse[size - 1][col] =
                identity[lastPivotRowIndex][col] / matrix[lastPivotRowIndex][size - 1];

            for (int row = size - 2; row >= 0; row--) {
                int currentPivotRowIndex = pivotIndex[row];
                double sum = identity[currentPivotRowIndex][col];

                for (int k = row + 1; k < size; k++) {
                    sum -= matrix[currentPivotRowIndex][k] * inverse[k][col];
                }

                inverse[row][col] = sum / matrix[currentPivotRowIndex][row];
            }
        }
    }

    /**
     * Performs partial-pivoting Gaussian elimination.
     * The pivotIndex array stores the pivoting order.
     */
    private static void performGaussianElimination(double[][] matrix, int[] pivotIndex) {
        int size = pivotIndex.length;
        double[] scalingFactors = new double[size];

        initializePivotIndex(pivotIndex);
        computeScalingFactors(matrix, scalingFactors);
        pivotAndEliminate(matrix, pivotIndex, scalingFactors);
    }

    private static void initializePivotIndex(int[] pivotIndex) {
        for (int i = 0; i < pivotIndex.length; i++) {
            pivotIndex[i] = i;
        }
    }

    private static void computeScalingFactors(double[][] matrix, double[] scalingFactors) {
        int size = matrix.length;

        for (int row = 0; row < size; row++) {
            double max = 0.0;
            for (int col = 0; col < size; col++) {
                double value = Math.abs(matrix[row][col]);
                if (value > max) {
                    max = value;
                }
            }
            scalingFactors[row] = max;
        }
    }

    private static void pivotAndEliminate(double[][] matrix, int[] pivotIndex, double[] scalingFactors) {
        int size = pivotIndex.length;

        for (int col = 0; col < size - 1; col++) {
            int pivotRow = selectPivotRow(matrix, pivotIndex, scalingFactors, col);
            swapPivotRows(pivotIndex, col, pivotRow);
            eliminateBelowPivot(matrix, pivotIndex, col);
        }
    }

    private static int selectPivotRow(
        double[][] matrix,
        int[] pivotIndex,
        double[] scalingFactors,
        int col
    ) {
        int size = pivotIndex.length;
        double maxRatio = 0.0;
        int pivotRow = col;

        for (int row = col; row < size; row++) {
            int currentRowIndex = pivotIndex[row];
            double ratio = Math.abs(matrix[currentRowIndex][col]) / scalingFactors[currentRowIndex];
            if (ratio > maxRatio) {
                maxRatio = ratio;
                pivotRow = row;
            }
        }

        return pivotRow;
    }

    private static void swapPivotRows(int[] pivotIndex, int first, int second) {
        int temp = pivotIndex[first];
        pivotIndex[first] = pivotIndex[second];
        pivotIndex[second] = temp;
    }

    private static void eliminateBelowPivot(double[][] matrix, int[] pivotIndex, int pivotCol) {
        int size = pivotIndex.length;

        for (int row = pivotCol + 1; row < size; row++) {
            int currentRowIndex = pivotIndex[row];
            int pivotRowIndex = pivotIndex[pivotCol];

            double factor = matrix[currentRowIndex][pivotCol] / matrix[pivotRowIndex][pivotCol];
            matrix[currentRowIndex][pivotCol] = factor;

            for (int col = pivotCol + 1; col < size; col++) {
                matrix[currentRowIndex][col] -= factor * matrix[pivotRowIndex][col];
            }
        }
    }
}