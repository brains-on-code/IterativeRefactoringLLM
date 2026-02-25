package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

public final class MatrixChainMultiplication {

    private MatrixChainMultiplication() {
        // Utility class; prevent instantiation
    }

    private static int[][] costTable;
    private static int[][] splitTable;
    private static int[] dimensions;

    public static Result calculateMatrixChainOrder(ArrayList<Matrix> matrices) {
        int matrixCount = matrices.size();
        costTable = new int[matrixCount + 1][matrixCount + 1];
        splitTable = new int[matrixCount + 1][matrixCount + 1];
        dimensions = new int[matrixCount + 1];

        initializeTables(matrixCount);
        initializeDimensions(matrices);
        computeMatrixChainOrder(matrixCount);

        return new Result(costTable, splitTable);
    }

    private static void initializeTables(int matrixCount) {
        for (int i = 0; i <= matrixCount; i++) {
            Arrays.fill(costTable[i], -1);
            Arrays.fill(splitTable[i], -1);
        }
    }

    private static void initializeDimensions(ArrayList<Matrix> matrices) {
        for (int i = 0; i < dimensions.length; i++) {
            if (i == 0) {
                dimensions[i] = matrices.get(i).getColumns();
            } else {
                dimensions[i] = matrices.get(i - 1).getRows();
            }
        }
    }

    private static void computeMatrixChainOrder(int matrixCount) {
        for (int i = 1; i <= matrixCount; i++) {
            costTable[i][i] = 0;
        }

        for (int chainLength = 2; chainLength <= matrixCount; chainLength++) {
            for (int start = 1; start <= matrixCount - chainLength + 1; start++) {
                int end = start + chainLength - 1;
                costTable[start][end] = Integer.MAX_VALUE;

                for (int split = start; split < end; split++) {
                    int costLeft = costTable[start][split];
                    int costRight = costTable[split + 1][end];
                    int multiplicationCost = dimensions[start - 1] * dimensions[split] * dimensions[end];
                    int totalCost = costLeft + costRight + multiplicationCost;

                    if (totalCost < costTable[start][end]) {
                        costTable[start][end] = totalCost;
                        splitTable[start][end] = split;
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
        private final int count;
        private final int columns;
        private final int rows;

        public Matrix(int count, int columns, int rows) {
            this.count = count;
            this.columns = columns;
            this.rows = rows;
        }

        public int getCount() {
            return count;
        }

        public int getColumns() {
            return columns;
        }

        public int getRows() {
            return rows;
        }
    }
}