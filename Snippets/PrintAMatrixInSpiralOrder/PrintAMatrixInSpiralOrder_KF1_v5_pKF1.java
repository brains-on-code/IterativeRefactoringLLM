package com.thealgorithms.var1;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrixTraversal {
    /**
     * Returns the elements of the given matrix in spiral order.
     *
     * @param matrix the 2D array to traverse
     * @param rowCount total number of rows in the matrix
     * @param columnCount total number of columns in the matrix
     * @return a list of integers representing the matrix in spiral order
     */
    public List<Integer> getSpiralOrder(int[][] matrix, int rowCount, int columnCount) {
        int topBoundary = 0;
        int leftBoundary = 0;
        int bottomBoundary = rowCount;
        int rightBoundary = columnCount;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topBoundary < bottomBoundary && leftBoundary < rightBoundary) {
            // Traverse from left to right across the current top row
            for (int columnIndex = leftBoundary; columnIndex < rightBoundary; columnIndex++) {
                spiralOrder.add(matrix[topBoundary][columnIndex]);
            }
            topBoundary++;

            // Traverse from top to bottom along the current rightmost column
            for (int rowIndex = topBoundary; rowIndex < bottomBoundary; rowIndex++) {
                spiralOrder.add(matrix[rowIndex][rightBoundary - 1]);
            }
            rightBoundary--;

            // Traverse from right to left across the current bottom row
            if (topBoundary < bottomBoundary) {
                for (int columnIndex = rightBoundary - 1; columnIndex >= leftBoundary; columnIndex--) {
                    spiralOrder.add(matrix[bottomBoundary - 1][columnIndex]);
                }
                bottomBoundary--;
            }

            // Traverse from bottom to top along the current leftmost column
            if (leftBoundary < rightBoundary) {
                for (int rowIndex = bottomBoundary - 1; rowIndex >= topBoundary; rowIndex--) {
                    spiralOrder.add(matrix[rowIndex][leftBoundary]);
                }
                leftBoundary++;
            }
        }

        return spiralOrder;
    }
}