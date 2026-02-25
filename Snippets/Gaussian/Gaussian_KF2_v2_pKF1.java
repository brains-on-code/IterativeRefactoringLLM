package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class Gaussian {

    private Gaussian() {
    }

    public static ArrayList<Double> solveGaussianSystem(int size, List<Double> flatAugmentedMatrix) {
        double[][] augmentedMatrix = new double[size][size + 1];
        double[][] solutionMatrix = new double[size][size + 1];

        int index = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                augmentedMatrix[row][col] = flatAugmentedMatrix.get(index++);
            }
        }

        performGaussianElimination(size, augmentedMatrix);
        return performBackSubstitution(size, solutionMatrix, augmentedMatrix);
    }

    public static double[][] performGaussianElimination(int size, double[][] augmentedMatrix) {
        for (int pivotRow = 0; pivotRow < size - 1; pivotRow++) {
            for (int row = pivotRow; row < size - 1; row++) {
                double eliminationFactor = augmentedMatrix[row + 1][pivotRow] / augmentedMatrix[pivotRow][pivotRow];

                for (int col = pivotRow; col <= size; col++) {
                    augmentedMatrix[row + 1][col] =
                        augmentedMatrix[row + 1][col] - (eliminationFactor * augmentedMatrix[pivotRow][col]);
                }
            }
        }
        return augmentedMatrix;
    }

    public static ArrayList<Double> performBackSubstitution(int size, double[][] solutionMatrix, double[][] augmentedMatrix) {
        ArrayList<Double> solutionVector = new ArrayList<>();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                solutionMatrix[row][col] = augmentedMatrix[row][col];
            }
        }

        for (int row = size - 1; row >= 0; row--) {
            double sum = 0;
            int col;
            for (col = size - 1; col > row; col--) {
                solutionMatrix[row][col] = solutionMatrix[col][col] * solutionMatrix[row][col];
                sum += solutionMatrix[row][col];
            }
            if (solutionMatrix[row][row] == 0) {
                solutionMatrix[row][row] = 0;
            } else {
                solutionMatrix[row][row] = (solutionMatrix[row][size] - sum) / solutionMatrix[row][row];
            }
            solutionVector.add(solutionMatrix[row][col]);
        }
        return solutionVector;
    }
}