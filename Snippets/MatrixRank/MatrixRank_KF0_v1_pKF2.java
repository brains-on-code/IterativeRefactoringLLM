package com.thealgorithms.matrix;

import static com.thealgorithms.matrix.utils.MatrixUtil.validateInputMatrix;

/**
 * Utility class for computing the rank of a matrix.
 *
 * <p>The rank of a matrix is the maximum number of linearly independent rows or
 * columns. It is defined for any m x n matrix.</p>
 */
public final class MatrixRank {

    private MatrixRank() {
        // Utility class; prevent instantiation
    }

    private static final double EPSILON = 1e-10;

    /**
     * Computes the rank of the given matrix using a row-echelon style
     * Gaussian elimination.
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

        boolean[] rowUsedAsPivot = new boolean[numRows];
        double[][] workingCopy = deepCopy(matrix);

        for (int colIndex = 0; colIndex < numColumns; colIndex++) {
            int pivotRow = findPivotRow(workingCopy, rowUsedAsPivot, colIndex);
            if (pivotRow == numRows) {
                continue; // no pivot in this column
            }

            rank++;
            rowUsedAsPivot[pivotRow] = true;

            normalizePivotRow(workingCopy, pivotRow, colIndex);
            eliminateOtherRows(workingCopy, pivotRow, colIndex);
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
     * Finds a pivot row for the given column.
     *
     * <p>A pivot row is the first unused row (from top to bottom) whose entry
     * in the given column is non-zero.</p>
     *
     * @param matrix the working matrix
     * @param rowUsedAsPivot flags indicating which rows are already used as pivots
     * @param colIndex the column in which to search for a pivot
     * @return the index of the pivot row, or {@code matrix.length} if none is found
     */
    private static int findPivotRow(double[][] matrix, boolean[] rowUsedAsPivot, int colIndex) {
        int numRows = matrix.length;

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            if (!rowUsedAsPivot[rowIndex] && !isZero(matrix[rowIndex][colIndex])) {
                return rowIndex;
            }
        }

        return numRows;
    }

    /**
     * Normalizes the pivot row so that the pivot element becomes 1.
     *
     * <p>Only entries to the right of the pivot are scaled, since earlier
     * columns are not needed for rank computation.</p>
     *
     * @param matrix the working matrix
     * @param pivotRow the index of the pivot row
     * @param colIndex the index of the pivot column
     */
    private static void normalizePivotRow(double[][] matrix, int pivotRow, int colIndex) {
        int numColumns = matrix[0].length;
        double pivotValue = matrix[pivotRow][colIndex];

        for (int col = colIndex + 1; col < numColumns; col++) {
            matrix[pivotRow][col] /= pivotValue;
        }
    }

    /**
     * Eliminates the pivot column from all non-pivot rows.
     *
     * <p>For each other row, subtracts a suitable multiple of the pivot row so
     * that the entry in the pivot column becomes zero.</p>
     *
     * @param matrix the working matrix
     * @param pivotRow the index of the pivot row
     * @param colIndex the index of the pivot column
     */
    private static void eliminateOtherRows(double[][] matrix, int pivotRow, int colIndex) {
        int numRows = matrix.length;
        int numColumns = matrix[0].length;

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            if (rowIndex == pivotRow || isZero(matrix[rowIndex][colIndex])) {
                continue;
            }

            double factor = matrix[rowIndex][colIndex];
            for (int col = colIndex + 1; col < numColumns; col++) {
                matrix[rowIndex][col] -= matrix[pivotRow][col] * factor;
            }
        }
    }
}