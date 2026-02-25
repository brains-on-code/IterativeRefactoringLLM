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

        boolean[] isPivotRowUsed = new boolean[rowCount];

        double[][] workingMatrix = deepCopyMatrix(matrix);

        for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
            int pivotRowIndex = findPivotRowIndex(workingMatrix, isPivotRowUsed, columnIndex);
            if (pivotRowIndex != rowCount) {
                ++rank;
                isPivotRowUsed[pivotRowIndex] = true;
                normalizePivotRow(workingMatrix, pivotRowIndex, columnIndex);
                eliminateColumnUsingPivot(workingMatrix, pivotRowIndex, columnIndex);
            }
        }
        return rank;
    }

    private static boolean isEffectivelyZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    private static double[][] deepCopyMatrix(double[][] originalMatrix) {
        int rowCount = originalMatrix.length;
        int columnCount = originalMatrix[0].length;
        double[][] copiedMatrix = new double[rowCount][columnCount];
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            System.arraycopy(originalMatrix[rowIndex], 0, copiedMatrix[rowIndex], 0, columnCount);
        }
        return copiedMatrix;
    }

    private static int findPivotRowIndex(double[][] matrix, boolean[] isPivotRowUsed, int pivotColumnIndex) {
        int rowCount = matrix.length;
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            if (!isPivotRowUsed[rowIndex] && !isEffectivelyZero(matrix[rowIndex][pivotColumnIndex])) {
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

    private static void eliminateColumnUsingPivot(double[][] matrix, int pivotRowIndex, int pivotColumnIndex) {
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