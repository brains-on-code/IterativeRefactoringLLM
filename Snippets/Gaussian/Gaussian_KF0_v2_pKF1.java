package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class Gaussian {

    private Gaussian() {
    }

    public static ArrayList<Double> solveLinearSystem(int dimension, List<Double> flatAugmentedMatrix) {
        double[][] augmentedMatrix = new double[dimension][dimension + 1];

        int flatIndex = 0;
        for (int rowIndex = 0; rowIndex < dimension; rowIndex++) {
            for (int columnIndex = 0; columnIndex <= dimension; columnIndex++) {
                augmentedMatrix[rowIndex][columnIndex] = flatAugmentedMatrix.get(flatIndex++);
            }
        }

        performForwardElimination(dimension, augmentedMatrix);
        return performBackSubstitution(dimension, augmentedMatrix);
    }

    private static void performForwardElimination(int dimension, double[][] augmentedMatrix) {
        for (int pivotRowIndex = 0; pivotRowIndex < dimension - 1; pivotRowIndex++) {
            for (int targetRowIndex = pivotRowIndex; targetRowIndex < dimension - 1; targetRowIndex++) {
                double eliminationFactor =
                    augmentedMatrix[targetRowIndex + 1][pivotRowIndex] / augmentedMatrix[pivotRowIndex][pivotRowIndex];

                for (int columnIndex = pivotRowIndex; columnIndex <= dimension; columnIndex++) {
                    augmentedMatrix[targetRowIndex + 1][columnIndex] -=
                        eliminationFactor * augmentedMatrix[pivotRowIndex][columnIndex];
                }
            }
        }
    }

    private static ArrayList<Double> performBackSubstitution(int dimension, double[][] augmentedMatrix) {
        double[][] workingMatrix = new double[dimension][dimension + 1];

        for (int rowIndex = 0; rowIndex < dimension; rowIndex++) {
            for (int columnIndex = 0; columnIndex <= dimension; columnIndex++) {
                workingMatrix[rowIndex][columnIndex] = augmentedMatrix[rowIndex][columnIndex];
            }
        }

        ArrayList<Double> solutionVector = new ArrayList<>();

        for (int rowIndex = dimension - 1; rowIndex >= 0; rowIndex--) {
            double accumulatedSum = 0.0;

            for (int columnIndex = dimension - 1; columnIndex > rowIndex; columnIndex--) {
                workingMatrix[rowIndex][columnIndex] =
                    workingMatrix[columnIndex][columnIndex] * workingMatrix[rowIndex][columnIndex];
                accumulatedSum += workingMatrix[rowIndex][columnIndex];
            }

            if (workingMatrix[rowIndex][rowIndex] == 0) {
                workingMatrix[rowIndex][rowIndex] = 0;
            } else {
                workingMatrix[rowIndex][rowIndex] =
                    (workingMatrix[rowIndex][dimension] - accumulatedSum) / workingMatrix[rowIndex][rowIndex];
            }

            solutionVector.add(workingMatrix[rowIndex][rowIndex]);
        }

        return solutionVector;
    }
}