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
     * @return a list of matrix elements in spiral order
     */
    public List<Integer> print(int[][] matrix, int rows, int cols) {
        List<Integer> result = new ArrayList<>();

        int top = 0;
        int bottom = rows - 1;
        int left = 0;
        int right = cols - 1;

        while (top <= bottom && left <= right) {
            // Top row: left → right
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }
            top++;

            // Right column: top → bottom
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }
            right--;

            // Bottom row: right → left (if still within vertical bounds)
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }
                bottom--;
            }

            // Left column: bottom → top (if still within horizontal bounds)
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][left]);
                }
                left++;
            }
        }

        return result;
    }
}