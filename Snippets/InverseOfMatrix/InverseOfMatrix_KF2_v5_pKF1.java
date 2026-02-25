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

        for (int pivotCol = 0; pivotCol < size - 1; ++pivotCol) {
            for (int row = pivotCol + 1; row < size; ++row) {
                for (int col = 0; col < size; ++col) {
                    identity[pivotIndices[row]][col] -=
                        matrix[pivotIndices[row]][pivotCol]
                            * identity[pivotIndices[pivotCol]][col];
                }
            }
        }

        for (int col = 0; col < size; ++col) {
            inverse[size - 1][col] =
                identity[pivotIndices[size - 1]][col]
                    / matrix[pivotIndices[size - 1]][size - 1];

            for (int row = size - 2; row >= 0; --row) {
                inverse[row][col] = identity[pivotIndices[row]][col];

                for (int k = row + 1; k < size; ++k) {
                    inverse[row][col] -=
                        matrix[pivotIndices[row]][k] * inverse[k][col];
                }

                inverse[row][col] /= matrix[pivotIndices[row]][row];
            }
        }

        return inverse;
    }

    private static void performGaussianElimination(double[][] matrix, int[] pivotIndices) {
        int size = pivotIndices.length;
        double[] rowScaleFactors = new double[size];

        for (int i = 0; i < size; ++i) {
            pivotIndices[i] = i;
        }

        for (int row = 0; row < size; ++row) {
            double maxAbsInRow = 0;
            for (int col = 0; col < size; ++col) {
                double absValue = Math.abs(matrix[row][col]);
                if (absValue > maxAbsInRow) {
                    maxAbsInRow = absValue;
                }
            }
            rowScaleFactors[row] = maxAbsInRow;
        }

        for (int col = 0; col < size - 1; ++col) {
            double maxScaledRatio = 0;
            int pivotRow = col;

            for (int row = col; row < size; ++row) {
                double scaledRatio =
                    Math.abs(matrix[pivotIndices[row]][col])
                        / rowScaleFactors[pivotIndices[row]];
                if (scaledRatio > maxScaledRatio) {
                    maxScaledRatio = scaledRatio;
                    pivotRow = row;
                }
            }

            int tempIndex = pivotIndices[col];
            pivotIndices[col] = pivotIndices[pivotRow];
            pivotIndices[pivotRow] = tempIndex;

            for (int row = col + 1; row < size; ++row) {
                double eliminationFactor =
                    matrix[pivotIndices[row]][col]
                        / matrix[pivotIndices[col]][col];

                matrix[pivotIndices[row]][col] = eliminationFactor;

                for (int k = col + 1; k < size; ++k) {
                    matrix[pivotIndices[row]][k] -=
                        eliminationFactor * matrix[pivotIndices[col]][k];
                }
            }
        }
    }
}