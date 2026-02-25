package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {

    public List<Integer> print(int[][] matrix, int totalRows, int totalCols) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || totalRows == 0 || totalCols == 0) {
            return result;
        }

        int top = 0;
        int bottom = totalRows - 1;
        int left = 0;
        int right = totalCols - 1;

        while (top <= bottom && left <= right) {
            // Traverse from left to right along the top row
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }
            top++;

            // Traverse from top to bottom along the right column
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }
            right--;

            // Traverse from right to left along the bottom row
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }
                bottom--;
            }

            // Traverse from bottom to top along the left column
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