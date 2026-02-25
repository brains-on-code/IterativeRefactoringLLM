package com.thealgorithms.var1;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrixTraversal {
    /**
     * Returns the elements of the given matrix in spiral order.
     *
     * @param matrix the 2D array to traverse
     * @param rowCount total number of rows in the matrix
     * @param colCount total number of columns in the matrix
     * @return a list of integers representing the matrix in spiral order
     */
    public List<Integer> getSpiralOrder(int[][] matrix, int rowCount, int colCount) {
        int topRowIndex = 0;
        int leftColIndex = 0;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topRowIndex < rowCount && leftColIndex < colCount) {
            // Traverse from left to right across the current top row
            for (int colIndex = leftColIndex; colIndex < colCount; colIndex++) {
                spiralOrder.add(matrix[topRowIndex][colIndex]);
            }
            topRowIndex++;

            // Traverse from top to bottom along the current rightmost column
            for (int rowIndex = topRowIndex; rowIndex < rowCount; rowIndex++) {
                spiralOrder.add(matrix[rowIndex][colCount - 1]);
            }
            colCount--;

            // Traverse from right to left across the current bottom row
            if (topRowIndex < rowCount) {
                for (int colIndex = colCount - 1; colIndex >= leftColIndex; colIndex--) {
                    spiralOrder.add(matrix[rowCount - 1][colIndex]);
                }
                rowCount--;
            }

            // Traverse from bottom to top along the current leftmost column
            if (leftColIndex < colCount) {
                for (int rowIndex = rowCount - 1; rowIndex >= topRowIndex; rowIndex--) {
                    spiralOrder.add(matrix[rowIndex][leftColIndex]);
                }
                leftColIndex++;
            }
        }

        return spiralOrder;
    }
}