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
        int startRow = 0;
        int startCol = 0;

        List<Integer> spiralOrder = new ArrayList<>();

        while (startRow < totalRows && startCol < totalCols) {
            // Traverse the top row from left to right
            for (int col = startCol; col < totalCols; col++) {
                spiralOrder.add(matrix[startRow][col]);
            }
            startRow++;

            // Traverse the rightmost column from top to bottom
            for (int row = startRow; row < totalRows; row++) {
                spiralOrder.add(matrix[row][totalCols - 1]);
            }
            totalCols--;

            // Traverse the bottom row from right to left, if any rows remain
            if (startRow < totalRows) {
                for (int col = totalCols - 1; col >= startCol; col--) {
                    spiralOrder.add(matrix[totalRows - 1][col]);
                }
                totalRows--;
            }

            // Traverse the leftmost column from bottom to top, if any columns remain
            if (startCol < totalCols) {
                for (int row = totalRows - 1; row >= startRow; row--) {
                    spiralOrder.add(matrix[row][startCol]);
                }
                startCol++;
            }
        }

        return spiralOrder;
    }
}