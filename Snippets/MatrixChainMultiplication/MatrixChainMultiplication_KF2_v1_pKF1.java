package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

public final class MatrixChainMultiplication {
    private MatrixChainMultiplication() {
    }

    private static int[][] costTable;
    private static int[][] splitTable;
    private static int[] dimensionArray;

    public static Result calculateMatrixChainOrder(ArrayList<Matrix> matrices) {
        int matrixCount = matrices.size();
        costTable = new int[matrixCount + 1][matrixCount + 1];
        splitTable = new int[matrixCount + 1][matrixCount + 1];
        dimensionArray = new int[matrixCount + 1];

        for (int i = 0; i < matrixCount + 1; i++) {
            Arrays.fill(costTable[i], -1);
            Arrays.fill(splitTable[i], -1);
        }

        for (int i = 0; i < dimensionArray.length; i++) {
            dimensionArray[i] = (i == 0) ? matrices.get(i).getColumnCount() : matrices.get(i - 1).getRowCount();
        }

        computeMatrixChainOrder(matrixCount);
        return new Result(costTable, splitTable);
    }

    private static void computeMatrixChainOrder(int matrixCount) {
        for (int i = 1; i < matrixCount + 1; i++) {
            costTable[i][i] = 0;
        }

        for (int chainLength = 2; chainLength < matrixCount + 1; chainLength++) {
            for (int startIndex = 1; startIndex < matrixCount - chainLength + 2; startIndex++) {
                int endIndex = startIndex + chainLength - 1;
                costTable[startIndex][endIndex] = Integer.MAX_VALUE;

                for (int splitIndex = startIndex; splitIndex < endIndex; splitIndex++) {
                    int currentCost =
                        costTable[startIndex][splitIndex]
                            + costTable[splitIndex + 1][endIndex]
                            + dimensionArray[startIndex - 1] * dimensionArray[splitIndex] * dimensionArray[endIndex];

                    if (currentCost < costTable[startIndex][endIndex]) {
                        costTable[startIndex][endIndex] = currentCost;
                        splitTable[startIndex][endIndex] = splitIndex;
                    }
                }
            }
        }
    }

    public static class Result {
        private final int[][] costTable;
        private final int[][] splitTable;

        public Result(int[][] costTable, int[][] splitTable) {
            this.costTable = costTable;
            this.splitTable = splitTable;
        }

        public int[][] getCostTable() {
            return costTable;
        }

        public int[][] getSplitTable() {
            return splitTable;
        }
    }

    public static class Matrix {
        private final int id;
        private final int columnCount;
        private final int rowCount;

        public Matrix(int id, int columnCount, int rowCount) {
            this.id = id;
            this.columnCount = columnCount;
            this.rowCount = rowCount;
        }

        public int getId() {
            return id;
        }

        public int getColumnCount() {
            return columnCount;
        }

        public int getRowCount() {
            return rowCount;
        }
    }
}