package com.thealgorithms.matrix;

public final class InverseOfMatrix {

    private InverseOfMatrix() {}

    public static double[][] invert(double[][] inputMatrix) {
        int dimension = inputMatrix.length;
        double[][] inverseMatrix = new double[dimension][dimension];
        double[][] identityMatrix = new double[dimension][dimension];
        int[] pivotRowIndices = new int[dimension];

        for (int i = 0; i < dimension; ++i) {
            identityMatrix[i][i] = 1;
        }

        performGaussianElimination(inputMatrix, pivotRowIndices);

        for (int pivotColumn = 0; pivotColumn < dimension - 1; ++pivotColumn) {
            for (int row = pivotColumn + 1; row < dimension; ++row) {
                for (int column = 0; column < dimension; ++column) {
                    identityMatrix[pivotRowIndices[row]][column] -=
                        inputMatrix[pivotRowIndices[row]][pivotColumn]
                            * identityMatrix[pivotRowIndices[pivotColumn]][column];
                }
            }
        }

        for (int column = 0; column < dimension; ++column) {
            inverseMatrix[dimension - 1][column] =
                identityMatrix[pivotRowIndices[dimension - 1]][column]
                    / inputMatrix[pivotRowIndices[dimension - 1]][dimension - 1];

            for (int row = dimension - 2; row >= 0; --row) {
                inverseMatrix[row][column] = identityMatrix[pivotRowIndices[row]][column];

                for (int k = row + 1; k < dimension; ++k) {
                    inverseMatrix[row][column] -=
                        inputMatrix[pivotRowIndices[row]][k] * inverseMatrix[k][column];
                }

                inverseMatrix[row][column] /= inputMatrix[pivotRowIndices[row]][row];
            }
        }

        return inverseMatrix;
    }

    private static void performGaussianElimination(double[][] matrix, int[] pivotRowIndices) {
        int dimension = pivotRowIndices.length;
        double[] rowScalingFactors = new double[dimension];

        for (int i = 0; i < dimension; ++i) {
            pivotRowIndices[i] = i;
        }

        for (int row = 0; row < dimension; ++row) {
            double maxRowAbsoluteValue = 0;
            for (int column = 0; column < dimension; ++column) {
                double absoluteValue = Math.abs(matrix[row][column]);
                if (absoluteValue > maxRowAbsoluteValue) {
                    maxRowAbsoluteValue = absoluteValue;
                }
            }
            rowScalingFactors[row] = maxRowAbsoluteValue;
        }

        for (int column = 0; column < dimension - 1; ++column) {
            double maxScaledRatio = 0;
            int pivotRow = column;

            for (int row = column; row < dimension; ++row) {
                double scaledRatio =
                    Math.abs(matrix[pivotRowIndices[row]][column])
                        / rowScalingFactors[pivotRowIndices[row]];
                if (scaledRatio > maxScaledRatio) {
                    maxScaledRatio = scaledRatio;
                    pivotRow = row;
                }
            }

            int tempIndex = pivotRowIndices[column];
            pivotRowIndices[column] = pivotRowIndices[pivotRow];
            pivotRowIndices[pivotRow] = tempIndex;

            for (int row = column + 1; row < dimension; ++row) {
                double eliminationFactor =
                    matrix[pivotRowIndices[row]][column]
                        / matrix[pivotRowIndices[column]][column];

                matrix[pivotRowIndices[row]][column] = eliminationFactor;

                for (int k = column + 1; k < dimension; ++k) {
                    matrix[pivotRowIndices[row]][k] -=
                        eliminationFactor * matrix[pivotRowIndices[column]][k];
                }
            }
        }
    }
}