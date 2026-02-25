package com.thealgorithms.matrix;

import static com.thealgorithms.matrix.utils.MatrixUtil.validateInputMatrix;

/**
 * Utility class to compute the rank of a matrix.
 */
public final class MatrixRank {

    private static final double EPSILON = 1e-10;

    private MatrixRank() {
        // Prevent instantiation
    }

    /**
     * Computes the rank of the input matrix.
     *
     * @param matrix the input matrix
     * @return the rank of the input matrix
     */
    public static int computeRank(double[][] matrix) {
        validateInputMatrix(matrix);

        final int rowCount = matrix.length;
        final int columnCount = matrix[0].length;

        int rank = 0;
        boolean[] usedAsPivotRow = new boolean[rowCount];
        double[][] workingMatrix = copyMatrix(matrix);

        for (int column = 0; column < columnCount; column++) {
            int pivotRow = findPivotRow(workingMatrix, usedAsPivotRow, column);
            if (pivotRow == rowCount) {
                continue;
            }

            rank++;
            usedAsPivotRow[pivotRow] = true;

            normalizePivotRow(workingMatrix, pivotRow, column);
            eliminateColumnFromOtherRows(workingMatrix, pivotRow, column);
        }

        return rank;
    }

    private static boolean isZero(double value) {
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

    /**
     * Finds the pivot row for a given column.
     *
     * @param matrix        the working matrix
     * @param usedAsPivotRow flags indicating which rows are already used as pivot rows
     * @param column        the column index
     * @return the pivot row index, or the number of rows if no suitable pivot row was found
     */
    private static int findPivotRow(double[][] matrix, boolean[] usedAsPivotRow, int column) {
        int rowCount = matrix.length;

        for (int row = 0; row < rowCount; row++) {
            if (!usedAsPivotRow[row] && !isZero(matrix[row][column])) {
                return row;
            }
        }

        return rowCount;
    }

    /**
     * Normalizes the pivot row so that the pivot element becomes 1.
     *
     * @param matrix   the working matrix
     * @param pivotRow the pivot row index
     * @param column   the pivot column index
     */
    private static void normalizePivotRow(double[][] matrix, int pivotRow, int column) {
        int columnCount = matrix[0].length;
        double pivotValue = matrix[pivotRow][column];

        if (isZero(pivotValue)) {
            return;
        }

        for (int j = column; j < columnCount; j++) {
            matrix[pivotRow][j] /= pivotValue;
        }
    }

    /**
     * Eliminates the current column from all rows except the pivot row.
     *
     * @param matrix   the working matrix
     * @param pivotRow the pivot row index
     * @param column   the pivot column index
     */
    private static void eliminateColumnFromOtherRows(double[][] matrix, int pivotRow, int column) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;

        for (int row = 0; row < rowCount; row++) {
            if (row == pivotRow) {
                continue;
            }

            double factor = matrix[row][column];
            if (isZero(factor)) {
                continue;
            }

            for (int j = column; j < columnCount; j++) {
                matrix[row][j] -= matrix[pivotRow][j] * factor;
            }
        }
    }
}