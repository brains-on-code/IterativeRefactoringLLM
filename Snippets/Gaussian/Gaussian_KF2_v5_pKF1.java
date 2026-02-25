package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class Gaussian {

    private Gaussian() {
    }

    public static ArrayList<Double> solveGaussianSystem(int dimension, List<Double> flattenedAugmentedMatrix) {
        double[][] augmentedMatrix = new double[dimension][dimension + 1];
        double[][] workingMatrix = new double[dimension][dimension + 1];

        int flatIndex = 0;
        for (int rowIndex = 0; rowIndex < dimension; rowIndex++) {
            for (int columnIndex = 0; columnIndex <= dimension; columnIndex++) {
                augmentedMatrix[rowIndex][columnIndex] = flattenedAugmentedMatrix.get(flatIndex++);
            }
        }

        performGaussianElimination(dimension, augmentedMatrix);
        return performBackSubstitution(dimension, workingMatrix, augmentedMatrix);
    }

    public static double[][] performGaussianElimination(int dimension, double[][] augmentedMatrix) {
        for (int pivotRowIndex = 0; pivotRowIndex < dimension - 1; pivotRowIndex++) {
            for (int targetRowIndex = pivotRowIndex; targetRowIndex < dimension - 1; targetRowIndex++) {
                double eliminationFactor =
                    augmentedMatrix[targetRowIndex + 1][pivotRowIndex] / augmentedMatrix[pivotRowIndex][pivotRowIndex];

                for (int columnIndex = pivotRowIndex; columnIndex <= dimension; columnIndex++) {
                    augmentedMatrix[targetRowIndex + 1][columnIndex] =
                        augmentedMatrix[targetRowIndex + 1][columnIndex]
                            - (eliminationFactor * augmentedMatrix[pivotRowIndex][columnIndex]);
                }
            }
        }
        return augmentedMatrix;
    }

    public static ArrayList<Double> performBackSubstitution(
        int dimension, double[][] workingMatrix, double[][] augmentedMatrix) {

        ArrayList<Double> solutionVector = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < dimension; rowIndex++) {
            for (int columnIndex = 0; columnIndex <= dimension; columnIndex++) {
                workingMatrix[rowIndex][columnIndex] = augmentedMatrix[rowIndex][columnIndex];
            }
        }

        for (int rowIndex = dimension - 1; rowIndex >= 0; rowIndex--) {
            double sumOfProducts = 0;
            int columnIndex;
            for (columnIndex = dimension - 1; columnIndex > rowIndex; columnIndex--) {
                workingMatrix[rowIndex][columnIndex] =
                    workingMatrix[columnIndex][columnIndex] * workingMatrix[rowIndex][columnIndex];
                sumOfProducts += workingMatrix[rowIndex][columnIndex];
            }
            if (workingMatrix[rowIndex][rowIndex] == 0) {
                workingMatrix[rowIndex][rowIndex] = 0;
            } else {
                workingMatrix[rowIndex][rowIndex] =
                    (workingMatrix[rowIndex][dimension] - sumOfProducts) / workingMatrix[rowIndex][rowIndex];
            }
            solutionVector.add(workingMatrix[rowIndex][columnIndex]);
        }
        return solutionVector;
    }
}