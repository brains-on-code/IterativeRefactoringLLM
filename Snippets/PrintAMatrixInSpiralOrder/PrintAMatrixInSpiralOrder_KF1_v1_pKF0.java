package com.thealgorithms.var1;

import java.util.ArrayList;
import java.util.List;

public class Class1 {

    /**
     * Traverses a 2D matrix in spiral order.
     *
     * @param matrix the 2D array to traverse
     * @param rowCount number of rows in the matrix
     * @param colCount number of columns in the matrix
     * @return a list of elements in spiral order
     */
    public List<Integer> method1(int[][] matrix, int rowCount, int colCount) {
        int topRow = 0;
        int leftCol = 0;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topRow < rowCount && leftCol < colCount) {
            // Traverse from left to right across the top row
            for (int col = leftCol; col < colCount; col++) {
                spiralOrder.add(matrix[topRow][col]);
            }
            topRow++;

            // Traverse from top to bottom down the rightmost column
            for (int row = topRow; row < rowCount; row++) {
                spiralOrder.add(matrix[row][colCount - 1]);
            }
            colCount--;

            // Traverse from right to left across the bottom row
            if (topRow < rowCount) {
                for (int col = colCount - 1; col >= leftCol; col--) {
                    spiralOrder.add(matrix[rowCount - 1][col]);
                }
                rowCount--;
            }

            // Traverse from bottom to top up the leftmost column
            if (leftCol < colCount) {
                for (int row = rowCount - 1; row >= topRow; row--) {
                    spiralOrder.add(matrix[row][leftCol]);
                }
                leftCol++;
            }
        }

        return spiralOrder;
    }
}