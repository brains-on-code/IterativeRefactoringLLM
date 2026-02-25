package com.thealgorithms.matrix;

public final class InverseOfMatrix {

    private InverseOfMatrix() {}

    public static double[][] invert(double[][] matrix) {
        int size = matrix.length;
        double[][] inverse = new double[size][size];
        double[][] identity = new double[size][size];
        int[] pivotIndices = new int[size];

        for (int i = 0; i < size; ++i) {
            identity[i][i] = 1;
        }

        performGaussianElimination(matrix, pivotIndices);

        for (int pivotRow = 0; pivotRow < size - 1; ++pivotRow) {
            for (int row = pivotRow + 1; row < size; ++row) {
                for (int col = 0; col < size; ++col) {
                    identity[pivotIndices[row]][col] -=
                        matrix[pivotIndices[row]][pivotRow] * identity[pivotIndices[pivotRow]][col];
                }
            }
        }

        for (int col = 0; col < size; ++col) {
            inverse[size - 1][col] =
                identity[pivotIndices[size - 1]][col] / matrix[pivotIndices[size - 1]][size - 1];

            for (int row = size - 2; row >= 0; --row) {
                inverse[row][col] = identity[pivotIndices[row]][col];

                for (int k = row + 1; k < size; ++k) {
                    inverse[row][col] -= matrix[pivotIndices[row]][k] * inverse[k][col];
                }

                inverse[row][col] /= matrix[pivotIndices[row]][row];
            }
        }

        return inverse;
    }

    private static void performGaussianElimination(double[][] matrix, int[] pivotIndices) {
        int size = pivotIndices.length;
        double[] scalingFactors = new double[size];

        for (int i = 0; i < size; ++i) {
            pivotIndices[i] = i;
        }

        for (int row = 0; row < size; ++row) {
            double maxRowElement = 0;
            for (int col = 0; col < size; ++col) {
                double currentElementAbs = Math.abs(matrix[row][col]);
                if (currentElementAbs > maxRowElement) {
                    maxRowElement = currentElementAbs;
                }
            }
            scalingFactors[row] = maxRowElement;
        }

        for (int col = 0; col < size - 1; ++col) {
            double maxRatio = 0;
            int maxRatioRowIndex = col;

            for (int row = col; row < size; ++row) {
                double currentRatio =
                    Math.abs(matrix[pivotIndices[row]][col]) / scalingFactors[pivotIndices[row]];
                if (currentRatio > maxRatio) {
                    maxRatio = currentRatio;
                    maxRatioRowIndex = row;
                }
            }

            int tempIndex = pivotIndices[col];
            pivotIndices[col] = pivotIndices[maxRatioRowIndex];
            pivotIndices[maxRatioRowIndex] = tempIndex;

            for (int row = col + 1; row < size; ++row) {
                double eliminationFactor =
                    matrix[pivotIndices[row]][col] / matrix[pivotIndices[col]][col];

                matrix[pivotIndices[row]][col] = eliminationFactor;

                for (int k = col + 1; k < size; ++k) {
                    matrix[pivotIndices[row]][k] -=
                        eliminationFactor * matrix[pivotIndices[col]][k];
                }
            }
        }
    }
}