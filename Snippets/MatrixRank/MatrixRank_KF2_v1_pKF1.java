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

        boolean[] isRowUsedAsPivot = new boolean[rowCount];

        double[][] workingMatrix = copyMatrix(matrix);

        for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
            int pivotRowIndex = findPivotRowIndex(workingMatrix, isRowUsedAsPivot, columnIndex);
            if (pivotRowIndex != rowCount) {
                ++rank;
                isRowUsedAsPivot[pivotRowIndex] = true;
                normalizePivotRow(workingMatrix, pivotRowIndex, columnIndex);
                eliminateOtherRows(workingMatrix, pivotRowIndex, columnIndex);
            }
        }
        return rank;
    }

    private static boolean isEffectivelyZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    private static double[][] copyMatrix(double[][] matrix) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;
        double[][] copy = new double[rowCount][columnCount];
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            System.arraycopy(matrix[rowIndex], 0, copy[rowIndex], 0, columnCount);
        }
        return copy;
    }

    private static int findPivotRowIndex(double[][] matrix, boolean[] isRowUsedAsPivot, int columnIndex) {
        int rowCount = matrix.length;
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            if (!isRowUsedAsPivot[rowIndex] && !isEffectivelyZero(matrix[rowIndex][columnIndex])) {
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

    private static void eliminateOtherRows(double[][] matrix, int pivotRowIndex, int pivotColumnIndex) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            if (rowIndex != pivotRowIndex && !isEffectivelyZero(matrix[rowIndex][pivotColumnIndex])) {
                double factor = matrix[rowIndex][pivotColumnIndex];
                for (int columnIndex = pivotColumnIndex + 1; columnIndex < columnCount; ++columnIndex) {
                    matrix[rowIndex][columnIndex] -= matrix[pivotRowIndex][columnIndex] * factor;
                }
            }
        }
    }
}