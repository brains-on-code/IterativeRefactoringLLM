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
        int numberOfTestCases = scanner.nextInt();

        while (numberOfTestCases-- > 0) {
            int matrixSize = scanner.nextInt();
            int[][] matrix = new int[matrixSize][matrixSize];

            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                    matrix[rowIndex][columnIndex] = scanner.nextInt();
                }
            }

            MatrixRotator.rotate90Degrees(matrix);
            printMatrix(matrix);
        }
        scanner.close();
    }

    static void printMatrix(int[][] matrix) {
        for (int[] rowValues : matrix) {
            for (int value : rowValues) {
                System.out.print(value + " ");
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
        int matrixSize = matrix.length;

        // Transpose the matrix
        for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                if (rowIndex > columnIndex) {
                    int temporaryValue = matrix[rowIndex][columnIndex];
                    matrix[rowIndex][columnIndex] = matrix[columnIndex][rowIndex];
                    matrix[columnIndex][rowIndex] = temporaryValue;
                }
            }
        }

        // Reverse rows (top to bottom)
        int topRowIndex = 0;
        int bottomRowIndex = matrixSize - 1;
        while (topRowIndex < bottomRowIndex) {
            for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                int temporaryValue = matrix[topRowIndex][columnIndex];
                matrix[topRowIndex][columnIndex] = matrix[bottomRowIndex][columnIndex];
                matrix[bottomRowIndex][columnIndex] = temporaryValue;
            }
            topRowIndex++;
            bottomRowIndex--;
        }
    }
}