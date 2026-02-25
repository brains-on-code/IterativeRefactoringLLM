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

        int bottomBoundaryExclusive = rowCount;
        int rightBoundaryExclusive = colCount;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topBoundary < bottomBoundaryExclusive && leftBoundary < rightBoundaryExclusive) {
            // Traverse the top row from left to right
            for (int columnIndex = leftBoundary; columnIndex < rightBoundaryExclusive; columnIndex++) {
                spiralOrder.add(matrix[topBoundary][columnIndex]);
            }
            topBoundary++;

            // Traverse the rightmost column from top to bottom
            for (int rowIndex = topBoundary; rowIndex < bottomBoundaryExclusive; rowIndex++) {
                spiralOrder.add(matrix[rowIndex][rightBoundaryExclusive - 1]);
            }
            rightBoundaryExclusive--;

            // Traverse the bottom row from right to left, if any rows remain
            if (topBoundary < bottomBoundaryExclusive) {
                for (int columnIndex = rightBoundaryExclusive - 1; columnIndex >= leftBoundary; columnIndex--) {
                    spiralOrder.add(matrix[bottomBoundaryExclusive - 1][columnIndex]);
                }
                bottomBoundaryExclusive--;
            }

            // Traverse the leftmost column from bottom to top, if any columns remain
            if (leftBoundary < rightBoundaryExclusive) {
                for (int rowIndex = bottomBoundaryExclusive - 1; rowIndex >= topBoundary; rowIndex--) {
                    spiralOrder.add(matrix[rowIndex][leftBoundary]);
                }
                leftBoundary++;
            }
        }

        return spiralOrder;
    }
}