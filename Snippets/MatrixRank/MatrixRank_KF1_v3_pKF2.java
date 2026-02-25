package com.thealgorithms.var1;

import static com.thealgorithms.var1.utils.MatrixUtil.validateInputMatrix;

/**
 * Utility class for matrix-related operations.
 */
public final class Class1 {

    /** Threshold below which values are treated as zero. */
    private static final double EPSILON = 1e-10;

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the rank of a matrix using Gaussian elimination.
     *
     * @param matrix the input matrix
     * @return the rank of the matrix
     * @throws IllegalArgumentException if the matrix is null, empty, or not rectangular
     */
    public static int method1(double[][] matrix) {
        validateInputMatrix(matrix);

        int rowCount = matrix.length;
        int colCount = matrix[0].length;
        int rank = 0;

        boolean[] pivotRowUsed = new boolean[rowCount];
        double[][] workingMatrix = deepCopy(matrix);

        for (int col = 0; col < colCount; col++) {
            int pivotRow = findPivotRow(workingMatrix, pivotRowUsed, col);
            if (pivotRow == rowCount) {
                // No pivot in this column.
                continue;
            }

            rank++;
            pivotRowUsed[pivotRow] = true;

            normalizePivotRow(workingMatrix, pivotRow, col);
            eliminateColumn(workingMatrix, pivotRow, col);
        }

        return rank;
    }

    /**
     * Returns {@code true} if the given value is effectively zero within {@link #EPSILON}.
     */
    private static boolean isZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    /**
     * Returns a deep copy of the given matrix.
     */
    private static double[][] deepCopy(double[][] matrix) {
        int rowCount = matrix.length;
        int colCount = matrix[0].length;
        double[][] copy = new double[rowCount][colCount];

        for (int row = 0; row < rowCount; row++) {
            System.arraycopy(matrix[row], 0, copy[row], 0, colCount);
        }

        return copy;
    }

    /**
     * Finds a pivot row for the given column that has not yet been used and
     * whose entry in that column is non-zero.
     *
     * @param matrix the working matrix
     * @param pivotRowUsed flags indicating which rows are already used as pivots
     * @param col the column index for which to find a pivot
     * @return the index of the pivot row, or {@code matrix.length} if none is found
     */
    private static int findPivotRow(double[][] matrix, boolean[] pivotRowUsed, int col) {
        int rowCount = matrix.length;

        for (int row = 0; row < rowCount; row++) {
            if (!pivotRowUsed[row] && !isZero(matrix[row][col])) {
                return row;
            }
        }

        return rowCount;
    }

    /**
     * Normalizes the pivot row so that the pivot element becomes 1.
     *
     * @param matrix the working matrix
     * @param pivotRow the row index of the pivot
     * @param pivotCol the column index of the pivot
     */
    private static void normalizePivotRow(double[][] matrix, int pivotRow, int pivotCol) {
        int colCount = matrix[0].length;
        double pivotValue = matrix[pivotRow][pivotCol];

        for (int col = pivotCol + 1; col < colCount; col++) {
            matrix[pivotRow][col] /= pivotValue;
        }
    }

    /**
     * Eliminates the pivot column from all other rows using the pivot row.
     *
     * @param matrix the working matrix
     * @param pivotRow the row index of the pivot
     * @param pivotCol the column index of the pivot
     */
    private static void eliminateColumn(double[][] matrix, int pivotRow, int pivotCol) {
        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        for (int row = 0; row < rowCount; row++) {
            if (row == pivotRow || isZero(matrix[row][pivotCol])) {
                continue;
            }

            double factor = matrix[row][pivotCol];
            for (int col = pivotCol + 1; col < colCount; col++) {
                matrix[row][col] -= matrix[pivotRow][col] * factor;
            }
        }
    }
}