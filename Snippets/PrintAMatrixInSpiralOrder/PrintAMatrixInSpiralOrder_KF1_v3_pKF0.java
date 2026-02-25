package com.thealgorithms.var1;

import java.util.ArrayList;
import java.util.List;

public class Class1 {

    /**
     * Traverses a 2D matrix in spiral order.
     *
     * @param matrix   the 2D array to traverse
     * @param rowCount number of rows in the matrix
     * @param colCount number of columns in the matrix
     * @return a list of elements in spiral order
     */
    public List<Integer> method1(int[][] matrix, int rowCount, int colCount) {
        List<Integer> spiralOrder = new ArrayList<>();

        if (matrix == null || rowCount == 0 || colCount == 0) {
            return spiralOrder;
        }

        int top = 0;
        int bottom = rowCount - 1;
        int left = 0;
        int right = colCount - 1;

        while (top <= bottom && left <= right) {
            traverseTopRow(matrix, left, right, top, spiralOrder);
            top++;

            traverseRightColumn(matrix, top, bottom, right, spiralOrder);
            right--;

            if (top <= bottom) {
                traverseBottomRow(matrix, right, left, bottom, spiralOrder);
                bottom--;
            }

            if (left <= right) {
                traverseLeftColumn(matrix, bottom, top, left, spiralOrder);
                left++;
            }
        }

        return spiralOrder;
    }

    private void traverseTopRow(int[][] matrix, int left, int right, int row, List<Integer> result) {
        for (int col = left; col <= right; col++) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseRightColumn(int[][] matrix, int top, int bottom, int col, List<Integer> result) {
        for (int row = top; row <= bottom; row++) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseBottomRow(int[][] matrix, int right, int left, int row, List<Integer> result) {
        for (int col = right; col >= left; col--) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseLeftColumn(int[][] matrix, int bottom, int top, int col, List<Integer> result) {
        for (int row = bottom; row >= top; row--) {
            result.add(matrix[row][col]);
        }
    }
}