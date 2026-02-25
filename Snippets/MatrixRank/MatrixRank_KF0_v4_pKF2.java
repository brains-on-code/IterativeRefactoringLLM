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
        // Prevent instantiation
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

        boolean[] pivotRowUsed = new boolean[numRows];
        double[][] workingCopy = deepCopy(matrix);

        for (int colIndex = 0; colIndex < numColumns; colIndex++) {
            int pivotRow = findPivotRow(workingCopy, pivotRowUsed, colIndex);
            if (pivotRow == numRows) {
                // No pivot in this column
                continue;
            }

            rank++;
            pivotRowUsed[pivotRow] = true;

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
     * Returns the index of the first unused row with a non-zero entry in the given column.
     *
     * @param matrix the working matrix
     * @param pivotRowUsed flags indicating which rows are already used as pivots
     * @param colIndex the column in which to search for a pivot
     * @return the index of the pivot row, or {@code matrix.length} if none is found
     */
    private static int findPivotRow(double[][] matrix, boolean[] pivotRowUsed, int colIndex) {
        int numRows = matrix.length;

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            if (!pivotRowUsed[rowIndex] && !isZero(matrix[rowIndex][colIndex])) {
                return rowIndex;
            }
        }

        return numRows;
    }

    /**
     * Scales the pivot row so that the pivot element becomes 1.
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