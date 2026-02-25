package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {

    public List<Integer> print(int[][] matrix, int totalRows, int totalCols) {
        List<Integer> spiralOrder = new ArrayList<>();

        int top = 0;
        int bottom = totalRows - 1;
        int left = 0;
        int right = totalCols - 1;

        while (top <= bottom && left <= right) {
            // Traverse the current top row from left to right
            for (int col = left; col <= right; col++) {
                spiralOrder.add(matrix[top][col]);
            }
            top++;

            // Traverse the current right column from top to bottom
            for (int row = top; row <= bottom; row++) {
                spiralOrder.add(matrix[row][right]);
            }
            right--;

            // Traverse the current bottom row from right to left (if still within bounds)
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    spiralOrder.add(matrix[bottom][col]);
                }
                bottom--;
            }

            // Traverse the current left column from bottom to top (if still within bounds)
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