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

        int topRow = 0;
        int bottomRow = rowCount - 1;
        int leftCol = 0;
        int rightCol = colCount - 1;

        while (topRow <= bottomRow && leftCol <= rightCol) {
            // Traverse from left to right across the top row
            for (int col = leftCol; col <= rightCol; col++) {
                spiralOrder.add(matrix[topRow][col]);
            }
            topRow++;

            // Traverse from top to bottom down the rightmost column
            for (int row = topRow; row <= bottomRow; row++) {
                spiralOrder.add(matrix[row][rightCol]);
            }
            rightCol--;

            // Traverse from right to left across the bottom row
            if (topRow <= bottomRow) {
                for (int col = rightCol; col >= leftCol; col--) {
                    spiralOrder.add(matrix[bottomRow][col]);
                }
                bottomRow--;
            }

            // Traverse from bottom to top up the leftmost column
            if (leftCol <= rightCol) {
                for (int row = bottomRow; row >= topRow; row--) {
                    spiralOrder.add(matrix[row][leftCol]);
                }
                leftCol++;
            }
        }

        return spiralOrder;
    }
}