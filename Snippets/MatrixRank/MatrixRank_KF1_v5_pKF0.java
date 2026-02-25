package com.thealgorithms.var1;

import static com.thealgorithms.var1.utils.MatrixUtil.validateInputMatrix;

/**
 * Utility class for matrix rank calculation.
 */
public final class Class1 {

    private static final double EPSILON = 1e-10;

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the rank of a matrix using a form of Gaussian elimination.
     *
     * @param matrix input matrix
     * @return rank of the matrix
     */
    public static int method1(double[][] matrix) {
        validateInputMatrix(matrix);

        int rowCount = matrix.length;
        int colCount = matrix[0].length;
        int rank = 0;

        boolean[] isRowUsed = new boolean[rowCount];
        double[][] workingMatrix = deepCopy(matrix);

        for (int col = 0; col < colCount; col++) {
            int pivotRow = findPivotRow(workingMatrix, isRowUsed, col);
            if (pivotRow == -1) {
                continue;
            }

            rank++;
            isRowUsed[pivotRow] = true;

            normalizePivotRow(workingMatrix, pivotRow, col);
            eliminateColumn(workingMatrix, pivotRow, col);
        }

        return rank;
    }

    private static boolean isEffectivelyZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    private static double[][] deepCopy(double[][] matrix) {
        int rowCount = matrix.length;
        int colCount = matrix[0].length;
        double[][] copy = new double[rowCount][colCount];

        for (int row = 0; row < rowCount; row++) {
            System.arraycopy(matrix[row], 0, copy[row], 0, colCount);
        }

        return copy;
    }

    private static int findPivotRow(double[][] matrix, boolean[] isRowUsed, int col) {
        int rowCount = matrix.length;

        for (int row = 0; row < rowCount; row++) {
            if (!isRowUsed[row] && !isEffectivelyZero(matrix[row][col])) {
                return row;
            }
        }

        return -1;
    }

    private static void normalizePivotRow(double[][] matrix, int pivotRow, int col) {
        int colCount = matrix[0].length;
        double pivotValue = matrix[pivotRow][col];

        if (isEffectivelyZero(pivotValue)) {
            return;
        }

        for (int j = col + 1; j < colCount; j++) {
            matrix[pivotRow][j] /= pivotValue;
        }
    }

    private static void eliminateColumn(double[][] matrix, int pivotRow, int col) {
        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        for (int row = 0; row < rowCount; row++) {
            if (row == pivotRow || isEffectivelyZero(matrix[row][col])) {
                continue;
            }

            double factor = matrix[row][col];
            for (int j = col + 1; j < colCount; j++) {
                matrix[row][j] -= matrix[pivotRow][j] * factor;
            }
        }
    }
}