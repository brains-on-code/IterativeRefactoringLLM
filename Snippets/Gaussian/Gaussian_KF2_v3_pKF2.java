package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class Gaussian {

    private Gaussian() {
        // Utility class; prevent instantiation
    }

    /**
     * Solves a system of linear equations using Gaussian elimination.
     *
     * @param size   number of variables (and equations)
     * @param matrix augmented matrix in row-major order,
     *               of length size * (size + 1)
     * @return solution vector
     */
    public static ArrayList<Double> gaussian(int size, List<Double> matrix) {
        double[][] augmentedMatrix = to2dAugmentedMatrix(size, matrix);
        gaussianElimination(size, augmentedMatrix);
        return backSubstitution(size, augmentedMatrix);
    }

    private static double[][] to2dAugmentedMatrix(int size, List<Double> matrix) {
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
     * Forward elimination: convert the matrix to upper triangular form.
     *
     * @param size            number of variables (and equations)
     * @param augmentedMatrix augmented matrix to be transformed
     */
    public static void gaussianElimination(int size, double[][] augmentedMatrix) {
        for (int pivotRow = 0; pivotRow < size - 1; pivotRow++) {
            double pivotValue = augmentedMatrix[pivotRow][pivotRow];

            for (int row = pivotRow + 1; row < size; row++) {
                double factor = augmentedMatrix[row][pivotRow] / pivotValue;

                for (int col = pivotRow; col <= size; col++) {
                    augmentedMatrix[row][col] -= factor * augmentedMatrix[pivotRow][col];
                }
            }
        }
    }

    /**
     * Back substitution on an upper triangular matrix to find the solution vector.
     *
     * @param size            number of variables (and equations)
     * @param augmentedMatrix upper triangular augmented matrix
     * @return solution vector
     */
    public static ArrayList<Double> backSubstitution(int size, double[][] augmentedMatrix) {
        double[] solution = new double[size];

        for (int row = size - 1; row >= 0; row--) {
            double sum = 0.0;

            for (int col = row + 1; col < size; col++) {
                sum += augmentedMatrix[row][col] * solution[col];
            }

            double diagonal = augmentedMatrix[row][row];
            solution[row] = (diagonal == 0.0)
                ? 0.0
                : (augmentedMatrix[row][size] - sum) / diagonal;
        }

        ArrayList<Double> result = new ArrayList<>(size);
        for (double value : solution) {
            result.add(value);
        }
        return result;
    }
}