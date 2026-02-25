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
        double[][] mat = new double[size][size + 1];
        double[][] x = new double[size][size + 1];

        // Fill matrix from flat list (row-major)
        int index = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                mat[row][col] = matrix.get(index++);
            }
        }

        gaussianElimination(size, mat);
        return backSubstitution(size, x, mat);
    }

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

    private static ArrayList<Double> backSubstitution(int size, double[][] x, double[][] mat) {
        ArrayList<Double> solution = new ArrayList<>();

        // Copy matrix to x
        for (int row = 0; row < size; row++) {
            System.arraycopy(mat[row], 0, x[row], 0, size + 1);
        }

        // Back substitution
        for (int row = size - 1; row >= 0; row--) {
            double sum = 0;
            for (int col = size - 1; col > row; col--) {
                x[row][col] = x[col][col] * x[row][col];
                sum += x[row][col];
            }

            if (x[row][row] == 0) {
                x[row][row] = 0;
            } else {
                x[row][row] = (x[row][size] - sum) / x[row][row];
            }

            solution.add(0, x[row][row]);
        }

        return solution;
    }
}