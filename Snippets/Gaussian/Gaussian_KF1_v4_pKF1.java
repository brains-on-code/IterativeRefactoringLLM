package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class GaussianEliminationSolver {

    private GaussianEliminationSolver() {
    }

    public static ArrayList<Double> solve(int dimension, List<Double> coefficients) {
        double[][] augmentedMatrix = new double[dimension + 1][dimension + 1];
        double[][] upperTriangularMatrix = new double[dimension][dimension + 1];

        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column <= dimension; column++) {
                augmentedMatrix[row][column] = coefficients.get(row);
            }
        }

        augmentedMatrix = performForwardElimination(dimension, augmentedMatrix);
        return performBackSubstitution(dimension, upperTriangularMatrix, augmentedMatrix);
    }

    public static double[][] performForwardElimination(int dimension, double[][] matrix) {
        for (int pivotRow = 0; pivotRow < dimension - 1; pivotRow++) {
            for (int targetRow = pivotRow; targetRow < dimension - 1; targetRow++) {
                double eliminationFactor =
                    matrix[targetRow + 1][pivotRow] / matrix[pivotRow][pivotRow];

                for (int column = pivotRow; column <= dimension; column++) {
                    matrix[targetRow + 1][column] =
                        matrix[targetRow + 1][column]
                            - (eliminationFactor * matrix[pivotRow][column]);
                }
            }
        }
        return matrix;
    }

    public static ArrayList<Double> performBackSubstitution(
        int dimension, double[][] upperTriangularMatrix, double[][] matrix) {

        ArrayList<Double> solutionVector = new ArrayList<>();

        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column <= dimension; column++) {
                upperTriangularMatrix[row][column] = matrix[row][column];
            }
        }

        for (int row = dimension - 1; row >= 0; row--) {
            double sum = 0;
            for (int column = dimension - 1; column > row; column--) {
                upperTriangularMatrix[row][column] =
                    upperTriangularMatrix[column][column] * upperTriangularMatrix[row][column];
                sum = upperTriangularMatrix[row][column] + sum;
            }
            if (upperTriangularMatrix[row][row] == 0) {
                upperTriangularMatrix[row][row] = 0;
            } else {
                upperTriangularMatrix[row][row] =
                    (upperTriangularMatrix[row][dimension] - sum)
                        / (upperTriangularMatrix[row][row]);
            }
            solutionVector.add(upperTriangularMatrix[row][row]);
        }
        return solutionVector;
    }
}