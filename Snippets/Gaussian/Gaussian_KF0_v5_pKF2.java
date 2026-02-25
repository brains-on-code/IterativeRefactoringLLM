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
     * @param matrix augmented matrix as a flat list in row-major order
     * @return solution vector
     */
    public static ArrayList<Double> gaussian(int size, List<Double> matrix) {
        double[][] augmentedMatrix = toAugmentedMatrix(size, matrix);
        forwardElimination(size, augmentedMatrix);
        return backSubstitution(size, augmentedMatrix);
    }

    /**
     * Converts a flat list representation of an augmented matrix into a 2D array.
     *
     * @param size       number of variables (and equations)
     * @param flatMatrix augmented matrix as a flat list in row-major order
     * @return 2D augmented matrix of dimensions size x (size + 1)
     */
    private static double[][] toAugmentedMatrix(int size, List<Double> flatMatrix) {
        double[][] matrix = new double[size][size + 1];
        int index = 0;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                matrix[row][col] = flatMatrix.get(index++);
            }
        }

        return matrix;
    }

    /**
     * Performs forward elimination to convert the matrix to upper triangular form.
     *
     * @param size   number of variables (and equations)
     * @param matrix augmented matrix to be transformed in-place
     */
    private static void forwardElimination(int size, double[][] matrix) {
        for (int pivot = 0; pivot < size - 1; pivot++) {
            for (int row = pivot + 1; row < size; row++) {
                double factor = matrix[row][pivot] / matrix[pivot][pivot];

                for (int col = pivot; col <= size; col++) {
                    matrix[row][col] -= factor * matrix[pivot][col];
                }
            }
        }
    }

    /**
     * Performs back substitution on an upper triangular matrix to find the solution vector.
     *
     * @param size   number of variables (and equations)
     * @param matrix upper triangular augmented matrix
     * @return solution vector as a list of doubles
     */
    private static ArrayList<Double> backSubstitution(int size, double[][] matrix) {
        double[] solution = new double[size];

        for (int row = size - 1; row >= 0; row--) {
            double sum = 0.0;

            for (int col = row + 1; col < size; col++) {
                sum += matrix[row][col] * solution[col];
            }

            double diagonal = matrix[row][row];
            solution[row] = (diagonal == 0)
                ? 0.0
                : (matrix[row][size] - sum) / diagonal;
        }

        ArrayList<Double> result = new ArrayList<>(size);
        for (double value : solution) {
            result.add(value);
        }
        return result;
    }
}