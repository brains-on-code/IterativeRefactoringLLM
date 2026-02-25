package com.thealgorithms.var1;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrixTraversal {
    /**
     * Returns the elements of the given matrix in spiral order.
     *
     * @param matrix the 2D array to traverse
     * @param totalRows total number of rows in the matrix
     * @param totalCols total number of columns in the matrix
     * @return a list of integers representing the matrix in spiral order
     */
    public List<Integer> getSpiralOrder(int[][] matrix, int totalRows, int totalCols) {
        int topRow = 0;
        int leftCol = 0;
        int bottomRow = totalRows;
        int rightCol = totalCols;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topRow < bottomRow && leftCol < rightCol) {
            // Traverse from left to right across the current top row
            for (int col = leftCol; col < rightCol; col++) {
                spiralOrder.add(matrix[topRow][col]);
            }
            topRow++;

            // Traverse from top to bottom along the current rightmost column
            for (int row = topRow; row < bottomRow; row++) {
                spiralOrder.add(matrix[row][rightCol - 1]);
            }
            rightCol--;

            // Traverse from right to left across the current bottom row
            if (topRow < bottomRow) {
                for (int col = rightCol - 1; col >= leftCol; col--) {
                    spiralOrder.add(matrix[bottomRow - 1][col]);
                }
                bottomRow--;
            }

            // Traverse from bottom to top along the current leftmost column
            if (leftCol < rightCol) {
                for (int row = bottomRow - 1; row >= topRow; row--) {
                    spiralOrder.add(matrix[row][leftCol]);
                }
                leftCol++;
            }
        }

        return spiralOrder;
    }
}