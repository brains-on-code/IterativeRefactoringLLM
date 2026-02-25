package com.thealgorithms.dynamicprogramming;

public class Class1 {

    private final int rowCount;
    private final int columnCount;
    private final int[][] baseValues;
    private final int[][] transitionCosts;
    private final int[][] dpTable;

    public Class1(int rowCount, int columnCount, int[][] baseValues, int[][] transitionCosts) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.baseValues = baseValues;
        this.transitionCosts = transitionCosts;
        this.dpTable = new int[rowCount][columnCount];
    }

    public void compute() {
        fillDpTable();
        printDpTable();
    }

    private void fillDpTable() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                dpTable[row][column] = computeCellValue(row, column);
            }
        }
    }

    private int computeCellValue(int row, int column) {
        if (row == 0) {
            return baseValues[row][column];
        }

        int baseValue = baseValues[row][column];
        int minValue = Integer.MAX_VALUE;

        for (int previousColumn = 0; previousColumn < columnCount; previousColumn++) {
            int previousValue = dpTable[row - 1][previousColumn];
            int transitionCost = transitionCosts[previousColumn][column];

            int candidateValue = previousValue + transitionCost + baseValue;
            minValue = Math.min(minValue, candidateValue);
        }

        return minValue;
    }

    private void printDpTable() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                System.out.print(dpTable[row][column] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getValue(int row, int column) {
        return dpTable[row][column];
    }
}