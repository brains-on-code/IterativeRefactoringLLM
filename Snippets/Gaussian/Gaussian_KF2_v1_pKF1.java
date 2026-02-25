package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class Gaussian {

    private Gaussian() {
    }

    public static ArrayList<Double> gaussian(int matrixSize, List<Double> flatMatrix) {
        double[][] augmentedMatrix = new double[matrixSize + 1][matrixSize + 1];
        double[][] workingMatrix = new double[matrixSize][matrixSize + 1];

        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col <= matrixSize; col++) {
                augmentedMatrix[row][col] = flatMatrix.get(row);
            }
        }

        augmentedMatrix = gaussianElimination(matrixSize, augmentedMatrix);
        return backSubstitution(matrixSize, workingMatrix, augmentedMatrix);
    }

    public static double[][] gaussianElimination(int matrixSize, double[][] augmentedMatrix) {
        for (int pivotRow = 0; pivotRow < matrixSize - 1; pivotRow++) {
            for (int row = pivotRow; row < matrixSize - 1; row++) {
                double factor = augmentedMatrix[row + 1][pivotRow] / augmentedMatrix[pivotRow][pivotRow];

                for (int col = pivotRow; col <= matrixSize; col++) {
                    augmentedMatrix[row + 1][col] =
                        augmentedMatrix[row + 1][col] - (factor * augmentedMatrix[pivotRow][col]);
                }
            }
        }
        return augmentedMatrix;
    }

    public static ArrayList<Double> backSubstitution(int matrixSize, double[][] workingMatrix, double[][] augmentedMatrix) {
        ArrayList<Double> solution = new ArrayList<>();

        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col <= matrixSize; col++) {
                workingMatrix[row][col] = augmentedMatrix[row][col];
            }
        }

        for (int row = matrixSize - 1; row >= 0; row--) {
            double sum = 0;
            int col;
            for (col = matrixSize - 1; col > row; col--) {
                workingMatrix[row][col] = workingMatrix[col][col] * workingMatrix[row][col];
                sum += workingMatrix[row][col];
            }
            if (workingMatrix[row][row] == 0) {
                workingMatrix[row][row] = 0;
            } else {
                workingMatrix[row][row] = (workingMatrix[row][matrixSize] - sum) / workingMatrix[row][row];
            }
            solution.add(workingMatrix[row][col]);
        }
        return solution;
    }
}