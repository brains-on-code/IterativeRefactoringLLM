package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class GaussianEliminationSolver {

    private GaussianEliminationSolver() {
    }

    public static ArrayList<Double> solve(int size, List<Double> coefficients) {
        int rowIndex;
        int columnIndex = 0;

        double[][] augmentedMatrix = new double[size + 1][size + 1];
        double[][] workingMatrix = new double[size][size + 1];

        for (rowIndex = 0; rowIndex < size; rowIndex++) {
            for (columnIndex = 0; columnIndex <= size; columnIndex++) {
                augmentedMatrix[rowIndex][columnIndex] = coefficients.get(rowIndex);
            }
        }

        augmentedMatrix = forwardElimination(size, rowIndex, augmentedMatrix);
        return backSubstitution(size, workingMatrix, augmentedMatrix);
    }

    public static double[][] forwardElimination(int size, int rowIndex, double[][] matrix) {
        int pivotRow = 0;
        for (pivotRow = 0; pivotRow < size - 1; pivotRow++) {
            for (rowIndex = pivotRow; rowIndex < size - 1; rowIndex++) {
                double factor = matrix[rowIndex + 1][pivotRow] / matrix[pivotRow][pivotRow];

                for (int columnIndex = pivotRow; columnIndex <= size; columnIndex++) {
                    matrix[rowIndex + 1][columnIndex] =
                        matrix[rowIndex + 1][columnIndex] - (factor * matrix[pivotRow][columnIndex]);
                }
            }
        }
        return matrix;
    }

    public static ArrayList<Double> backSubstitution(int size, double[][] workingMatrix, double[][] matrix) {
        ArrayList<Double> solutions = new ArrayList<>();
        int rowIndex;
        int columnIndex;

        for (rowIndex = 0; rowIndex < size; rowIndex++) {
            for (columnIndex = 0; columnIndex <= size; columnIndex++) {
                workingMatrix[rowIndex][columnIndex] = matrix[rowIndex][columnIndex];
            }
        }

        for (rowIndex = size - 1; rowIndex >= 0; rowIndex--) {
            double sum = 0;
            for (columnIndex = size - 1; columnIndex > rowIndex; columnIndex--) {
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
            solutions.add(workingMatrix[rowIndex][columnIndex]);
        }
        return solutions;
    }
}