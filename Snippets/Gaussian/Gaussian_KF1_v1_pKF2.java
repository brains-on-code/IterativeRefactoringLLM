package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Solves a system of linear equations using Gaussian elimination.
     *
     * @param size the number of variables/equations
     * @param coefficients the list of coefficients (same value repeated across each row in this implementation)
     * @return the list of solutions
     */
    public static ArrayList<Double> method1(int size, List<Double> coefficients) {
        double[][] augmentedMatrix = new double[size + 1][size + 1];
        double[][] workingMatrix = new double[size][size + 1];

        // Initialize augmented matrix with the given coefficients
        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                augmentedMatrix[row][col] = coefficients.get(row);
            }
        }

        augmentedMatrix = method2(size, augmentedMatrix);
        return method3(size, workingMatrix, augmentedMatrix);
    }

    /**
     * Performs forward elimination to convert the matrix to an upper triangular form.
     *
     * @param size the number of variables/equations
     * @param matrix the augmented matrix
     * @return the matrix in upper triangular form
     */
    public static double[][] method2(int size, double[][] matrix) {
        for (int pivot = 0; pivot < size - 1; pivot++) {
            for (int row = pivot; row < size - 1; row++) {
                double factor = matrix[row + 1][pivot] / matrix[pivot][pivot];

                for (int col = pivot; col <= size; col++) {
                    matrix[row + 1][col] = matrix[row + 1][col] - (factor * matrix[pivot][col]);
                }
            }
        }
        return matrix;
    }

    /**
     * Performs back substitution on an upper triangular matrix to find the solution vector.
     *
     * @param size the number of variables/equations
     * @param workingMatrix a temporary matrix used during back substitution
     * @param upperTriangularMatrix the matrix in upper triangular form
     * @return the list of solutions
     */
    public static ArrayList<Double> method3(int size, double[][] workingMatrix, double[][] upperTriangularMatrix) {
        ArrayList<Double> solutions = new ArrayList<>();

        // Copy upper triangular matrix into working matrix
        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                workingMatrix[row][col] = upperTriangularMatrix[row][col];
            }
        }

        // Back substitution
        for (int row = size - 1; row >= 0; row--) {
            double sum = 0;
            for (int col = size - 1; col > row; col--) {
                workingMatrix[row][col] = workingMatrix[col][col] * workingMatrix[row][col];
                sum += workingMatrix[row][col];
            }

            if (workingMatrix[row][row] == 0) {
                workingMatrix[row][row] = 0;
            } else {
                workingMatrix[row][row] = (workingMatrix[row][size] - sum) / workingMatrix[row][row];
            }

            solutions.add(workingMatrix[row][row]);
        }

        return solutions;
    }
}