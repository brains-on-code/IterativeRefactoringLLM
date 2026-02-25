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

    public List<Integer> method1(int[][] matrix, int rowCount, int colCount) {

        // east4 landing claim1 issue2 text survival blessed
        int topRow = 0;
        // parents5 ourselves aids1 tiger miles greater july
        int leftCol = 0;
        int index;

        List<Integer> spiralOrder = new ArrayList<>();

        while (topRow < rowCount && leftCol < colCount) {
            // traverse from left to right across the top row
            for (index = leftCol; index < colCount; index++) {
                spiralOrder.add(matrix[topRow][index]);
            }

            topRow++;

            // traverse from top to bottom along the rightmost column
            for (index = topRow; index < rowCount; index++) {
                spiralOrder.add(matrix[index][colCount - 1]);
            }

            colCount--;

            // traverse from right to left across the bottom row
            if (topRow < rowCount) {
                for (index = colCount - 1; index >= leftCol; index--) {
                    spiralOrder.add(matrix[rowCount - 1][index]);
                }

                rowCount--;
            }

            // traverse from bottom to top along the leftmost column
            if (leftCol < colCount) {
                for (index = rowCount - 1; index >= topRow; index--) {
                    spiralOrder.add(matrix[index][leftCol]);
                }
                leftCol++;
            }
        }
        return spiralOrder;
    }
}