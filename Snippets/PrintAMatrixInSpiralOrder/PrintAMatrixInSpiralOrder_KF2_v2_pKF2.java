package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {

    public List<Integer> print(int[][] matrix, int totalRows, int totalCols) {
        List<Integer> result = new ArrayList<>();

        int topRow = 0;
        int bottomRow = totalRows - 1;
        int leftCol = 0;
        int rightCol = totalCols - 1;

        while (topRow <= bottomRow && leftCol <= rightCol) {
            // Top row: left → right
            for (int col = leftCol; col <= rightCol; col++) {
                result.add(matrix[topRow][col]);
            }
            topRow++;

            // Right column: top → bottom
            for (int row = topRow; row <= bottomRow; row++) {
                result.add(matrix[row][rightCol]);
            }
            rightCol--;

            // Bottom row: right → left
            if (topRow <= bottomRow) {
                for (int col = rightCol; col >= leftCol; col--) {
                    result.add(matrix[bottomRow][col]);
                }
                bottomRow--;
            }

            // Left column: bottom → top
            if (leftCol <= rightCol) {
                for (int row = bottomRow; row >= topRow; row--) {
                    result.add(matrix[row][leftCol]);
                }
                leftCol++;
            }
        }

        return result;
    }
}