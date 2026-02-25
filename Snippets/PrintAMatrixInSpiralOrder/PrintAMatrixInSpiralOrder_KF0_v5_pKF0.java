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
     * @return list of elements in spiral order
     */
    public List<Integer> print(int[][] matrix, int rows, int cols) {
        List<Integer> spiralOrder = new ArrayList<>();
        if (!isValidMatrix(matrix, rows, cols)) {
            return spiralOrder;
        }

        int top = 0;
        int bottom = rows - 1;
        int left = 0;
        int right = cols - 1;

        while (top <= bottom && left <= right) {
            addTopRow(matrix, top, left, right, spiralOrder);
            top++;

            addRightColumn(matrix, right, top, bottom, spiralOrder);
            right--;

            if (top <= bottom) {
                addBottomRow(matrix, bottom, left, right, spiralOrder);
                bottom--;
            }

            if (left <= right) {
                addLeftColumn(matrix, left, top, bottom, spiralOrder);
                left++;
            }
        }

        return spiralOrder;
    }

    private boolean isValidMatrix(int[][] matrix, int rows, int cols) {
        if (matrix == null || rows <= 0 || cols <= 0) {
            return false;
        }
        if (matrix.length < rows) {
            return false;
        }
        for (int i = 0; i < rows; i++) {
            if (matrix[i] == null || matrix[i].length < cols) {
                return false;
            }
        }
        return true;
    }

    private void addTopRow(int[][] matrix, int row, int startCol, int endCol, List<Integer> result) {
        for (int col = startCol; col <= endCol; col++) {
            result.add(matrix[row][col]);
        }
    }

    private void addRightColumn(int[][] matrix, int col, int startRow, int endRow, List<Integer> result) {
        for (int row = startRow; row <= endRow; row++) {
            result.add(matrix[row][col]);
        }
    }

    private void addBottomRow(int[][] matrix, int row, int startCol, int endCol, List<Integer> result) {
        for (int col = endCol; col >= startCol; col--) {
            result.add(matrix[row][col]);
        }
    }

    private void addLeftColumn(int[][] matrix, int col, int startRow, int endRow, List<Integer> result) {
        for (int row = endRow; row >= startRow; row--) {
            result.add(matrix[row][col]);
        }
    }
}