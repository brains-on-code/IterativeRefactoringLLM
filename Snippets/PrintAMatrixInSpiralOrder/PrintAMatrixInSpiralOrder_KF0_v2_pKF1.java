package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {
    /**
     * Print a matrix in spiral order.
     *
     * @param matrix the matrix to be printed in spiral order
     * @param totalRows number of rows in the matrix
     * @param totalCols number of columns in the matrix
     * @author Sadiul Hakim : https://github.com/sadiul-hakim
     */
    public List<Integer> print(int[][] matrix, int totalRows, int totalCols) {
        int topRow = 0;
        int leftCol = 0;

        int bottomRow = totalRows;
        int rightCol = totalCols;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topRow < bottomRow && leftCol < rightCol) {
            // Traverse the top row from left to right
            for (int col = leftCol; col < rightCol; col++) {
                spiralOrder.add(matrix[topRow][col]);
            }
            topRow++;

            // Traverse the rightmost column from top to bottom
            for (int row = topRow; row < bottomRow; row++) {
                spiralOrder.add(matrix[row][rightCol - 1]);
            }
            rightCol--;

            // Traverse the bottom row from right to left, if any rows remain
            if (topRow < bottomRow) {
                for (int col = rightCol - 1; col >= leftCol; col--) {
                    spiralOrder.add(matrix[bottomRow - 1][col]);
                }
                bottomRow--;
            }

            // Traverse the leftmost column from bottom to top, if any columns remain
            if (leftCol < rightCol) {
                for (int row = bottomRow - 1; row >= topRow; row--) {
                    spiralOrder.add(matrix[row][leftCol]);
                }
                leftCol++;
            }
        }

        return spiralOrder;
    }
}