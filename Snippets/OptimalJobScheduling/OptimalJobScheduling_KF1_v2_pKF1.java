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
        computeDpTable();
        printDpTable();
    }

    private void computeDpTable() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                dpTable[row][column] = computeCellValue(row, column);
            }
        }
    }

    private int computeCellValue(int row, int column) {
        if (row == 0) {
            return baseValues[row][column];
        } else {
            int[] candidateValues = new int[columnCount];

            for (int previousColumn = 0; previousColumn < columnCount; previousColumn++) {
                candidateValues[previousColumn] =
                    dpTable[row - 1][previousColumn]
                        + transitionCosts[previousColumn][column]
                        + baseValues[row][column];
            }
            return findMinimum(candidateValues);
        }
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

    private void printDpTable() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                System.out.print(dpTable[row][column]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getValue(int row, int column) {
        return dpTable[row][column];
    }
}