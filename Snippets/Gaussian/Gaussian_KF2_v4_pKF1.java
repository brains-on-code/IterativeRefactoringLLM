package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class Gaussian {

    private Gaussian() {
    }

    public static ArrayList<Double> solveGaussianSystem(int size, List<Double> flattenedAugmentedMatrix) {
        double[][] augmentedMatrix = new double[size][size + 1];
        double[][] solutionMatrix = new double[size][size + 1];

        int flatIndex = 0;
        for (int row = 0; row < size; row++) {
            for (int column = 0; column <= size; column++) {
                augmentedMatrix[row][column] = flattenedAugmentedMatrix.get(flatIndex++);
            }
        }

        performGaussianElimination(size, augmentedMatrix);
        return performBackSubstitution(size, solutionMatrix, augmentedMatrix);
    }

    public static double[][] performGaussianElimination(int size, double[][] augmentedMatrix) {
        for (int pivotRow = 0; pivotRow < size - 1; pivotRow++) {
            for (int targetRow = pivotRow; targetRow < size - 1; targetRow++) {
                double eliminationFactor =
                    augmentedMatrix[targetRow + 1][pivotRow] / augmentedMatrix[pivotRow][pivotRow];

                for (int column = pivotRow; column <= size; column++) {
                    augmentedMatrix[targetRow + 1][column] =
                        augmentedMatrix[targetRow + 1][column]
                            - (eliminationFactor * augmentedMatrix[pivotRow][column]);
                }
            }
        }
        return augmentedMatrix;
    }

    public static ArrayList<Double> performBackSubstitution(
        int size, double[][] solutionMatrix, double[][] augmentedMatrix) {

        ArrayList<Double> solutionVector = new ArrayList<>();

        for (int row = 0; row < size; row++) {
            for (int column = 0; column <= size; column++) {
                solutionMatrix[row][column] = augmentedMatrix[row][column];
            }
        }

        for (int row = size - 1; row >= 0; row--) {
            double sumOfProducts = 0;
            int column;
            for (column = size - 1; column > row; column--) {
                solutionMatrix[row][column] =
                    solutionMatrix[column][column] * solutionMatrix[row][column];
                sumOfProducts += solutionMatrix[row][column];
            }
            if (solutionMatrix[row][row] == 0) {
                solutionMatrix[row][row] = 0;
            } else {
                solutionMatrix[row][row] =
                    (solutionMatrix[row][size] - sumOfProducts) / solutionMatrix[row][row];
            }
            solutionVector.add(solutionMatrix[row][column]);
        }
        return solutionVector;
    }
}