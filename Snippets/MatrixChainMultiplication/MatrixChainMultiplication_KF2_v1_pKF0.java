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
        int size = matrices.size();
        costTable = new int[size + 1][size + 1];
        splitTable = new int[size + 1][size + 1];
        dimensions = new int[size + 1];

        initializeTables(size);
        initializeDimensions(matrices);

        computeMatrixChainOrder(size);
        return new Result(costTable, splitTable);
    }

    private static void initializeTables(int size) {
        for (int i = 0; i <= size; i++) {
            Arrays.fill(costTable[i], -1);
            Arrays.fill(splitTable[i], -1);
        }
    }

    private static void initializeDimensions(ArrayList<Matrix> matrices) {
        for (int i = 0; i < dimensions.length; i++) {
            dimensions[i] = (i == 0)
                ? matrices.get(i).col()
                : matrices.get(i - 1).row();
        }
    }

    private static void computeMatrixChainOrder(int size) {
        for (int i = 1; i <= size; i++) {
            costTable[i][i] = 0;
        }

        for (int chainLength = 2; chainLength <= size; chainLength++) {
            for (int start = 1; start <= size - chainLength + 1; start++) {
                int end = start + chainLength - 1;
                costTable[start][end] = Integer.MAX_VALUE;

                for (int split = start; split < end; split++) {
                    int cost =
                        costTable[start][split]
                            + costTable[split + 1][end]
                            + dimensions[start - 1] * dimensions[split] * dimensions[end];

                    if (cost < costTable[start][end]) {
                        costTable[start][end] = cost;
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

        public int count() {
            return count;
        }

        public int col() {
            return columns;
        }

        public int row() {
            return rows;
        }
    }
}