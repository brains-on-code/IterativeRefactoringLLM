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

        for (int pivotRow = 0; pivotRow < dimension - 1; ++pivotRow) {
            for (int row = pivotRow + 1; row < dimension; ++row) {
                for (int column = 0; column < dimension; ++column) {
                    identityMatrix[pivotRowIndices[row]][column] -=
                        inputMatrix[pivotRowIndices[row]][pivotRow]
                            * identityMatrix[pivotRowIndices[pivotRow]][column];
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
            double maxRowElement = 0;
            for (int column = 0; column < dimension; ++column) {
                double absoluteElement = Math.abs(matrix[row][column]);
                if (absoluteElement > maxRowElement) {
                    maxRowElement = absoluteElement;
                }
            }
            rowScalingFactors[row] = maxRowElement;
        }

        for (int column = 0; column < dimension - 1; ++column) {
            double maxScaledPivotRatio = 0;
            int pivotRowWithMaxRatio = column;

            for (int row = column; row < dimension; ++row) {
                double scaledPivotRatio =
                    Math.abs(matrix[pivotRowIndices[row]][column])
                        / rowScalingFactors[pivotRowIndices[row]];
                if (scaledPivotRatio > maxScaledPivotRatio) {
                    maxScaledPivotRatio = scaledPivotRatio;
                    pivotRowWithMaxRatio = row;
                }
            }

            int tempIndex = pivotRowIndices[column];
            pivotRowIndices[column] = pivotRowIndices[pivotRowWithMaxRatio];
            pivotRowIndices[pivotRowWithMaxRatio] = tempIndex;

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