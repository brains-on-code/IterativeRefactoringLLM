package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class Gaussian {

    private Gaussian() {
        // Utility class
    }

    /**
     * Solves a system of linear equations using Gaussian elimination.
     *
     * @param size   the number of variables (and equations)
     * @param matrix the augmented matrix as a flat list in row-major order
     *               of length size * (size + 1)
     * @return the solution vector
     */
    public static ArrayList<Double> gaussian(int size, List<Double> matrix) {
        double[][] augmentedMatrix = buildAugmentedMatrix(size, matrix);
        performGaussianElimination(size, augmentedMatrix);
        return performBackSubstitution(size, augmentedMatrix);
    }

    private static double[][] buildAugmentedMatrix(int size, List<Double> flatMatrix) {
        double[][] matrix = new double[size][size + 1];
        int index = 0;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                matrix[row][col] = flatMatrix.get(index++);
            }
        }

        return matrix;
    }

    private static void performGaussianElimination(int size, double[][] matrix) {
        for (int pivot = 0; pivot < size - 1; pivot++) {
            for (int row = pivot + 1; row < size; row++) {
                double factor = matrix[row][pivot] / matrix[pivot][pivot];
                for (int col = pivot; col <= size; col++) {
                    matrix[row][col] -= factor * matrix[pivot][col];
                }
            }
        }
    }

    private static ArrayList<Double> performBackSubstitution(int size, double[][] matrix) {
        double[][] workingMatrix = copyMatrix(size, matrix);
        ArrayList<Double> solution = new ArrayList<>(size);

        for (int row = size - 1; row >= 0; row--) {
            double sum = 0.0;

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

            solution.add(0, workingMatrix[row][row]);
        }

        return solution;
    }

    private static double[][] copyMatrix(int size, double[][] source) {
        double[][] destination = new double[size][size + 1];
        for (int row = 0; row < size; row++) {
            System.arraycopy(source[row], 0, destination[row], 0, size + 1);
        }
        return destination;
    }
}