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

    private MatrixRank() {}

    private static final double EPSILON = 1e-10;

    /**
     * Computes the rank of the input matrix.
     *
     * @param matrix The input matrix
     * @return The rank of the input matrix
     */
    public static int computeRank(double[][] matrix) {
        validateInputMatrix(matrix);

        int rowCount = matrix.length;
        int columnCount = matrix[0].length;
        int rank = 0;

        boolean[] isPivotRowUsed = new boolean[rowCount];

        double[][] workingMatrix = copyMatrix(matrix);

        for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
            int pivotRowIndex = findPivotRowIndex(workingMatrix, isPivotRowUsed, columnIndex);
            if (pivotRowIndex != rowCount) {
                ++rank;
                isPivotRowUsed[pivotRowIndex] = true;
                normalizePivotRow(workingMatrix, pivotRowIndex, columnIndex);
                eliminateColumnUsingPivotRow(workingMatrix, pivotRowIndex, columnIndex);
            }
        }
        return rank;
    }

    private static boolean isEffectivelyZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    private static double[][] copyMatrix(double[][] sourceMatrix) {
        int rowCount = sourceMatrix.length;
        int columnCount = sourceMatrix[0].length;
        double[][] matrixCopy = new double[rowCount][columnCount];
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            System.arraycopy(sourceMatrix[rowIndex], 0, matrixCopy[rowIndex], 0, columnCount);
        }
        return matrixCopy;
    }

    /**
     * The pivot row is the row in the matrix that is used to eliminate other rows and reduce the matrix to its row echelon form.
     * The pivot row is selected as the first row (from top to bottom) where the value in the current column (the pivot column) is not zero.
     * This row is then used to "eliminate" other rows, by subtracting multiples of the pivot row from them, so that all other entries in the pivot column become zero.
     * This process is repeated for each column, each time selecting a new pivot row, until the matrix is in row echelon form.
     * The number of pivot rows (rows with a leading entry, or pivot) then gives the rank of the matrix.
     *
     * @param matrix The input matrix
     * @param isPivotRowUsed An array indicating which rows have been used as pivot rows
     * @param columnIndex The column index
     * @return The pivot row index, or the number of rows if no suitable pivot row was found
     */
    private static int findPivotRowIndex(double[][] matrix, boolean[] isPivotRowUsed, int columnIndex) {
        int rowCount = matrix.length;
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            if (!isPivotRowUsed[rowIndex] && !isEffectivelyZero(matrix[rowIndex][columnIndex])) {
                return rowIndex;
            }
        }
        return rowCount;
    }

    /**
     * This method divides all values in the pivot row by the value in the given column.
     * This ensures that the pivot value itself will be 1, which simplifies further calculations.
     *
     * @param matrix The input matrix
     * @param pivotRowIndex The pivot row index
     * @param columnIndex The column index
     */
    private static void normalizePivotRow(double[][] matrix, int pivotRowIndex, int columnIndex) {
        int columnCount = matrix[0].length;
        double pivotValue = matrix[pivotRowIndex][columnIndex];
        for (int targetColumnIndex = columnIndex + 1; targetColumnIndex < columnCount; ++targetColumnIndex) {
            matrix[pivotRowIndex][targetColumnIndex] /= pivotValue;
        }
    }

    /**
     * This method subtracts multiples of the pivot row from all other rows,
     * so that all values in the given column of other rows will be zero.
     * This is a key step in reducing the matrix to row echelon form.
     *
     * @param matrix The input matrix
     * @param pivotRowIndex The pivot row index
     * @param columnIndex The column index
     */
    private static void eliminateColumnUsingPivotRow(double[][] matrix, int pivotRowIndex, int columnIndex) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            if (rowIndex == pivotRowIndex || isEffectivelyZero(matrix[rowIndex][columnIndex])) {
                continue;
            }
            double eliminationFactor = matrix[rowIndex][columnIndex];
            for (int targetColumnIndex = columnIndex + 1; targetColumnIndex < columnCount; ++targetColumnIndex) {
                matrix[rowIndex][targetColumnIndex] -= matrix[pivotRowIndex][targetColumnIndex] * eliminationFactor;
            }
        }
    }
}