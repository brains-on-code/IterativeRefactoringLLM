package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {

    public List<Integer> print(int[][] matrix, int totalRows, int totalCols) {
        List<Integer> spiralOrder = new ArrayList<>();

        int topRow = 0;
        int bottomRow = totalRows - 1;
        int leftCol = 0;
        int rightCol = totalCols - 1;

        while (topRow <= bottomRow && leftCol <= rightCol) {
            for (int col = leftCol; col <= rightCol; col++) {
                spiralOrder.add(matrix[topRow][col]);
            }
            topRow++;

            for (int row = topRow; row <= bottomRow; row++) {
                spiralOrder.add(matrix[row][rightCol]);
            }
            rightCol--;

            if (topRow <= bottomRow) {
                for (int col = rightCol; col >= leftCol; col--) {
                    spiralOrder.add(matrix[bottomRow][col]);
                }
                bottomRow--;
            }

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