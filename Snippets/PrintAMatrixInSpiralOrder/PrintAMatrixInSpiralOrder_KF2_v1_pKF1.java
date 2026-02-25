package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {

    public List<Integer> print(int[][] matrix, int totalRows, int totalCols) {

        int topRow = 0;
        int leftCol = 0;
        int currentIndex;

        List<Integer> result = new ArrayList<>();

        while (topRow < totalRows && leftCol < totalCols) {
            for (currentIndex = leftCol; currentIndex < totalCols; currentIndex++) {
                result.add(matrix[topRow][currentIndex]);
            }

            topRow++;

            for (currentIndex = topRow; currentIndex < totalRows; currentIndex++) {
                result.add(matrix[currentIndex][totalCols - 1]);
            }

            totalCols--;

            if (topRow < totalRows) {
                for (currentIndex = totalCols - 1; currentIndex >= leftCol; currentIndex--) {
                    result.add(matrix[totalRows - 1][currentIndex]);
                }

                totalRows--;
            }

            if (leftCol < totalCols) {
                for (currentIndex = totalRows - 1; currentIndex >= topRow; currentIndex--) {
                    result.add(matrix[currentIndex][leftCol]);
                }
                leftCol++;
            }
        }
        return result;
    }
}