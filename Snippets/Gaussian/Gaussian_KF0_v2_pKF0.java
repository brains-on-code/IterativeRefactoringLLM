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
     * @param matrix the augmented matrix in row-major order (size * (size + 1) elements)
     * @return the solution vector as an ArrayList<Double>
     */
    public static ArrayList<Double> gaussian(int size, List<Double> matrix) {
        double[][] augmentedMatrix = buildAugmentedMatrix(size, matrix);
        performForwardElimination(size, augmentedMatrix);
        return performBackSubstitution(size, augmentedMatrix);
    }

    private static double[][] buildAugmentedMatrix(int size, List<Double> matrix) {
        double[][] augmentedMatrix = new double[size][size + 1];
        int index = 0;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                augmentedMatrix[row][col] = matrix.get(index++);
            }
        }

        return augmentedMatrix;
    }

    /**
     * Perform forward elimination to convert the matrix to upper triangular form.
     */
    private static void performForwardElimination(int size, double[][] matrix) {
        for (int pivotRow = 0; pivotRow < size - 1; pivotRow++) {
            double pivotValue = matrix[pivotRow][pivotRow];

            for (int row = pivotRow + 1; row < size; row++) {
                double factor = matrix[row][pivotRow] / pivotValue;

                for (int col = pivotRow; col <= size; col++) {
                    matrix[row][col] -= factor * matrix[pivotRow][col];
                }
            }
        }
    }

    /**
     * Perform back substitution to compute the solution vector.
     */
    private static ArrayList<Double> performBackSubstitution(int size, double[][] matrix) {
        double[] solution = new double[size];

        for (int row = size - 1; row >= 0; row--) {
            double sum = 0.0;

            for (int col = row + 1; col < size; col++) {
                sum += matrix[row][col] * solution[col];
            }

            double diagonal = matrix[row][row];
            solution[row] = (diagonal == 0.0) ? 0.0 : (matrix[row][size] - sum) / diagonal;
        }

        ArrayList<Double> result = new ArrayList<>(size);
        for (double value : solution) {
            result.add(value);
        }
        return result;
    }
}