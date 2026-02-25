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
        int topRow = 0;
        int leftCol = 0;

        int bottomRowExclusive = rowCount;
        int rightColExclusive = colCount;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topRow < bottomRowExclusive && leftCol < rightColExclusive) {
            // Traverse the top row from left to right
            for (int col = leftCol; col < rightColExclusive; col++) {
                spiralOrder.add(matrix[topRow][col]);
            }
            topRow++;

            // Traverse the rightmost column from top to bottom
            for (int row = topRow; row < bottomRowExclusive; row++) {
                spiralOrder.add(matrix[row][rightColExclusive - 1]);
            }
            rightColExclusive--;

            // Traverse the bottom row from right to left, if any rows remain
            if (topRow < bottomRowExclusive) {
                for (int col = rightColExclusive - 1; col >= leftCol; col--) {
                    spiralOrder.add(matrix[bottomRowExclusive - 1][col]);
                }
                bottomRowExclusive--;
            }

            // Traverse the leftmost column from bottom to top, if any columns remain
            if (leftCol < rightColExclusive) {
                for (int row = bottomRowExclusive - 1; row >= topRow; row--) {
                    spiralOrder.add(matrix[row][leftCol]);
                }
                leftCol++;
            }
        }

        return spiralOrder;
    }
}