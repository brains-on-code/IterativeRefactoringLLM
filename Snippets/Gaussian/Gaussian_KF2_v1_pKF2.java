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
     * @param size   the number of variables (and equations)
     * @param matrix the augmented matrix as a flat list in row-major order,
     *               of length size * (size + 1)
     * @return the solution vector
     */
    public static ArrayList<Double> gaussian(int size, List<Double> matrix) {
        double[][] augmentedMatrix = new double[size][size + 1];

        // Fill augmentedMatrix from the flat list in row-major order
        int index = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                augmentedMatrix[row][col] = matrix.get(index++);
            }
        }

        gaussianElimination(size, augmentedMatrix);
        return backSubstitution(size, augmentedMatrix);
    }

    /**
     * Performs forward elimination to convert the matrix to upper triangular form.
     *
     * @param size           the number of variables (and equations)
     * @param augmentedMatrix the augmented matrix to be transformed
     */
    public static void gaussianElimination(int size, double[][] augmentedMatrix) {
        for (int pivot = 0; pivot < size - 1; pivot++) {
            for (int row = pivot + 1; row < size; row++) {
                double factor = augmentedMatrix[row][pivot] / augmentedMatrix[pivot][pivot];

                for (int col = pivot; col <= size; col++) {
                    augmentedMatrix[row][col] -= factor * augmentedMatrix[pivot][col];
                }
            }
        }
    }

    /**
     * Performs back substitution on an upper triangular matrix to find the solution vector.
     *
     * @param size            the number of variables (and equations)
     * @param augmentedMatrix the upper triangular augmented matrix
     * @return the solution vector
     */
    public static ArrayList<Double> backSubstitution(int size, double[][] augmentedMatrix) {
        double[] solution = new double[size];

        for (int row = size - 1; row >= 0; row--) {
            double sum = 0.0;

            for (int col = row + 1; col < size; col++) {
                sum += augmentedMatrix[row][col] * solution[col];
            }

            double diagonal = augmentedMatrix[row][row];
            if (diagonal == 0.0) {
                solution[row] = 0.0;
            } else {
                solution[row] = (augmentedMatrix[row][size] - sum) / diagonal;
            }
        }

        ArrayList<Double> result = new ArrayList<>(size);
        for (double value : solution) {
            result.add(value);
        }
        return result;
    }
}