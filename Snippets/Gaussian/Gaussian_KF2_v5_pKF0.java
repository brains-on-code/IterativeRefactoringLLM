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
        eliminateToUpperTriangularForm(size, augmentedMatrix);
        return backSubstitute(size, augmentedMatrix);
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

    private static void eliminateToUpperTriangularForm(int size, double[][] matrix) {
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

    private static ArrayList<Double> backSubstitute(int size, double[][] matrix) {
        double[][] workingMatrix = copyMatrix(size, matrix);
        ArrayList<Double> solution = new ArrayList<>(size);

        for (int row = size - 1; row >= 0; row--) {
            double sum = 0.0;

            for (int col = size - 1; col > row; col--) {
                workingMatrix[row][col] *= workingMatrix[col][col];
                sum += workingMatrix[row][col];
            }

            double diagonal = workingMatrix[row][row];
            workingMatrix[row][row] = (diagonal == 0)
                ? 0
                : (workingMatrix[row][size] - sum) / diagonal;

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