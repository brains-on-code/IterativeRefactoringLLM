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
     * @param size         number of variables/equations
     * @param coefficients list of coefficients (same value repeated across each row in this implementation)
     * @return list of solutions
     */
    public static ArrayList<Double> solve(int size, List<Double> coefficients) {
        double[][] augmentedMatrix = new double[size + 1][size + 1];
        double[][] workingMatrix = new double[size][size + 1];

        for (int row = 0; row < size; row++) {
            double value = coefficients.get(row);
            for (int col = 0; col <= size; col++) {
                augmentedMatrix[row][col] = value;
            }
        }

        double[][] upperTriangular = forwardElimination(size, augmentedMatrix);
        return backSubstitution(size, workingMatrix, upperTriangular);
    }

    /**
     * Converts the given augmented matrix to upper triangular form.
     *
     * @param size   number of variables/equations
     * @param matrix augmented matrix
     * @return matrix in upper triangular form
     */
    public static double[][] forwardElimination(int size, double[][] matrix) {
        for (int pivot = 0; pivot < size - 1; pivot++) {
            for (int row = pivot; row < size - 1; row++) {
                double factor = matrix[row + 1][pivot] / matrix[pivot][pivot];
                for (int col = pivot; col <= size; col++) {
                    matrix[row + 1][col] -= factor * matrix[pivot][col];
                }
            }
        }
        return matrix;
    }

    /**
     * Performs back substitution on an upper triangular matrix to find the solution vector.
     *
     * @param size                  number of variables/equations
     * @param workingMatrix         temporary matrix used during back substitution
     * @param upperTriangularMatrix matrix in upper triangular form
     * @return list of solutions
     */
    public static ArrayList<Double> backSubstitution(
        int size,
        double[][] workingMatrix,
        double[][] upperTriangularMatrix
    ) {
        ArrayList<Double> solutions = new ArrayList<>();

        for (int row = 0; row < size; row++) {
            System.arraycopy(upperTriangularMatrix[row], 0, workingMatrix[row], 0, size + 1);
        }

        for (int row = size - 1; row >= 0; row--) {
            double sum = 0;
            for (int col = size - 1; col > row; col--) {
                workingMatrix[row][col] = workingMatrix[col][col] * workingMatrix[row][col];
                sum += workingMatrix[row][col];
            }

            if (workingMatrix[row][row] == 0) {
                workingMatrix[row][row] = 0;
            } else {
                workingMatrix[row][row] =
                    (workingMatrix[row][size] - sum) / workingMatrix[row][row];
            }

            solutions.add(workingMatrix[row][row]);
        }

        return solutions;
    }
}