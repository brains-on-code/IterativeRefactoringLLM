package com.thealgorithms.matrix;

import static com.thealgorithms.matrix.utils.MatrixUtil.validateInputMatrix;

public final class MatrixRank {

    private static final double EPSILON = 1e-10;

    private MatrixRank() {
        // Prevent instantiation
    }

    /**
     * Computes the rank of a matrix using Gaussian elimination.
     *
     * @param matrix the input matrix
     * @return the rank of the matrix
     */
    public static int computeRank(double[][] matrix) {
        validateInputMatrix(matrix);

        int numRows = matrix.length;
        int numColumns = matrix[0].length;
        int rank = 0;

        boolean[] pivotRows = new boolean[numRows];
        double[][] workingMatrix = deepCopy(matrix);

        for (int col = 0; col < numColumns; col++) {
            int pivotRow = findPivotRow(workingMatrix, pivotRows, col);
            if (pivotRow == numRows) {
                continue;
            }

            rank++;
            pivotRows[pivotRow] = true;

            normalizePivotRow(workingMatrix, pivotRow, col);
            eliminateColumnFromOtherRows(workingMatrix, pivotRow, col);
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
     * Returns the index of the first non-pivot row with a non-zero entry in the
     * given column, or matrix.length if none is found.
     */
    private static int findPivotRow(double[][] matrix, boolean[] pivotRows, int col) {
        int numRows = matrix.length;

        for (int row = 0; row < numRows; row++) {
            if (!pivotRows[row] && !isZero(matrix[row][col])) {
                return row;
            }
        }

        return numRows;
    }

    /**
     * Scales the pivot row so that the pivot element in the given column becomes 1.
     */
    private static void normalizePivotRow(double[][] matrix, int pivotRow, int col) {
        int numColumns = matrix[0].length;
        double pivotValue = matrix[pivotRow][col];

        for (int j = col + 1; j < numColumns; j++) {
            matrix[pivotRow][j] /= pivotValue;
        }
    }

    /**
     * Eliminates the pivot column from all rows except the pivot row.
     */
    private static void eliminateColumnFromOtherRows(double[][] matrix, int pivotRow, int col) {
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