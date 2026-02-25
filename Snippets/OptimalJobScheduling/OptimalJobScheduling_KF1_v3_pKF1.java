package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic programming example:
 *  - Computes a DP table based on base values and transition costs.
 */
public class DynamicProgrammingTable {

    private final int rowCount;
    private final int columnCount;
    private final int[][] baseValues;
    private final int[][] transitionCosts;
    private final int[][] dpTable;

    public DynamicProgrammingTable(int rowCount, int columnCount, int[][] baseValues, int[][] transitionCosts) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.baseValues = baseValues;
        this.transitionCosts = transitionCosts;
        this.dpTable = new int[rowCount][columnCount];
    }

    public void computeAndPrint() {
        computeDynamicProgrammingTable();
        printDynamicProgrammingTable();
    }

    private void computeDynamicProgrammingTable() {
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                dpTable[rowIndex][columnIndex] = computeCellValue(rowIndex, columnIndex);
            }
        }
    }

    private int computeCellValue(int rowIndex, int columnIndex) {
        if (rowIndex == 0) {
            return baseValues[rowIndex][columnIndex];
        }

        int[] candidateValues = new int[columnCount];

        for (int previousColumnIndex = 0; previousColumnIndex < columnCount; previousColumnIndex++) {
            candidateValues[previousColumnIndex] =
                dpTable[rowIndex - 1][previousColumnIndex]
                    + transitionCosts[previousColumnIndex][columnIndex]
                    + baseValues[rowIndex][columnIndex];
        }

        return findMinimum(candidateValues);
    }

    private int findMinimum(int[] values) {
        int minIndex = 0;

        for (int index = 1; index < values.length; index++) {
            if (values[index] < values[minIndex]) {
                minIndex = index;
            }
        }
        return values[minIndex];
    }

    private void printDynamicProgrammingTable() {
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                System.out.print(dpTable[rowIndex][columnIndex]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getValue(int rowIndex, int columnIndex) {
        return dpTable[rowIndex][columnIndex];
    }
}