package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class Gaussian {

    private Gaussian() {
    }

    public static ArrayList<Double> solveLinearSystem(int size, List<Double> flatAugmentedMatrix) {
        double[][] augmentedMatrix = new double[size][size + 1];

        int flatIndex = 0;
        for (int row = 0; row < size; row++) {
            for (int column = 0; column <= size; column++) {
                augmentedMatrix[row][column] = flatAugmentedMatrix.get(flatIndex++);
            }
        }

        performForwardElimination(size, augmentedMatrix);
        return performBackSubstitution(size, augmentedMatrix);
    }

    private static void performForwardElimination(int size, double[][] augmentedMatrix) {
        for (int pivotRow = 0; pivotRow < size - 1; pivotRow++) {
            for (int targetRow = pivotRow; targetRow < size - 1; targetRow++) {
                double eliminationFactor =
                    augmentedMatrix[targetRow + 1][pivotRow]
                        / augmentedMatrix[pivotRow][pivotRow];

                for (int column = pivotRow; column <= size; column++) {
                    augmentedMatrix[targetRow + 1][column] -=
                        eliminationFactor * augmentedMatrix[pivotRow][column];
                }
            }
        }
    }

    private static ArrayList<Double> performBackSubstitution(int size, double[][] augmentedMatrix) {
        double[][] workingMatrix = new double[size][size + 1];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column <= size; column++) {
                workingMatrix[row][column] = augmentedMatrix[row][column];
            }
        }

        ArrayList<Double> solutionVector = new ArrayList<>();

        for (int row = size - 1; row >= 0; row--) {
            double sumOfKnownTerms = 0.0;

            for (int column = size - 1; column > row; column--) {
                workingMatrix[row][column] =
                    workingMatrix[column][column] * workingMatrix[row][column];
                sumOfKnownTerms += workingMatrix[row][column];
            }

            if (workingMatrix[row][row] == 0) {
                workingMatrix[row][row] = 0;
            } else {
                workingMatrix[row][row] =
                    (workingMatrix[row][size] - sumOfKnownTerms)
                        / workingMatrix[row][row];
            }

            solutionVector.add(workingMatrix[row][row]);
        }

        return solutionVector;
    }
}