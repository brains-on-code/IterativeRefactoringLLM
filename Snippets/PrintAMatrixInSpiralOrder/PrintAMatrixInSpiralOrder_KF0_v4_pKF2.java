package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {

    /**
     * Returns the elements of a matrix in spiral order.
     *
     * @param matrix the matrix to traverse
     * @param rows   number of rows in the matrix
     * @param cols   number of columns in the matrix
     * @return a list of matrix elements in spiral order
     * @throws IllegalArgumentException if the matrix dimensions are invalid
     */
    public List<Integer> print(int[][] matrix, int rows, int cols) {
        validateInput(matrix, rows, cols);

        List<Integer> result = new ArrayList<>(rows * cols);

        int top = 0;
        int bottom = rows - 1;
        int left = 0;
        int right = cols - 1;

        while (top <= bottom && left <= right) {
            traverseTopRow(matrix, result, top, left, right);
            top++;

            traverseRightColumn(matrix, result, right, top, bottom);
            right--;

            if (top <= bottom) {
                traverseBottomRow(matrix, result, bottom, left, right);
                bottom--;
            }

            if (left <= right) {
                traverseLeftColumn(matrix, result, left, top, bottom);
                left++;
            }
        }

        return result;
    }

    private void validateInput(int[][] matrix, int rows, int cols) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix must not be null.");
        }
        if (rows < 0 || cols < 0) {
            throw new IllegalArgumentException("Row and column counts must be non-negative.");
        }
        if (rows == 0 || cols == 0) {
            return;
        }
        if (matrix.length < rows) {
            throw new IllegalArgumentException("Matrix has fewer rows than specified.");
        }
        for (int i = 0; i < rows; i++) {
            if (matrix[i] == null || matrix[i].length < cols) {
                throw new IllegalArgumentException(
                    "Matrix row " + i + " has fewer columns than specified."
                );
            }
        }
    }

    private void traverseTopRow(int[][] matrix, List<Integer> result,
                                int row, int startCol, int endCol) {
        for (int col = startCol; col <= endCol; col++) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseRightColumn(int[][] matrix, List<Integer> result,
                                     int col, int startRow, int endRow) {
        for (int row = startRow; row <= endRow; row++) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseBottomRow(int[][] matrix, List<Integer> result,
                                   int row, int startCol, int endCol) {
        for (int col = endCol; col >= startCol; col--) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseLeftColumn(int[][] matrix, List<Integer> result,
                                    int col, int startRow, int endRow) {
        for (int row = endRow; row >= startRow; row--) {
            result.add(matrix[row][col]);
        }
    }
}