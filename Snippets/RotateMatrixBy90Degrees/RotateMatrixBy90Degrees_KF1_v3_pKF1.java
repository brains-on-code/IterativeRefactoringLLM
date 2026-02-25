package com.thealgorithms.matrix;

import java.util.Scanner;

/**
 * Runner class for rotating matrices.
 */
final class MatrixRotationRunner {

    private MatrixRotationRunner() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCaseCount = scanner.nextInt();

        while (testCaseCount-- > 0) {
            int dimension = scanner.nextInt();
            int[][] matrix = new int[dimension][dimension];

            for (int row = 0; row < dimension; row++) {
                for (int column = 0; column < dimension; column++) {
                    matrix[row][column] = scanner.nextInt();
                }
            }

            MatrixRotator.rotate90Degrees(matrix);
            printMatrix(matrix);
        }
        scanner.close();
    }

    static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}

/**
 * Utility class for matrix rotation operations.
 */
final class MatrixRotator {

    private MatrixRotator() {
        // Prevent instantiation
    }

    static void rotate90Degrees(int[][] matrix) {
        int dimension = matrix.length;

        // Transpose the matrix
        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column < dimension; column++) {
                if (row > column) {
                    int temp = matrix[row][column];
                    matrix[row][column] = matrix[column][row];
                    matrix[column][row] = temp;
                }
            }
        }

        // Reverse rows (top to bottom)
        int topRow = 0;
        int bottomRow = dimension - 1;
        while (topRow < bottomRow) {
            for (int column = 0; column < dimension; column++) {
                int temp = matrix[topRow][column];
                matrix[topRow][column] = matrix[bottomRow][column];
                matrix[bottomRow][column] = temp;
            }
            topRow++;
            bottomRow--;
        }
    }
}