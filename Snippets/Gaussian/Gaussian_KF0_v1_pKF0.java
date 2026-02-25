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
        double[][] mat = new double[size][size + 1];

        // Fill matrix from list (row-major order)
        int index = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                mat[row][col] = matrix.get(index++);
            }
        }

        gaussianElimination(size, mat);
        return backSubstitution(size, mat);
    }

    /**
     * Perform forward elimination to convert the matrix to upper triangular form.
     */
    private static void gaussianElimination(int size, double[][] mat) {
        for (int pivot = 0; pivot < size - 1; pivot++) {
            for (int row = pivot + 1; row < size; row++) {
                double factor = mat[row][pivot] / mat[pivot][pivot];
                for (int col = pivot; col <= size; col++) {
                    mat[row][col] -= factor * mat[pivot][col];
                }
            }
        }
    }

    /**
     * Perform back substitution to compute the solution vector.
     */
    private static ArrayList<Double> backSubstitution(int size, double[][] mat) {
        double[] solution = new double[size];

        for (int row = size - 1; row >= 0; row--) {
            double sum = 0.0;
            for (int col = row + 1; col < size; col++) {
                sum += mat[row][col] * solution[col];
            }

            double diagonal = mat[row][row];
            if (diagonal == 0.0) {
                solution[row] = 0.0; // or handle singular matrix appropriately
            } else {
                solution[row] = (mat[row][size] - sum) / diagonal;
            }
        }

        ArrayList<Double> result = new ArrayList<>(size);
        for (double value : solution) {
            result.add(value);
        }
        return result;
    }
}