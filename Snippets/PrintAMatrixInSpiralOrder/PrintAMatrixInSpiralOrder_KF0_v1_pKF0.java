package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {

    /**
     * Prints the elements of a matrix in spiral order.
     *
     * @param matrix the matrix to traverse
     * @param row    number of rows in the matrix
     * @param col    number of columns in the matrix
     * @return list of elements in spiral order
     */
    public List<Integer> print(int[][] matrix, int row, int col) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || row == 0 || col == 0) {
            return result;
        }

        int top = 0;
        int bottom = row - 1;
        int left = 0;
        int right = col - 1;

        while (top <= bottom && left <= right) {
            // Traverse from left to right along the top row
            for (int j = left; j <= right; j++) {
                result.add(matrix[top][j]);
            }
            top++;

            // Traverse from top to bottom along the right column
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;

            // Traverse from right to left along the bottom row
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    result.add(matrix[bottom][j]);
                }
                bottom--;
            }

            // Traverse from bottom to top along the left column
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }

        return result;
    }
}