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
        boolean[] usedRows = new boolean[rowCount];
        double[][] workingMatrix = copyMatrix(matrix);

        for (int col = 0; col < columnCount; col++) {
            int pivotRow = findPivotRow(workingMatrix, usedRows, col);
            if (pivotRow == rowCount) {
                continue;
            }

            rank++;
            usedRows[pivotRow] = true;

            normalizePivotRow(workingMatrix, pivotRow, col);
            eliminateColumn(workingMatrix, pivotRow, col);
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

        for (int row = 0; row < rowCount; row++) {
            System.arraycopy(matrix[row], 0, copy[row], 0, columnCount);
        }

        return copy;
    }

    private static int findPivotRow(double[][] matrix, boolean[] usedRows, int col) {
        int rowCount = matrix.length;

        for (int row = 0; row < rowCount; row++) {
            if (!usedRows[row] && !isEffectivelyZero(matrix[row][col])) {
                return row;
            }
        }

        return rowCount;
    }

    private static void normalizePivotRow(double[][] matrix, int pivotRow, int pivotCol) {
        int columnCount = matrix[0].length;
        double pivotValue = matrix[pivotRow][pivotCol];

        for (int col = pivotCol + 1; col < columnCount; col++) {
            matrix[pivotRow][col] /= pivotValue;
        }
    }

    private static void eliminateColumn(double[][] matrix, int pivotRow, int pivotCol) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;

        for (int row = 0; row < rowCount; row++) {
            if (row == pivotRow || isEffectivelyZero(matrix[row][pivotCol])) {
                continue;
            }

            double factor = matrix[row][pivotCol];
            for (int col = pivotCol + 1; col < columnCount; col++) {
                matrix[row][col] -= matrix[pivotRow][col] * factor;
            }
        }
    }
}