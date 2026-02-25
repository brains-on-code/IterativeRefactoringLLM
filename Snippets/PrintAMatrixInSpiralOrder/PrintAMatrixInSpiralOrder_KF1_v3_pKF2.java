package com.thealgorithms.var1;

import java.util.ArrayList;
import java.util.List;

public class Class1 {

    /**
     * Returns the elements of a 2D matrix in spiral order.
     *
     * @param matrix   the 2D array to traverse
     * @param rowCount the number of rows in the matrix
     * @param colCount the number of columns in the matrix
     * @return a list containing the matrix elements in spiral order
     */
    public List<Integer> method1(int[][] matrix, int rowCount, int colCount) {
        List<Integer> spiralOrder = new ArrayList<>();

        int topRow = 0;
        int bottomRow = rowCount - 1;
        int leftCol = 0;
        int rightCol = colCount - 1;

        while (topRow <= bottomRow && leftCol <= rightCol) {
            // Top edge: left → right
            for (int col = leftCol; col <= rightCol; col++) {
                spiralOrder.add(matrix[topRow][col]);
            }
            topRow++;

            // Right edge: top → bottom
            for (int row = topRow; row <= bottomRow; row++) {
                spiralOrder.add(matrix[row][rightCol]);
            }
            rightCol--;

            // Bottom edge: right → left
            if (topRow <= bottomRow) {
                for (int col = rightCol; col >= leftCol; col--) {
                    spiralOrder.add(matrix[bottomRow][col]);
                }
                bottomRow--;
            }

            // Left edge: bottom → top
            if (leftCol <= rightCol) {
                for (int row = bottomRow; row >= topRow; row--) {
                    spiralOrder.add(matrix[row][leftCol]);
                }
                leftCol++;
            }
        }

        return spiralOrder;
    }
}