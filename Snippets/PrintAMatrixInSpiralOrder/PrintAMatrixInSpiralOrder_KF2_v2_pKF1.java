package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {

    public List<Integer> print(int[][] matrix, int rowCount, int colCount) {
        int topBoundary = 0;
        int leftBoundary = 0;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topBoundary < rowCount && leftBoundary < colCount) {
            // Traverse from left to right along the top boundary
            for (int col = leftBoundary; col < colCount; col++) {
                spiralOrder.add(matrix[topBoundary][col]);
            }
            topBoundary++;

            // Traverse from top to bottom along the right boundary
            for (int row = topBoundary; row < rowCount; row++) {
                spiralOrder.add(matrix[row][colCount - 1]);
            }
            colCount--;

            // Traverse from right to left along the bottom boundary
            if (topBoundary < rowCount) {
                for (int col = colCount - 1; col >= leftBoundary; col--) {
                    spiralOrder.add(matrix[rowCount - 1][col]);
                }
                rowCount--;
            }

            // Traverse from bottom to top along the left boundary
            if (leftBoundary < colCount) {
                for (int row = rowCount - 1; row >= topBoundary; row--) {
                    spiralOrder.add(matrix[row][leftBoundary]);
                }
                leftBoundary++;
            }
        }

        return spiralOrder;
    }
}