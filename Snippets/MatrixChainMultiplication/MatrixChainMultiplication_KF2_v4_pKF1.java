package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

public final class MatrixChainMultiplication {
    private MatrixChainMultiplication() {}

    private static int[][] minMultiplicationCost;
    private static int[][] optimalSplitIndex;
    private static int[] matrixDimensions;

    public static Result calculateMatrixChainOrder(ArrayList<Matrix> matrices) {
        int numberOfMatrices = matrices.size();
        minMultiplicationCost = new int[numberOfMatrices + 1][numberOfMatrices + 1];
        optimalSplitIndex = new int[numberOfMatrices + 1][numberOfMatrices + 1];
        matrixDimensions = new int[numberOfMatrices + 1];

        for (int i = 0; i <= numberOfMatrices; i++) {
            Arrays.fill(minMultiplicationCost[i], -1);
            Arrays.fill(optimalSplitIndex[i], -1);
        }

        for (int i = 0; i < matrixDimensions.length; i++) {
            matrixDimensions[i] =
                (i == 0) ? matrices.get(i).getColumnCount() : matrices.get(i - 1).getRowCount();
        }

        computeMatrixChainOrder(numberOfMatrices);
        return new Result(minMultiplicationCost, optimalSplitIndex);
    }

    private static void computeMatrixChainOrder(int numberOfMatrices) {
        for (int i = 1; i <= numberOfMatrices; i++) {
            minMultiplicationCost[i][i] = 0;
        }

        for (int chainLength = 2; chainLength <= numberOfMatrices; chainLength++) {
            for (int startMatrixIndex = 1;
                    startMatrixIndex <= numberOfMatrices - chainLength + 1;
                    startMatrixIndex++) {

                int endMatrixIndex = startMatrixIndex + chainLength - 1;
                minMultiplicationCost[startMatrixIndex][endMatrixIndex] = Integer.MAX_VALUE;

                for (int splitMatrixIndex = startMatrixIndex;
                        splitMatrixIndex < endMatrixIndex;
                        splitMatrixIndex++) {

                    int currentCost =
                        minMultiplicationCost[startMatrixIndex][splitMatrixIndex]
                            + minMultiplicationCost[splitMatrixIndex + 1][endMatrixIndex]
                            + matrixDimensions[startMatrixIndex - 1]
                                * matrixDimensions[splitMatrixIndex]
                                * matrixDimensions[endMatrixIndex];

                    if (currentCost < minMultiplicationCost[startMatrixIndex][endMatrixIndex]) {
                        minMultiplicationCost[startMatrixIndex][endMatrixIndex] = currentCost;
                        optimalSplitIndex[startMatrixIndex][endMatrixIndex] = splitMatrixIndex;
                    }
                }
            }
        }
    }

    public static class Result {
        private final int[][] minMultiplicationCost;
        private final int[][] optimalSplitIndex;

        public Result(int[][] minMultiplicationCost, int[][] optimalSplitIndex) {
            this.minMultiplicationCost = minMultiplicationCost;
            this.optimalSplitIndex = optimalSplitIndex;
        }

        public int[][] getMinMultiplicationCost() {
            return minMultiplicationCost;
        }

        public int[][] getOptimalSplitIndex() {
            return optimalSplitIndex;
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