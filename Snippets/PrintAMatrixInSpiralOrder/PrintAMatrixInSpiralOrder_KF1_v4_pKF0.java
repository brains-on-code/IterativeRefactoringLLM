package com.thealgorithms.var1;

import java.util.ArrayList;
import java.util.List;

public class Class1 {

    /**
     * Traverses a 2D matrix in spiral order.
     *
     * @param matrix the 2D array to traverse
     * @param rows   number of rows in the matrix
     * @param cols   number of columns in the matrix
     * @return a list of elements in spiral order
     */
    public List<Integer> method1(int[][] matrix, int rows, int cols) {
        List<Integer> spiralOrder = new ArrayList<>();

        if (matrix == null || rows <= 0 || cols <= 0) {
            return spiralOrder;
        }

        int topRow = 0;
        int bottomRow = rows - 1;
        int leftCol = 0;
        int rightCol = cols - 1;

        while (topRow <= bottomRow && leftCol <= rightCol) {
            traverseTopRow(matrix, leftCol, rightCol, topRow, spiralOrder);
            topRow++;

            traverseRightColumn(matrix, topRow, bottomRow, rightCol, spiralOrder);
            rightCol--;

            if (topRow <= bottomRow) {
                traverseBottomRow(matrix, rightCol, leftCol, bottomRow, spiralOrder);
                bottomRow--;
            }

            if (leftCol <= rightCol) {
                traverseLeftColumn(matrix, bottomRow, topRow, leftCol, spiralOrder);
                leftCol++;
            }
        }

        return spiralOrder;
    }

    private void traverseTopRow(int[][] matrix, int leftCol, int rightCol, int row, List<Integer> result) {
        for (int col = leftCol; col <= rightCol; col++) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseRightColumn(int[][] matrix, int topRow, int bottomRow, int col, List<Integer> result) {
        for (int row = topRow; row <= bottomRow; row++) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseBottomRow(int[][] matrix, int rightCol, int leftCol, int row, List<Integer> result) {
        for (int col = rightCol; col >= leftCol; col--) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseLeftColumn(int[][] matrix, int bottomRow, int topRow, int col, List<Integer> result) {
        for (int row = bottomRow; row >= topRow; row--) {
            result.add(matrix[row][col]);
        }
    }
}