package com.thealgorithms.matrix;

public final class InverseOfMatrix {

    private InverseOfMatrix() {
        // Utility class; prevent instantiation
    }

    public static double[][] invert(double[][] matrix) {
        int size = matrix.length;
        double[][] inverse = new double[size][size];
        double[][] identity = new double[size][size];
        int[] pivotIndices = new int[size];

        initializeIdentityMatrix(identity);
        performGaussianElimination(matrix, pivotIndices);
        applyRowOperations(matrix, identity, pivotIndices);
        backSubstitution(matrix, identity, inverse, pivotIndices);

        return inverse;
    }

    private static void initializeIdentityMatrix(double[][] identity) {
        for (int i = 0; i < identity.length; i++) {
            identity[i][i] = 1.0;
        }
    }

    private static void applyRowOperations(double[][] matrix, double[][] identity, int[] pivotIndices) {
        int size = matrix.length;

        for (int pivot = 0; pivot < size - 1; pivot++) {
            for (int row = pivot + 1; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    identity[pivotIndices[row]][col] -=
                        matrix[pivotIndices[row]][pivot] * identity[pivotIndices[pivot]][col];
                }
            }
        }
    }

    private static void backSubstitution(
        double[][] matrix,
        double[][] identity,
        double[][] inverse,
        int[] pivotIndices
    ) {
        int size = matrix.length;

        for (int col = 0; col < size; col++) {
            inverse[size - 1][col] =
                identity[pivotIndices[size - 1]][col] / matrix[pivotIndices[size - 1]][size - 1];

            for (int row = size - 2; row >= 0; row--) {
                inverse[row][col] = identity[pivotIndices[row]][col];

                for (int k = row + 1; k < size; k++) {
                    inverse[row][col] -= matrix[pivotIndices[row]][k] * inverse[k][col];
                }

                inverse[row][col] /= matrix[pivotIndices[row]][row];
            }
        }
    }

    private static void performGaussianElimination(double[][] matrix, int[] pivotIndices) {
        int size = pivotIndices.length;
        double[] scalingFactors = new double[size];

        initializePivotIndices(pivotIndices);
        computeScalingFactors(matrix, scalingFactors);
        eliminate(matrix, pivotIndices, scalingFactors);
    }

    private static void initializePivotIndices(int[] pivotIndices) {
        for (int i = 0; i < pivotIndices.length; i++) {
            pivotIndices[i] = i;
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

    private static void eliminate(double[][] matrix, int[] pivotIndices, double[] scalingFactors) {
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
            double ratio = Math.abs(matrix[pivotIndices[row]][col]) / scalingFactors[pivotIndices[row]];
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

    private static void eliminateBelowPivot(double[][] matrix, int[] pivotIndices, int col) {
        int size = pivotIndices.length;

        for (int row = col + 1; row < size; row++) {
            double factor = matrix[pivotIndices[row]][col] / matrix[pivotIndices[col]][col];
            matrix[pivotIndices[row]][col] = factor;

            for (int k = col + 1; k < size; k++) {
                matrix[pivotIndices[row]][k] -= factor * matrix[pivotIndices[col]][k];
            }
        }
    }
}