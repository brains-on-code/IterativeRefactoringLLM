package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {
    /**
     * Print a matrix in spiral order.
     *
     * @param matrix the matrix to be printed in spiral order
     * @param rowCount number of rows in the matrix
     * @param colCount number of columns in the matrix
     * @author Sadiul Hakim : https://github.com/sadiul-hakim
     */
    public List<Integer> print(int[][] matrix, int rowCount, int colCount) {
        int topBoundary = 0;
        int leftBoundary = 0;

        int bottomBoundary = rowCount;
        int rightBoundary = colCount;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topBoundary < bottomBoundary && leftBoundary < rightBoundary) {
            // Traverse the top row from left to right
            for (int col = leftBoundary; col < rightBoundary; col++) {
                spiralOrder.add(matrix[topBoundary][col]);
            }
            topBoundary++;

            // Traverse the rightmost column from top to bottom
            for (int row = topBoundary; row < bottomBoundary; row++) {
                spiralOrder.add(matrix[row][rightBoundary - 1]);
            }
            rightBoundary--;

            // Traverse the bottom row from right to left, if any rows remain
            if (topBoundary < bottomBoundary) {
                for (int col = rightBoundary - 1; col >= leftBoundary; col--) {
                    spiralOrder.add(matrix[bottomBoundary - 1][col]);
                }
                bottomBoundary--;
            }

            // Traverse the leftmost column from bottom to top, if any columns remain
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