package com.thealgorithms.matrix;

import static com.thealgorithms.matrix.utils.MatrixUtil.validateInputMatrix;

public final class MatrixRank {

    private static final double EPSILON = 1e-10;

    private MatrixRank() {
        // Utility class; prevent instantiation
    }

    public static int computeRank(double[][] matrix) {
        validateInputMatrix(matrix);

        final int rowCount = matrix.length;
        final int columnCount = matrix[0].length;

        int rank = 0;
        boolean[] isRowUsed = new boolean[rowCount];
        double[][] workingMatrix = deepCopy(matrix);

        for (int column = 0; column < columnCount; column++) {
            int pivotRowIndex = findPivotRowIndex(workingMatrix, isRowUsed, column);
            if (pivotRowIndex == rowCount) {
                continue;
            }

            rank++;
            isRowUsed[pivotRowIndex] = true;

            normalizePivotRow(workingMatrix, pivotRowIndex, column);
            eliminateColumnFromOtherRows(workingMatrix, pivotRowIndex, column);
        }

        return rank;
    }

    private static boolean isEffectivelyZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    private static double[][] deepCopy(double[][] matrix) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;
        double[][] copy = new double[rowCount][columnCount];

        for (int row = 0; row < rowCount; row++) {
            System.arraycopy(matrix[row], 0, copy[row], 0, columnCount);
        }

        return copy;
    }

    private static int findPivotRowIndex(double[][] matrix, boolean[] isRowUsed, int column) {
        int rowCount = matrix.length;

        for (int row = 0; row < rowCount; row++) {
            if (!isRowUsed[row] && !isEffectivelyZero(matrix[row][column])) {
                return row;
            }
        }

        return rowCount;
    }

    private static void normalizePivotRow(double[][] matrix, int pivotRowIndex, int pivotColumn) {
        int columnCount = matrix[0].length;
        double pivotValue = matrix[pivotRowIndex][pivotColumn];

        for (int column = pivotColumn + 1; column < columnCount; column++) {
            matrix[pivotRowIndex][column] /= pivotValue;
        }
    }

    private static void eliminateColumnFromOtherRows(double[][] matrix, int pivotRowIndex, int pivotColumn) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;

        for (int row = 0; row < rowCount; row++) {
            if (row == pivotRowIndex || isEffectivelyZero(matrix[row][pivotColumn])) {
                continue;
            }

            double factor = matrix[row][pivotColumn];
            for (int column = pivotColumn + 1; column < columnCount; column++) {
                matrix[row][column] -= matrix[pivotRowIndex][column] * factor;
            }
        }
    }
}