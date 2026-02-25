package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {

    public List<Integer> print(int[][] matrix, int totalRows, int totalCols) {
        List<Integer> spiralOrder = new ArrayList<>();

        if (matrix == null || totalRows <= 0 || totalCols <= 0) {
            return spiralOrder;
        }

        int top = 0;
        int bottom = totalRows - 1;
        int left = 0;
        int right = totalCols - 1;

        while (top <= bottom && left <= right) {
            traverseTopRow(matrix, top, left, right, spiralOrder);
            top++;

            traverseRightColumn(matrix, right, top, bottom, spiralOrder);
            right--;

            if (top <= bottom) {
                traverseBottomRow(matrix, bottom, left, right, spiralOrder);
                bottom--;
            }

            if (left <= right) {
                traverseLeftColumn(matrix, left, top, bottom, spiralOrder);
                left++;
            }
        }

        return spiralOrder;
    }

    private void traverseTopRow(int[][] matrix, int row, int startCol, int endCol, List<Integer> result) {
        for (int col = startCol; col <= endCol; col++) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseRightColumn(int[][] matrix, int col, int startRow, int endRow, List<Integer> result) {
        for (int row = startRow; row <= endRow; row++) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseBottomRow(int[][] matrix, int row, int startCol, int endCol, List<Integer> result) {
        for (int col = endCol; col >= startCol; col--) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseLeftColumn(int[][] matrix, int col, int startRow, int endRow, List<Integer> result) {
        for (int row = endRow; row >= startRow; row--) {
            result.add(matrix[row][col]);
        }
    }
}