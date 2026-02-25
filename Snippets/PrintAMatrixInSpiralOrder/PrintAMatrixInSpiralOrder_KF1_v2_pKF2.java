package com.thealgorithms.var1;

import java.util.ArrayList;
import java.util.List;

public class Class1 {

    /**
     * Returns the elements of a 2D matrix in spiral order.
     *
     * @param matrix   the 2D array to traverse
     * @param rowCount the number of rows in the matrix
     * @param colCount the number of columns in the matrix
     * @return a list containing the matrix elements in spiral order
     */
    public List<Integer> method1(int[][] matrix, int rowCount, int colCount) {
        List<Integer> spiralOrder = new ArrayList<>();

        int top = 0;
        int bottom = rowCount - 1;
        int left = 0;
        int right = colCount - 1;

        while (top <= bottom && left <= right) {
            // Traverse from left to right across the current top row
            for (int col = left; col <= right; col++) {
                spiralOrder.add(matrix[top][col]);
            }
            top++;

            // Traverse from top to bottom along the current rightmost column
            for (int row = top; row <= bottom; row++) {
                spiralOrder.add(matrix[row][right]);
            }
            right--;

            // Traverse from right to left across the current bottom row
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    spiralOrder.add(matrix[bottom][col]);
                }
                bottom--;
            }

            // Traverse from bottom to top along the current leftmost column
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    spiralOrder.add(matrix[row][left]);
                }
                left++;
            }
        }

        return spiralOrder;
    }
}