package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {

    public List<Integer> print(int[][] matrix, int rowCount, int colCount) {
        int topBoundary = 0;
        int leftBoundary = 0;
        int bottomBoundary = rowCount;
        int rightBoundary = colCount;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topBoundary < bottomBoundary && leftBoundary < rightBoundary) {
            // Traverse from left to right along the current top boundary
            for (int col = leftBoundary; col < rightBoundary; col++) {
                spiralOrder.add(matrix[topBoundary][col]);
            }
            topBoundary++;

            // Traverse from top to bottom along the current right boundary
            for (int row = topBoundary; row < bottomBoundary; row++) {
                spiralOrder.add(matrix[row][rightBoundary - 1]);
            }
            rightBoundary--;

            // Traverse from right to left along the current bottom boundary
            if (topBoundary < bottomBoundary) {
                for (int col = rightBoundary - 1; col >= leftBoundary; col--) {
                    spiralOrder.add(matrix[bottomBoundary - 1][col]);
                }
                bottomBoundary--;
            }

            // Traverse from bottom to top along the current left boundary
            if (leftBoundary < rightBoundary) {
                for (int row = bottomBoundary - 1; row >= topBoundary; row--) {
                    spiralOrder.add(matrix[row][leftBoundary]);
                }
                leftBoundary++;
            }
        }

        return spiralOrder;
    }
}