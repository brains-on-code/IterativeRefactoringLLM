package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class GaussianEliminationSolver {

    private GaussianEliminationSolver() {
    }

    public static ArrayList<Double> solve(int size, List<Double> coefficients) {
        double[][] augmentedMatrix = new double[size + 1][size + 1];
        double[][] workingMatrix = new double[size][size + 1];

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex <= size; columnIndex++) {
                augmentedMatrix[rowIndex][columnIndex] = coefficients.get(rowIndex);
            }
        }

        augmentedMatrix = performForwardElimination(size, augmentedMatrix);
        return performBackSubstitution(size, workingMatrix, augmentedMatrix);
    }

    public static double[][] performForwardElimination(int size, double[][] matrix) {
        for (int pivotRowIndex = 0; pivotRowIndex < size - 1; pivotRowIndex++) {
            for (int currentRowIndex = pivotRowIndex; currentRowIndex < size - 1; currentRowIndex++) {
                double eliminationFactor =
                    matrix[currentRowIndex + 1][pivotRowIndex] / matrix[pivotRowIndex][pivotRowIndex];

                for (int columnIndex = pivotRowIndex; columnIndex <= size; columnIndex++) {
                    matrix[currentRowIndex + 1][columnIndex] =
                        matrix[currentRowIndex + 1][columnIndex]
                            - (eliminationFactor * matrix[pivotRowIndex][columnIndex]);
                }
            }
        }
        return matrix;
    }

    public static ArrayList<Double> performBackSubstitution(
        int size, double[][] workingMatrix, double[][] matrix) {

        ArrayList<Double> solutionVector = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex <= size; columnIndex++) {
                workingMatrix[rowIndex][columnIndex] = matrix[rowIndex][columnIndex];
            }
        }

        for (int rowIndex = size - 1; rowIndex >= 0; rowIndex--) {
            double sum = 0;
            for (int columnIndex = size - 1; columnIndex > rowIndex; columnIndex--) {
                workingMatrix[rowIndex][columnIndex] =
                    workingMatrix[columnIndex][columnIndex] * workingMatrix[rowIndex][columnIndex];
                sum = workingMatrix[rowIndex][columnIndex] + sum;
            }
            if (workingMatrix[rowIndex][rowIndex] == 0) {
                workingMatrix[rowIndex][rowIndex] = 0;
            } else {
                workingMatrix[rowIndex][rowIndex] =
                    (workingMatrix[rowIndex][size] - sum) / (workingMatrix[rowIndex][rowIndex]);
            }
            solutionVector.add(workingMatrix[rowIndex][rowIndex]);
        }
        return solutionVector;
    }
}