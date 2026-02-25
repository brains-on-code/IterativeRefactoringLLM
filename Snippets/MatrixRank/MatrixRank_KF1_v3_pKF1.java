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

        boolean[] usedRows = new boolean[rowCount];

        double[][] workingMatrix = deepCopyMatrix(matrix);

        for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
            int pivotRowIndex = findPivotRowIndex(workingMatrix, usedRows, columnIndex);
            if (pivotRowIndex != rowCount) {
                ++rank;
                usedRows[pivotRowIndex] = true;
                normalizePivotRow(workingMatrix, pivotRowIndex, columnIndex);
                eliminateColumnUsingPivot(workingMatrix, pivotRowIndex, columnIndex);
            }
        }
        return rank;
    }

    private static boolean isApproximatelyZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    private static double[][] deepCopyMatrix(double[][] originalMatrix) {
        int rowCount = originalMatrix.length;
        int columnCount = originalMatrix[0].length;
        double[][] matrixCopy = new double[rowCount][columnCount];
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            System.arraycopy(originalMatrix[rowIndex], 0, matrixCopy[rowIndex], 0, columnCount);
        }
        return matrixCopy;
    }

    private static int findPivotRowIndex(double[][] matrix, boolean[] usedRows, int columnIndex) {
        int rowCount = matrix.length;
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            if (!usedRows[rowIndex] && !isApproximatelyZero(matrix[rowIndex][columnIndex])) {
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
            if (rowIndex != pivotRowIndex && !isApproximatelyZero(matrix[rowIndex][pivotColumnIndex])) {
                double eliminationFactor = matrix[rowIndex][pivotColumnIndex];
                for (int columnIndex = pivotColumnIndex + 1; columnIndex < columnCount; ++columnIndex) {
                    matrix[rowIndex][columnIndex] -= matrix[pivotRowIndex][columnIndex] * eliminationFactor;
                }
            }
        }
    }
}