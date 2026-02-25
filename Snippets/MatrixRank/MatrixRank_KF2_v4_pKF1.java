package com.thealgorithms.matrix;

import static com.thealgorithms.matrix.utils.MatrixUtil.validateInputMatrix;

public final class MatrixRank {

    private MatrixRank() {}

    private static final double EPSILON = 1e-10;

    public static int computeRank(double[][] matrix) {
        validateInputMatrix(matrix);

        int rowCount = matrix.length;
        int columnCount = matrix[0].length;
        int rank = 0;

        boolean[] pivotRowUsed = new boolean[rowCount];

        double[][] workingMatrix = copyMatrix(matrix);

        for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
            int pivotRowIndex = findPivotRow(workingMatrix, pivotRowUsed, columnIndex);
            if (pivotRowIndex != rowCount) {
                ++rank;
                pivotRowUsed[pivotRowIndex] = true;
                normalizePivotRow(workingMatrix, pivotRowIndex, columnIndex);
                eliminateColumnWithPivot(workingMatrix, pivotRowIndex, columnIndex);
            }
        }
        return rank;
    }

    private static boolean isEffectivelyZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    private static double[][] copyMatrix(double[][] sourceMatrix) {
        int rowCount = sourceMatrix.length;
        int columnCount = sourceMatrix[0].length;
        double[][] matrixCopy = new double[rowCount][columnCount];
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            System.arraycopy(sourceMatrix[rowIndex], 0, matrixCopy[rowIndex], 0, columnCount);
        }
        return matrixCopy;
    }

    private static int findPivotRow(double[][] matrix, boolean[] pivotRowUsed, int pivotColumnIndex) {
        int rowCount = matrix.length;
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            if (!pivotRowUsed[rowIndex] && !isEffectivelyZero(matrix[rowIndex][pivotColumnIndex])) {
                return rowIndex;
            }
        }
        return rowCount;
    }

    private static void normalizePivotRow(double[][] matrix, int pivotRowIndex, int pivotColumnIndex) {
        int columnCount = matrix[0].length;
        double pivotValue = matrix[pivotRowIndex][pivotColumnIndex];
        for (int columnIndex = pivotColumnIndex + 1; columnIndex < columnCount; ++columnIndex) {
            matrix[pivotRowIndex][columnIndex] /= pivotValue;
        }
    }

    private static void eliminateColumnWithPivot(double[][] matrix, int pivotRowIndex, int pivotColumnIndex) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            if (rowIndex != pivotRowIndex && !isEffectivelyZero(matrix[rowIndex][pivotColumnIndex])) {
                double eliminationFactor = matrix[rowIndex][pivotColumnIndex];
                for (int columnIndex = pivotColumnIndex + 1; columnIndex < columnCount; ++columnIndex) {
                    matrix[rowIndex][columnIndex] -= matrix[pivotRowIndex][columnIndex] * eliminationFactor;
                }
            }
        }
    }
}