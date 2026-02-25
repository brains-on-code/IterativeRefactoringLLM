package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

public final class MatrixChainMultiplication {
    private MatrixChainMultiplication() {
    }

    private static int[][] minCostTable;
    private static int[][] splitIndexTable;
    private static int[] dimensionArray;

    public static Result calculateMatrixChainOrder(ArrayList<Matrix> matrices) {
        int matrixCount = matrices.size();
        minCostTable = new int[matrixCount + 1][matrixCount + 1];
        splitIndexTable = new int[matrixCount + 1][matrixCount + 1];
        dimensionArray = new int[matrixCount + 1];

        for (int i = 0; i <= matrixCount; i++) {
            Arrays.fill(minCostTable[i], -1);
            Arrays.fill(splitIndexTable[i], -1);
        }

        for (int i = 0; i < dimensionArray.length; i++) {
            dimensionArray[i] =
                (i == 0) ? matrices.get(i).getColumnCount() : matrices.get(i - 1).getRowCount();
        }

        computeMatrixChainOrder(matrixCount);
        return new Result(minCostTable, splitIndexTable);
    }

    private static void computeMatrixChainOrder(int matrixCount) {
        for (int i = 1; i <= matrixCount; i++) {
            minCostTable[i][i] = 0;
        }

        for (int chainLength = 2; chainLength <= matrixCount; chainLength++) {
            for (int startIndex = 1; startIndex <= matrixCount - chainLength + 1; startIndex++) {
                int endIndex = startIndex + chainLength - 1;
                minCostTable[startIndex][endIndex] = Integer.MAX_VALUE;

                for (int splitIndex = startIndex; splitIndex < endIndex; splitIndex++) {
                    int currentCost =
                        minCostTable[startIndex][splitIndex]
                            + minCostTable[splitIndex + 1][endIndex]
                            + dimensionArray[startIndex - 1]
                                * dimensionArray[splitIndex]
                                * dimensionArray[endIndex];

                    if (currentCost < minCostTable[startIndex][endIndex]) {
                        minCostTable[startIndex][endIndex] = currentCost;
                        splitIndexTable[startIndex][endIndex] = splitIndex;
                    }
                }
            }
        }
    }

    public static class Result {
        private final int[][] minCostTable;
        private final int[][] splitIndexTable;

        public Result(int[][] minCostTable, int[][] splitIndexTable) {
            this.minCostTable = minCostTable;
            this.splitIndexTable = splitIndexTable;
        }

        public int[][] getMinCostTable() {
            return minCostTable;
        }

        public int[][] getSplitIndexTable() {
            return splitIndexTable;
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