package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class Gaussian {

    private Gaussian() {
    }

    public static ArrayList<Double> gaussian(int size, List<Double> flatAugmentedMatrix) {
        double[][] augmentedMatrix = new double[size][size + 1];

        // Fill augmented matrix from flat list (row-major order)
        int index = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                augmentedMatrix[row][col] = flatAugmentedMatrix.get(index++);
            }
        }

        performGaussianElimination(size, augmentedMatrix);
        return backSubstitution(size, augmentedMatrix);
    }

    // Perform Gaussian elimination (forward elimination)
    private static void performGaussianElimination(int size, double[][] augmentedMatrix) {
        for (int pivotRow = 0; pivotRow < size - 1; pivotRow++) {
            for (int row = pivotRow; row < size - 1; row++) {
                double factor = augmentedMatrix[row + 1][pivotRow] / augmentedMatrix[pivotRow][pivotRow];

                for (int col = pivotRow; col <= size; col++) {
                    augmentedMatrix[row + 1][col] -= factor * augmentedMatrix[pivotRow][col];
                }
            }
        }
    }

    // Back substitution to compute solution vector
    private static ArrayList<Double> backSubstitution(int size, double[][] augmentedMatrix) {
        double[][] workingMatrix = new double[size][size + 1];

        // Copy augmented matrix to working matrix
        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size; col++) {
                workingMatrix[row][col] = augmentedMatrix[row][col];
            }
        }

        ArrayList<Double> solution = new ArrayList<>();

        for (int row = size - 1; row >= 0; row--) {
            double sum = 0.0;

            for (int col = size - 1; col > row; col--) {
                workingMatrix[row][col] = workingMatrix[col][col] * workingMatrix[row][col];
                sum += workingMatrix[row][col];
            }

            if (workingMatrix[row][row] == 0) {
                workingMatrix[row][row] = 0;
            } else {
                workingMatrix[row][row] = (workingMatrix[row][size] - sum) / workingMatrix[row][row];
            }

            solution.add(workingMatrix[row][row]);
        }

        return solution;
    }
}