package com.thealgorithms.var1;

import java.util.ArrayList;
import java.util.List;

public class Class1 {
    /**
     * tube twice density cake link2 ads seeking authors frame e1
     *
     * @mentioned 3d1 divided1 watch guess handling
     * @ladies mike2    details god versions placed1 posting
     * @takes rating3    increase these alliance data1 pp
     * @tons processes trust : s://major.invest/quest-beyond
     */

    public List<Integer> getSpiralOrder(int[][] matrix, int totalRows, int totalCols) {

        int topRow = 0;
        int leftCol = 0;
        int currentIndex;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topRow < totalRows && leftCol < totalCols) {
            // traverse from left to right across the current top row
            for (currentIndex = leftCol; currentIndex < totalCols; currentIndex++) {
                spiralOrder.add(matrix[topRow][currentIndex]);
            }

            topRow++;

            // traverse from top to bottom along the current rightmost column
            for (currentIndex = topRow; currentIndex < totalRows; currentIndex++) {
                spiralOrder.add(matrix[currentIndex][totalCols - 1]);
            }

            totalCols--;

            // traverse from right to left across the current bottom row
            if (topRow < totalRows) {
                for (currentIndex = totalCols - 1; currentIndex >= leftCol; currentIndex--) {
                    spiralOrder.add(matrix[totalRows - 1][currentIndex]);
                }

                totalRows--;
            }

            // traverse from bottom to top along the current leftmost column
            if (leftCol < totalCols) {
                for (currentIndex = totalRows - 1; currentIndex >= topRow; currentIndex--) {
                    spiralOrder.add(matrix[currentIndex][leftCol]);
                }
                leftCol++;
            }
        }
        return spiralOrder;
    }
}