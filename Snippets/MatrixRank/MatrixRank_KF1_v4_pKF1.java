package com.thealgorithms.var1;

import static com.thealgorithms.var1.utils.MatrixUtil.validateInputMatrix;

/**
 * Utility class for calculating the rank of a matrix.
 */
public final class MatrixRankCalculator {

    private MatrixRankCalculator() {
    }

    private static final double EPSILON = 1e-10;

    /**
     * Calculates the rank of the given matrix.
     *
     * @param matrix the input matrix
     * @return the rank of the matrix
     */
    public static int calculateRank(double[][] matrix) {
        validateInputMatrix(matrix);

        int rowCount = matrix.length;
        int columnCount = matrix[0].length;
        int rank = 0;

        boolean[] isRowUsed = new boolean[rowCount];

        double[][] workingMatrix = copyMatrix(matrix);

        for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
            int pivotRowIndex = findPivotRow(workingMatrix, isRowUsed, columnIndex);
            if (pivotRowIndex != rowCount) {
                ++rank;
                isRowUsed[pivotRowIndex] = true;
                normalizePivotRow(workingMatrix, pivotRowIndex, columnIndex);
                eliminateColumnWithPivot(workingMatrix, pivotRowIndex, columnIndex);
            }
        }
        return rank;
    }

    private static boolean isApproximatelyZero(double value) {
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

    private static int findPivotRow(double[][] matrix, boolean[] isRowUsed, int columnIndex) {
        int rowCount = matrix.length;
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            if (!isRowUsed[rowIndex] && !isApproximatelyZero(matrix[rowIndex][columnIndex])) {
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
            if (rowIndex != pivotRowIndex && !isApproximatelyZero(matrix[rowIndex][pivotColumnIndex])) {
                double factor = matrix[rowIndex][pivotColumnIndex];
                for (int columnIndex = pivotColumnIndex + 1; columnIndex < columnCount; ++columnIndex) {
                    matrix[rowIndex][columnIndex] -= matrix[pivotRowIndex][columnIndex] * factor;
                }
            }
        }
    }
}