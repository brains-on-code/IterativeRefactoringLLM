package com.thealgorithms.matrix;

import static com.thealgorithms.matrix.utils.MatrixUtil.validateInputMatrix;

/**
 * Utility class for computing the rank of a matrix.
 *
 * <p>The rank of a matrix is the maximum number of linearly independent rows or
 * columns. It is defined for any m x n matrix.</p>
 */
public final class MatrixRank {

    private static final double EPSILON = 1e-10;

    private MatrixRank() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the rank of the given matrix using Gaussian elimination.
     *
     * @param matrix the input matrix
     * @return the rank of the matrix
     * @throws IllegalArgumentException if the matrix is null or not rectangular
     */
    public static int computeRank(double[][] matrix) {
        validateInputMatrix(matrix);

        int numRows = matrix.length;
        int numColumns = matrix[0].length;
        int rank = 0;

        boolean[] isPivotRow = new boolean[numRows];
        double[][] workingMatrix = deepCopy(matrix);

        for (int colIndex = 0; colIndex < numColumns; colIndex++) {
            int pivotRowIndex = findPivotRow(workingMatrix, isPivotRow, colIndex);
            if (pivotRowIndex == numRows) {
                continue; // No pivot in this column
            }

            rank++;
            isPivotRow[pivotRowIndex] = true;

            normalizePivotRow(workingMatrix, pivotRowIndex, colIndex);
            eliminateOtherRows(workingMatrix, pivotRowIndex, colIndex);
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

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            System.arraycopy(matrix[rowIndex], 0, copy[rowIndex], 0, numColumns);
        }

        return copy;
    }

    /**
     * Finds the first unused row with a non-zero entry in the specified column.
     *
     * @param matrix the working matrix
     * @param isPivotRow flags indicating which rows are already used as pivots
     * @param colIndex the column in which to search for a pivot
     * @return the index of the pivot row, or {@code matrix.length} if none is found
     */
    private static int findPivotRow(double[][] matrix, boolean[] isPivotRow, int colIndex) {
        int numRows = matrix.length;

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            if (!isPivotRow[rowIndex] && !isZero(matrix[rowIndex][colIndex])) {
                return rowIndex;
            }
        }

        return numRows;
    }

    /**
     * Scales the pivot row so that the pivot element becomes 1.
     *
     * @param matrix the working matrix
     * @param pivotRowIndex the index of the pivot row
     * @param colIndex the index of the pivot column
     */
    private static void normalizePivotRow(double[][] matrix, int pivotRowIndex, int colIndex) {
        int numColumns = matrix[0].length;
        double pivotValue = matrix[pivotRowIndex][colIndex];

        for (int col = colIndex + 1; col < numColumns; col++) {
            matrix[pivotRowIndex][col] /= pivotValue;
        }
    }

    /**
     * Eliminates the pivot column from all non-pivot rows.
     *
     * @param matrix the working matrix
     * @param pivotRowIndex the index of the pivot row
     * @param colIndex the index of the pivot column
     */
    private static void eliminateOtherRows(double[][] matrix, int pivotRowIndex, int colIndex) {
        int numRows = matrix.length;
        int numColumns = matrix[0].length;

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            if (rowIndex == pivotRowIndex || isZero(matrix[rowIndex][colIndex])) {
                continue;
            }

            double factor = matrix[rowIndex][colIndex];
            for (int col = colIndex + 1; col < numColumns; col++) {
                matrix[rowIndex][col] -= matrix[pivotRowIndex][col] * factor;
            }
        }
    }
}