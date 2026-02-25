package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class GaussianEliminationSolver {

    private GaussianEliminationSolver() {
    }

    public static ArrayList<Double> solve(int size, List<Double> coefficients) {
        double[][] augmentedMatrix = new double[size + 1][size + 1];
        double[][] upperTriangularMatrix = new double[size][size + 1];

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex <= size; columnIndex++) {
                augmentedMatrix[rowIndex][columnIndex] = coefficients.get(rowIndex);
            }
        }

        augmentedMatrix = performForwardElimination(size, augmentedMatrix);
        return performBackSubstitution(size, upperTriangularMatrix, augmentedMatrix);
    }

    public static double[][] performForwardElimination(int size, double[][] matrix) {
        for (int pivotRowIndex = 0; pivotRowIndex < size - 1; pivotRowIndex++) {
            for (int targetRowIndex = pivotRowIndex; targetRowIndex < size - 1; targetRowIndex++) {
                double eliminationFactor =
                    matrix[targetRowIndex + 1][pivotRowIndex] / matrix[pivotRowIndex][pivotRowIndex];

                for (int columnIndex = pivotRowIndex; columnIndex <= size; columnIndex++) {
                    matrix[targetRowIndex + 1][columnIndex] =
                        matrix[targetRowIndex + 1][columnIndex]
                            - (eliminationFactor * matrix[pivotRowIndex][columnIndex]);
                }
            }
        }
        return matrix;
    }

    public static ArrayList<Double> performBackSubstitution(
        int size, double[][] upperTriangularMatrix, double[][] matrix) {

        ArrayList<Double> solutionVector = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex <= size; columnIndex++) {
                upperTriangularMatrix[rowIndex][columnIndex] = matrix[rowIndex][columnIndex];
            }
        }

        for (int rowIndex = size - 1; rowIndex >= 0; rowIndex--) {
            double sum = 0;
            for (int columnIndex = size - 1; columnIndex > rowIndex; columnIndex--) {
                upperTriangularMatrix[rowIndex][columnIndex] =
                    upperTriangularMatrix[columnIndex][columnIndex]
                        * upperTriangularMatrix[rowIndex][columnIndex];
                sum = upperTriangularMatrix[rowIndex][columnIndex] + sum;
            }
            if (upperTriangularMatrix[rowIndex][rowIndex] == 0) {
                upperTriangularMatrix[rowIndex][rowIndex] = 0;
            } else {
                upperTriangularMatrix[rowIndex][rowIndex] =
                    (upperTriangularMatrix[rowIndex][size] - sum)
                        / (upperTriangularMatrix[rowIndex][rowIndex]);
            }
            solutionVector.add(upperTriangularMatrix[rowIndex][rowIndex]);
        }
        return solutionVector;
    }
}