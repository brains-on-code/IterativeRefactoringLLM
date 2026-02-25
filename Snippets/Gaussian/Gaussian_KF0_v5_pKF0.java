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
        forwardElimination(augmentedMatrix);
        return backSubstitution(augmentedMatrix);
    }

    private static double[][] buildAugmentedMatrix(int size, List<Double> flatMatrix) {
        double[][] augmentedMatrix = new double[size][size + 1];
        int index = 0;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                augmentedMatrix[row][col] = flatMatrix.get(index++);
            }
        }

        return augmentedMatrix;
    }

    /**
     * Perform forward elimination to convert the matrix to upper triangular form.
     */
    private static void forwardElimination(double[][] matrix) {
        int size = matrix.length;

        for (int pivotRow = 0; pivotRow < size - 1; pivotRow++) {
            double pivotValue = matrix[pivotRow][pivotRow];

            for (int row = pivotRow + 1; row < size; row++) {
                double factor = matrix[row][pivotRow] / pivotValue;
                eliminateRow(matrix, pivotRow, row, factor);
            }
        }
    }

    private static void eliminateRow(double[][] matrix, int pivotRow, int targetRow, double factor) {
        int lastColumnIndex = matrix[0].length - 1;
        for (int col = pivotRow; col <= lastColumnIndex; col++) {
            matrix[targetRow][col] -= factor * matrix[pivotRow][col];
        }
    }

    /**
     * Perform back substitution to compute the solution vector.
     */
    private static ArrayList<Double> backSubstitution(double[][] matrix) {
        int size = matrix.length;
        double[] solution = new double[size];

        for (int row = size - 1; row >= 0; row--) {
            double sum = computeRowSum(matrix, solution, row);
            double diagonal = matrix[row][row];
            solution[row] = (diagonal == 0.0) ? 0.0 : (matrix[row][size] - sum) / diagonal;
        }

        return toArrayList(solution);
    }

    private static double computeRowSum(double[][] matrix, double[] solution, int row) {
        int size = matrix.length;
        double sum = 0.0;

        for (int col = row + 1; col < size; col++) {
            sum += matrix[row][col] * solution[col];
        }

        return sum;
    }

    private static ArrayList<Double> toArrayList(double[] array) {
        ArrayList<Double> list = new ArrayList<>(array.length);
        for (double value : array) {
            list.add(value);
        }
        return list;
    }
}