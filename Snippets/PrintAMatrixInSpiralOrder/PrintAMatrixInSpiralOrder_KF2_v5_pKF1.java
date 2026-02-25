package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {

    public List<Integer> print(int[][] matrix, int rowCount, int colCount) {
        int topRow = 0;
        int leftCol = 0;
        int bottomRow = rowCount;
        int rightCol = colCount;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topRow < bottomRow && leftCol < rightCol) {
            // Traverse from left to right along the current top row
            for (int col = leftCol; col < rightCol; col++) {
                spiralOrder.add(matrix[topRow][col]);
            }
            topRow++;

            // Traverse from top to bottom along the current right column
            for (int row = topRow; row < bottomRow; row++) {
                spiralOrder.add(matrix[row][rightCol - 1]);
            }
            rightCol--;

            // Traverse from right to left along the current bottom row
            if (topRow < bottomRow) {
                for (int col = rightCol - 1; col >= leftCol; col--) {
                    spiralOrder.add(matrix[bottomRow - 1][col]);
                }
                bottomRow--;
            }

            // Traverse from bottom to top along the current left column
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