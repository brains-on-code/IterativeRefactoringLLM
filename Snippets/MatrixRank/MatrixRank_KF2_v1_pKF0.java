package com.thealgorithms.matrix;

import static com.thealgorithms.matrix.utils.MatrixUtil.validateInputMatrix;

public final class MatrixRank {

    private static final double EPSILON = 1e-10;

    private MatrixRank() {
        // Utility class; prevent instantiation
    }

    public static int computeRank(double[][] matrix) {
        validateInputMatrix(matrix);

        int numRows = matrix.length;
        int numColumns = matrix[0].length;
        int rank = 0;

        boolean[] usedRow = new boolean[numRows];
        double[][] workingMatrix = copyMatrix(matrix);

        for (int col = 0; col < numColumns; col++) {
            int pivotRow = findPivotRow(workingMatrix, usedRow, col);
            if (pivotRow == numRows) {
                continue;
            }

            rank++;
            usedRow[pivotRow] = true;

            normalizePivotRow(workingMatrix, pivotRow, col);
            eliminateOtherRows(workingMatrix, pivotRow, col);
        }

        return rank;
    }

    private static boolean isZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    private static double[][] copyMatrix(double[][] matrix) {
        int numRows = matrix.length;
        int numColumns = matrix[0].length;
        double[][] copy = new double[numRows][numColumns];

        for (int row = 0; row < numRows; row++) {
            System.arraycopy(matrix[row], 0, copy[row], 0, numColumns);
        }

        return copy;
    }

    private static int findPivotRow(double[][] matrix, boolean[] usedRow, int col) {
        int numRows = matrix.length;

        for (int row = 0; row < numRows; row++) {
            if (!usedRow[row] && !isZero(matrix[row][col])) {
                return row;
            }
        }

        return numRows;
    }

    private static void normalizePivotRow(double[][] matrix, int pivotRow, int col) {
        int numColumns = matrix[0].length;
        double pivotValue = matrix[pivotRow][col];

        for (int j = col + 1; j < numColumns; j++) {
            matrix[pivotRow][j] /= pivotValue;
        }
    }

    private static void eliminateOtherRows(double[][] matrix, int pivotRow, int col) {
        int numRows = matrix.length;
        int numColumns = matrix[0].length;

        for (int row = 0; row < numRows; row++) {
            if (row == pivotRow || isZero(matrix[row][col])) {
                continue;
            }

            double factor = matrix[row][col];
            for (int j = col + 1; j < numColumns; j++) {
                matrix[row][j] -= matrix[pivotRow][j] * factor;
            }
        }
    }
}