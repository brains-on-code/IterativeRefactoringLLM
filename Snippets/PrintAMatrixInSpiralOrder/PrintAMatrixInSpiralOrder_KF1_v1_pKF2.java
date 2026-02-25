package com.thealgorithms.var1;

import java.util.ArrayList;
import java.util.List;

public class Class1 {

    /**
     * Returns the elements of a 2D matrix in spiral order.
     *
     * @param matrix the 2D array to traverse
     * @param rowCount the number of rows in the matrix
     * @param colCount the number of columns in the matrix
     * @return a list containing the matrix elements in spiral order
     */
    public List<Integer> method1(int[][] matrix, int rowCount, int colCount) {
        int top = 0;
        int left = 0;
        int row;
        List<Integer> spiralOrder = new ArrayList<>();

        while (top < rowCount && left < colCount) {
            // Traverse from left to right across the top row
            for (row = left; row < colCount; row++) {
                spiralOrder.add(matrix[top][row]);
            }
            top++;

            // Traverse from top to bottom along the rightmost column
            for (row = top; row < rowCount; row++) {
                spiralOrder.add(matrix[row][colCount - 1]);
            }
            colCount--;

            // Traverse from right to left across the bottom row (if still within bounds)
            if (top < rowCount) {
                for (row = colCount - 1; row >= left; row--) {
                    spiralOrder.add(matrix[rowCount - 1][row]);
                }
                rowCount--;
            }

            // Traverse from bottom to top along the leftmost column (if still within bounds)
            if (left < colCount) {
                for (row = rowCount - 1; row >= top; row--) {
                    spiralOrder.add(matrix[row][left]);
                }
                left++;
            }
        }
        return spiralOrder;
    }
}