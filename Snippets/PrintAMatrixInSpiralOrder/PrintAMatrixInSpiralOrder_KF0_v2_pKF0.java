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
        if (matrix == null || rows <= 0 || cols <= 0) {
            return spiralOrder;
        }

        int topRow = 0;
        int bottomRow = rows - 1;
        int leftCol = 0;
        int rightCol = cols - 1;

        while (topRow <= bottomRow && leftCol <= rightCol) {
            traverseTopRow(matrix, topRow, leftCol, rightCol, spiralOrder);
            topRow++;

            traverseRightCol(matrix, rightCol, topRow, bottomRow, spiralOrder);
            rightCol--;

            if (topRow <= bottomRow) {
                traverseBottomRow(matrix, bottomRow, leftCol, rightCol, spiralOrder);
                bottomRow--;
            }

            if (leftCol <= rightCol) {
                traverseLeftCol(matrix, leftCol, topRow, bottomRow, spiralOrder);
                leftCol++;
            }
        }

        return spiralOrder;
    }

    private void traverseTopRow(int[][] matrix, int row, int startCol, int endCol, List<Integer> result) {
        for (int col = startCol; col <= endCol; col++) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseRightCol(int[][] matrix, int col, int startRow, int endRow, List<Integer> result) {
        for (int row = startRow; row <= endRow; row++) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseBottomRow(int[][] matrix, int row, int startCol, int endCol, List<Integer> result) {
        for (int col = endCol; col >= startCol; col--) {
            result.add(matrix[row][col]);
        }
    }

    private void traverseLeftCol(int[][] matrix, int col, int startRow, int endRow, List<Integer> result) {
        for (int row = endRow; row >= startRow; row--) {
            result.add(matrix[row][col]);
        }
    }
}