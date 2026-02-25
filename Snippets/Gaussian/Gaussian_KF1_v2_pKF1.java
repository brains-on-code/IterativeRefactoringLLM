package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class GaussianEliminationSolver {

    private GaussianEliminationSolver() {
    }

    public static ArrayList<Double> solve(int dimension, List<Double> coefficients) {
        double[][] augmentedMatrix = new double[dimension + 1][dimension + 1];
        double[][] solutionMatrix = new double[dimension][dimension + 1];

        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column <= dimension; column++) {
                augmentedMatrix[row][column] = coefficients.get(row);
            }
        }

        augmentedMatrix = forwardElimination(dimension, augmentedMatrix);
        return backSubstitution(dimension, solutionMatrix, augmentedMatrix);
    }

    public static double[][] forwardElimination(int dimension, double[][] matrix) {
        for (int pivotRow = 0; pivotRow < dimension - 1; pivotRow++) {
            for (int row = pivotRow; row < dimension - 1; row++) {
                double eliminationFactor = matrix[row + 1][pivotRow] / matrix[pivotRow][pivotRow];

                for (int column = pivotRow; column <= dimension; column++) {
                    matrix[row + 1][column] =
                        matrix[row + 1][column] - (eliminationFactor * matrix[pivotRow][column]);
                }
            }
        }
        return matrix;
    }

    public static ArrayList<Double> backSubstitution(int dimension, double[][] solutionMatrix, double[][] matrix) {
        ArrayList<Double> solutions = new ArrayList<>();

        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column <= dimension; column++) {
                solutionMatrix[row][column] = matrix[row][column];
            }
        }

        for (int row = dimension - 1; row >= 0; row--) {
            double sum = 0;
            for (int column = dimension - 1; column > row; column--) {
                solutionMatrix[row][column] =
                    solutionMatrix[column][column] * solutionMatrix[row][column];
                sum = solutionMatrix[row][column] + sum;
            }
            if (solutionMatrix[row][row] == 0) {
                solutionMatrix[row][row] = 0;
            } else {
                solutionMatrix[row][row] =
                    (solutionMatrix[row][dimension] - sum) / (solutionMatrix[row][row]);
            }
            solutions.add(solutionMatrix[row][row]);
        }
        return solutions;
    }
}