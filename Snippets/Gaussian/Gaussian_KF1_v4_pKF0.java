package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class GaussianElimination {

    private GaussianElimination() {
        // Utility class; prevent instantiation
    }

    /**
     * Solves a system of linear equations using Gaussian elimination.
     *
     * @param size the number of variables/equations
     * @param coefficients the list of coefficients (same for each column in this implementation)
     * @return the solution vector
     */
    public static ArrayList<Double> solve(int size, List<Double> coefficients) {
        double[][] augmentedMatrix = buildAugmentedMatrix(size, coefficients);
        forwardElimination(size, augmentedMatrix);
        return backSubstitution(size, augmentedMatrix);
    }

    private static double[][] buildAugmentedMatrix(int size, List<Double> coefficients) {
        double[][] augmentedMatrix = new double[size + 1][size + 1];

        for (int row = 0; row < size; row++) {
            double coefficient = coefficients.get(row);
            for (int col = 0; col <= size; col++) {
                augmentedMatrix[row][col] = coefficient;
            }
        }

        return augmentedMatrix;
    }

    /**
     * Performs forward elimination on the augmented matrix.
     */
    public static double[][] forwardElimination(int size, double[][] matrix) {
        for (int pivotRow = 0; pivotRow < size - 1; pivotRow++) {
            double pivotValue = matrix[pivotRow][pivotRow];

            for (int row = pivotRow + 1; row < size; row++) {
                double factor = matrix[row][pivotRow] / pivotValue;

                for (int col = pivotRow; col <= size; col++) {
                    matrix[row][col] -= factor * matrix[pivotRow][col];
                }
            }
        }
        return matrix;
    }

    /**
     * Performs back substitution to obtain the solution vector.
     */
    public static ArrayList<Double> backSubstitution(int size, double[][] matrix) {
        double[][] workingMatrix = copyMatrix(size, matrix);
        ArrayList<Double> solution = new ArrayList<>(size);

        for (int row = size - 1; row >= 0; row--) {
            double sum = 0.0;

            for (int col = size - 1; col > row; col--) {
                workingMatrix[row][col] = workingMatrix[col][col] * workingMatrix[row][col];
                sum += workingMatrix[row][col];
            }

            double diagonal = workingMatrix[row][row];
            if (diagonal == 0.0) {
                workingMatrix[row][row] = 0.0;
            } else {
                workingMatrix[row][row] = (workingMatrix[row][size] - sum) / diagonal;
            }

            solution.add(workingMatrix[row][row]);
        }

        return solution;
    }

    private static double[][] copyMatrix(int size, double[][] sourceMatrix) {
        double[][] destinationMatrix = new double[size][size + 1];
        for (int row = 0; row < size; row++) {
            System.arraycopy(sourceMatrix[row], 0, destinationMatrix[row], 0, size + 1);
        }
        return destinationMatrix;
    }
}