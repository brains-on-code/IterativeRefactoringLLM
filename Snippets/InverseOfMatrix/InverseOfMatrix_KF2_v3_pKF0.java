package com.thealgorithms.matrix;

public final class InverseOfMatrix {

    private InverseOfMatrix() {
        // Utility class; prevent instantiation
    }

    public static double[][] invert(double[][] matrix) {
        int size = matrix.length;

        double[][] lu = deepCopy(matrix);
        double[][] inverse = new double[size][size];
        double[][] identity = identityMatrix(size);
        int[] pivotIndices = new int[size];

        decomposeToLU(lu, pivotIndices);
        forwardSubstitution(lu, identity, pivotIndices);
        backSubstitution(lu, identity, inverse, pivotIndices);

        return inverse;
    }

    private static double[][] deepCopy(double[][] matrix) {
        int size = matrix.length;
        double[][] copy = new double[size][size];

        for (int row = 0; row < size; row++) {
            System.arraycopy(matrix[row], 0, copy[row], 0, size);
        }

        return copy;
    }

    private static double[][] identityMatrix(int size) {
        double[][] identity = new double[size][size];

        for (int i = 0; i < size; i++) {
            identity[i][i] = 1.0;
        }

        return identity;
    }

    private static void forwardSubstitution(double[][] lu, double[][] identity, int[] pivotIndices) {
        int size = lu.length;

        for (int pivot = 0; pivot < size - 1; pivot++) {
            int pivotRowIndex = pivotIndices[pivot];

            for (int row = pivot + 1; row < size; row++) {
                int currentRowIndex = pivotIndices[row];
                double factor = lu[currentRowIndex][pivot];

                for (int col = 0; col < size; col++) {
                    identity[currentRowIndex][col] -= factor * identity[pivotRowIndex][col];
                }
            }
        }
    }

    private static void backSubstitution(
        double[][] lu,
        double[][] identity,
        double[][] inverse,
        int[] pivotIndices
    ) {
        int size = lu.length;

        for (int col = 0; col < size; col++) {
            int lastPivotRowIndex = pivotIndices[size - 1];
            inverse[size - 1][col] =
                identity[lastPivotRowIndex][col] / lu[lastPivotRowIndex][size - 1];

            for (int row = size - 2; row >= 0; row--) {
                int pivotRowIndex = pivotIndices[row];
                double sum = identity[pivotRowIndex][col];

                for (int k = row + 1; k < size; k++) {
                    sum -= lu[pivotRowIndex][k] * inverse[k][col];
                }

                inverse[row][col] = sum / lu[pivotRowIndex][row];
            }
        }
    }

    private static void decomposeToLU(double[][] matrix, int[] pivotIndices) {
        int size = pivotIndices.length;
        double[] scalingFactors = new double[size];

        initializePivotIndices(pivotIndices);
        computeScalingFactors(matrix, scalingFactors);
        performElimination(matrix, pivotIndices, scalingFactors);
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

    private static void performElimination(
        double[][] matrix,
        int[] pivotIndices,
        double[] scalingFactors
    ) {
        int size = pivotIndices.length;

        for (int col = 0; col < size - 1; col++) {
            int pivotRow = selectPivotRow(matrix, pivotIndices, scalingFactors, col);
            swap(pivotIndices, col, pivotRow);
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
            int currentRowIndex = pivotIndices[row];
            double ratio =
                Math.abs(matrix[currentRowIndex][col]) / scalingFactors[currentRowIndex];

            if (ratio > maxRatio) {
                maxRatio = ratio;
                pivotRow = row;
            }
        }

        return pivotRow;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void eliminateBelowPivot(double[][] matrix, int[] pivotIndices, int col) {
        int size = pivotIndices.length;
        int pivotRowIndex = pivotIndices[col];

        for (int row = col + 1; row < size; row++) {
            int currentRowIndex = pivotIndices[row];
            double factor = matrix[currentRowIndex][col] / matrix[pivotRowIndex][col];
            matrix[currentRowIndex][col] = factor;

            for (int k = col + 1; k < size; k++) {
                matrix[currentRowIndex][k] -= factor * matrix[pivotRowIndex][k];
            }
        }
    }
}