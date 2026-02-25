package com.thealgorithms.matrix;

import static com.thealgorithms.matrix.utils.MatrixUtil.validateInputMatrix;

/**
 * This class provides a method to compute the rank of a matrix.
 * In linear algebra, the rank of a matrix is the maximum number of linearly independent rows or columns in the matrix.
 * For example, consider the following 3x3 matrix:
 * 1 2 3
 * 2 4 6
 * 3 6 9
 * Despite having 3 rows and 3 columns, this matrix only has a rank of 1 because all rows (and columns) are multiples of each other.
 * It's a fundamental concept that gives key insights into the structure of the matrix.
 * It's important to note that the rank is not only defined for square matrices but for any m x n matrix.
 *
 * @author Anup Omkar
 */
public final class MatrixRank {

    private static final double EPSILON = 1e-10;

    private MatrixRank() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the rank of the input matrix.
     *
     * @param matrix the input matrix
     * @return the rank of the input matrix
     */
    public static int computeRank(double[][] matrix) {
        validateInputMatrix(matrix);

        int numRows = matrix.length;
        int numColumns = matrix[0].length;
        int rank = 0;

        boolean[] usedAsPivotRow = new boolean[numRows];
        double[][] workingMatrix = deepCopy(matrix);

        for (int col = 0; col < numColumns; col++) {
            int pivotRow = findPivotRow(workingMatrix, usedAsPivotRow, col);
            if (pivotRow == numRows) {
                continue; // no pivot in this column
            }

            rank++;
            usedAsPivotRow[pivotRow] = true;

            normalizePivotRow(workingMatrix, pivotRow, col);
            eliminateRows(workingMatrix, pivotRow, col);
        }

        return rank;
    }

    private static boolean isZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    private static double[][] deepCopy(double[][] matrix) {
        int numRows = matrix.length;
        int numColumns = matrix[0].length;
        double[][] copy = new double[numRows][numColumns];

        for (int row = 0; row < numRows; row++) {
            System.arraycopy(matrix[row], 0, copy[row], 0, numColumns);
        }

        return copy;
    }

    /**
     * Finds the pivot row for a given column.
     *
     * @param matrix       the working matrix
     * @param usedAsPivot  flags indicating which rows are already used as pivot rows
     * @param col          the column index
     * @return the pivot row index, or the number of rows if no suitable pivot row was found
     */
    private static int findPivotRow(double[][] matrix, boolean[] usedAsPivot, int col) {
        int numRows = matrix.length;

        for (int row = 0; row < numRows; row++) {
            if (!usedAsPivot[row] && !isZero(matrix[row][col])) {
                return row;
            }
        }

        return numRows;
    }

    /**
     * Normalizes the pivot row so that the pivot element becomes 1.
     *
     * @param matrix   the working matrix
     * @param pivotRow the pivot row index
     * @param col      the pivot column index
     */
    private static void normalizePivotRow(double[][] matrix, int pivotRow, int col) {
        int numColumns = matrix[0].length;
        double pivotValue = matrix[pivotRow][col];

        if (isZero(pivotValue)) {
            return;
        }

        for (int j = col + 1; j < numColumns; j++) {
            matrix[pivotRow][j] /= pivotValue;
        }
    }

    /**
     * Eliminates the current column from all rows except the pivot row.
     *
     * @param matrix   the working matrix
     * @param pivotRow the pivot row index
     * @param col      the pivot column index
     */
    private static void eliminateRows(double[][] matrix, int pivotRow, int col) {
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