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

        double[][] workingMatrix = copyMatrix(matrix);

        for (int col = 0; col < columnCount; ++col) {
            int pivotRow = findPivotRow(workingMatrix, isPivotRowUsed, col);
            if (pivotRow != rowCount) {
                ++rank;
                isPivotRowUsed[pivotRow] = true;
                normalizePivotRow(workingMatrix, pivotRow, col);
                eliminateColumnWithPivot(workingMatrix, pivotRow, col);
            }
        }
        return rank;
    }

    private static boolean isEffectivelyZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    private static double[][] copyMatrix(double[][] source) {
        int rowCount = source.length;
        int columnCount = source[0].length;
        double[][] copy = new double[rowCount][columnCount];
        for (int row = 0; row < rowCount; ++row) {
            System.arraycopy(source[row], 0, copy[row], 0, columnCount);
        }
        return copy;
    }

    private static int findPivotRow(double[][] matrix, boolean[] isPivotRowUsed, int pivotColumn) {
        int rowCount = matrix.length;
        for (int row = 0; row < rowCount; ++row) {
            if (!isPivotRowUsed[row] && !isEffectivelyZero(matrix[row][pivotColumn])) {
                return row;
            }
        }
        return rowCount;
    }

    private static void normalizePivotRow(double[][] matrix, int pivotRow, int pivotColumn) {
        int columnCount = matrix[0].length;
        double pivotValue = matrix[pivotRow][pivotColumn];
        for (int col = pivotColumn + 1; col < columnCount; ++col) {
            matrix[pivotRow][col] /= pivotValue;
        }
    }

    private static void eliminateColumnWithPivot(double[][] matrix, int pivotRow, int pivotColumn) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;
        for (int row = 0; row < rowCount; ++row) {
            if (row != pivotRow && !isEffectivelyZero(matrix[row][pivotColumn])) {
                double factor = matrix[row][pivotColumn];
                for (int col = pivotColumn + 1; col < columnCount; ++col) {
                    matrix[row][col] -= matrix[pivotRow][col] * factor;
                }
            }
        }
    }
}